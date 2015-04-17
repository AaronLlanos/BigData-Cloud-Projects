package com.refactorlabs.cs378.sessions;

import org.apache.avro.Schema;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.avro.mapred.Pair;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.mapreduce.AvroKeyOutputFormat;
import org.apache.avro.mapreduce.AvroMultipleOutputs;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.*;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * UserSessions example using Avro defined class for the word count data,
 * to demonstrate how to use Avro defined objects.
 *
 * Here the outputs (key and value) are both Avro objects and will be combined into
 * a Pair as the key, and null as the value.
 * written with output format: TextOutputFormat (creates an Avro container file).
 */
public class UserSessions extends Configured implements Tool {

	/**
	 * The run() method is called (indirectly) from main(), and contains all the job
	 * setup and configuration.
	 */
	public int run(String[] args) throws Exception {
		if (args.length != 3) {
			System.err.println("Usage: UserSessions <impression_input path> <lead_input path> <output path>");
			return -1;
		}

		Configuration conf = getConf();
		Job job = new Job(conf, "UserSessions");

		// Identify the JAR file to replicate to all machines.
		job.setJarByClass(UserSessions.class);

		// Use this JAR first in the classpath (We also set a bootstrap script in AWS)
		conf.set("mapreduce.user.classpath.first", "true");
	    AvroJob.setInputKeySchema(job, Pair.getPairSchema(Schema.create(Schema.Type.STRING), Session.getClassSchema()));

		// Specify the Map configurations
		MultipleInputs.addInputPath(job, new Path(args[0]), AvroKeyInputFormat.class, InvertedIndexMapper.class);
	

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// Specify the Reduce
		job.setReducerClass(InvertedIndexReducer.class);

		// Grab output directory from the command line.
		FileOutputFormat.setOutputPath(job, new Path(args[2]));


		// Execute job and grab exit code
		int exitCode = job.waitForCompletion(true) ? 0 : 1;

    	if(exitCode == 0){
        	Job reduceJoin = new Job(new Configuration(), "ReduceJoin");

        	// Identify the JAR file to replicate to all machines.
			reduceJoin.setJarByClass(UserSessions.class);

			//Set the inputs
			MultipleInputs.addInputPath(reduceJoin, new Path(args[1]), TextInputFormat.class, ReduceJoinMapper.class);
			MultipleInputs.addInputPath(reduceJoin, new Path(args[2]+"/part-r-00000"), TextInputFormat.class, ReduceJoinMapper.class);

			// Set the output key and value types (for map and reduce).
			reduceJoin.setOutputKeyClass(Text.class);
			reduceJoin.setOutputValueClass(Text.class);

			//Specify Reducer classes
			reduceJoin.setReducerClass(ReduceJoinReducer.class);

			//Specify output
			FileOutputFormat.setOutputPath(reduceJoin, new Path(args[2]+"/reduceJoin"));

			exitCode = reduceJoin.waitForCompletion(true) ? 0 : 1;

    		if(exitCode == 0){

    			Job userStatistics = new Job(new Configuration(), "UserStatistics");

	        	// Identify the JAR file to replicate to all machines.
				userStatistics.setJarByClass(UserSessions.class);

				// Set the output key and value types (for map and reduce).
				userStatistics.setMapOutputKeyClass(Text.class);
				userStatistics.setMapOutputValueClass(DoubleArrayWritable.class);
				userStatistics.setOutputKeyClass(Text.class);
				userStatistics.setOutputValueClass(DoubleArrayWritable.class);

				// Set the map, combiner, and reduce classes.
				userStatistics.setMapperClass(UserStatisticsMapper.class);
				userStatistics.setReducerClass(UserStatisticsReducer.class);

				// Set the input and output file formats.
				userStatistics.setInputFormatClass(TextInputFormat.class);
				userStatistics.setOutputFormatClass(TextOutputFormat.class);

				// Grab the input file and output directory from the command line.
				FileInputFormat.addInputPath(userStatistics, new Path(args[2]+"/reduceJoin/part-r-00000"));

				//Specify output
				FileOutputFormat.setOutputPath(userStatistics, new Path(args[2]+"/reduceJoin/UserStatistics"));

    			userStatistics.submit();

		        // Poll to see if both jobs are not finished, if not then sleep 
		        while (!userStatistics.isComplete() ){
		            Thread.sleep(5000);
				}
		        if (userStatistics.isSuccessful()) 
		            System.out.println("User UserStatistics job completed successfully!");
				else
					System.out.println("User UserStatistics job failed!");

			    System.exit(userStatistics.isSuccessful() ? 0 : 1);
    		}
		}
	    return 0;
	}
	public static void printClassPath() {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		URL[] urls = ((URLClassLoader) cl).getURLs();
		System.out.println("classpath BEGIN");
		for (URL url : urls) {
			System.out.println(url.getFile());
		}
		System.out.println("classpath END");
	}

	/**
	 * The main method specifies the characteristics of the map-reduce job
	 * by setting values on the Job object, and then initiates the map-reduce
	 * job and waits for it to complete.
	 */
	public static void main(String[] args) throws Exception {
		printClassPath();
		int res = ToolRunner.run(new Configuration(), new UserSessions(), args);
		System.exit(res);
	}
}

