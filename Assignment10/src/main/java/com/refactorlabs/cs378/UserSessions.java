package com.refactorlabs.cs378;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
	 * The Reduce class for word count.  Extends class Reducer, provided by Hadoop.
	 * This class defines the reduce() function for the word count example.
	 */
	public static class ReduceClass
     extends Reducer<Text, AvroValue<Session>, AvroKey<Pair<CharSequence, Session>>, NullWritable> {

		private AvroMultipleOutputs multipleOutputs;

		public void setup(Context context) {
			multipleOutputs = new AvroMultipleOutputs(context);
		}

		public void cleanup(Context context) throws IOException, InterruptedException{
		    multipleOutputs.close();
		}

		@Override
		public void reduce(Text key, Iterable<AvroValue<Session>> values, Context context)
				throws IOException, InterruptedException {

			//Create sessionBuilder and add to it
			Session.Builder sessionBuilder = Session.newBuilder();
			boolean first = true;
			String impressionType;
			String impressionActionName;

			// Add every impression and lead to final list of impressions and leads respectively
			List<Impression> impressionList = new ArrayList<Impression>();
			List<Lead> leadList = new ArrayList<Lead>();
			int SRPCount = 0;
			for (AvroValue<Session> value : values) {
				List<Impression> tempImpressionList = value.datum().getImpressions();
				impressionList.addAll(tempImpressionList);
				leadList.addAll(value.datum().getLeads());

				//Must go through impressions and count number of SRP impressions for categorizing
				for(Impression tempImpression: tempImpressionList){
					impressionType = tempImpression.getImpressionType().toString();
					impressionActionName = tempImpression.getActionName().toString();
					if(impressionType.equals("SRP"))
						SRPCount++;
				}
				if(first){
					sessionBuilder.setUserId(value.datum().getUserId());
					sessionBuilder.setApiKey(value.datum().getApiKey());
					sessionBuilder.setActivex(value.datum().getActivex());
					sessionBuilder.setResolution(value.datum().getResolution());
					sessionBuilder.setUserAgent(value.datum().getUserAgent());
					first = false;
				}
			}

			//Sort the impressions
			Collections.sort(impressionList, new Comparator<Impression>() {
		        public int compare(Impression x, Impression y) {
		            //Sorts by 'getTimestamp' property
		            return x.getTimestamp()<y.getTimestamp() ?-1 :x.getTimestamp()>y.getTimestamp() ?1:0;
		        }
		    });

			//Add Impressions to the Session
			sessionBuilder.setImpressions(impressionList);
			sessionBuilder.setLeads(leadList);

			//Write out from the reducer based on what category it is
			if(leadList.size() > 0){
				//Category is submitter
				multipleOutputs.write("userType",
                                new AvroKey<Pair<CharSequence, Session>>(new Pair<CharSequence, Session>(key.toString(), sessionBuilder.build())),
                                NullWritable.get(), "submitter");
			}
			else if(leadList.size() == 0 && impressionList.size() == 1){
				//Category is a bouncer
				multipleOutputs.write("userType",
                                new AvroKey<Pair<CharSequence, Session>>(new Pair<CharSequence, Session>(key.toString(), sessionBuilder.build())),
                                NullWritable.get(), "bouncer");
			}
			else if(SRPCount == impressionList.size()){
				//Category is a browser
				multipleOutputs.write("userType",
                                new AvroKey<Pair<CharSequence, Session>>(new Pair<CharSequence, Session>(key.toString(), sessionBuilder.build())),
                                NullWritable.get(), "browser");
			}
			else{
				//Category is a searcher
				multipleOutputs.write("userType",
                                new AvroKey<Pair<CharSequence, Session>>(new Pair<CharSequence, Session>(key.toString(), sessionBuilder.build())),
                                NullWritable.get(), "searcher");
			}
		}
	}

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

		// Specify the Map configurations
		MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, ImpressionMapper.class);
		MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, LeadMapper.class);
		
		AvroMultipleOutputs.addNamedOutput(job, "userType", AvroKeyOutputFormat.class, Pair.getPairSchema(Schema.create(Schema.Type.STRING), Session.getClassSchema()));
		
		job.setMapOutputKeyClass(Text.class);
		AvroJob.setMapOutputValueSchema(job, Session.getClassSchema());

		// Specify the Reduce
		job.setReducerClass(ReduceClass.class);
		job.setOutputValueClass(NullWritable.class);

		// Grab the input file and output directory from the command line.
		FileOutputFormat.setOutputPath(job, new Path(args[2]));

		//Set outputs
 		AvroMultipleOutputs.setCountersEnabled(job, true);

		// Execute job and grab exit code
		int exitCode = job.waitForCompletion(true) ? 0 : 1;

    	if(exitCode == 0){
        	Job bouncerCount = new Job(new Configuration(), "BouncerCount");
	        Job searcherCount = new Job(new Configuration(), "SearcherCount");

	        AvroJob.setInputKeySchema(bouncerCount, Pair.getPairSchema(Schema.create(Schema.Type.STRING), Session.getClassSchema()));
	        AvroJob.setInputKeySchema(searcherCount, Pair.getPairSchema(Schema.create(Schema.Type.STRING), Session.getClassSchema()));

        	// Identify the JAR file to replicate to all machines.
			bouncerCount.setJarByClass(UserSessions.class);
			searcherCount.setJarByClass(UserSessions.class);

			//Set the inputs
			MultipleInputs.addInputPath(bouncerCount, new Path(args[2]+"/bouncer-r-00000.avro"), AvroKeyInputFormat.class, BouncerImpressionMapper.class);
			MultipleInputs.addInputPath(searcherCount, new Path(args[2]+"/searcher-r-00000.avro"), AvroKeyInputFormat.class, SearcherVDPMapper.class);

			// Set the output key and value types (for map and reduce).
			bouncerCount.setOutputKeyClass(Text.class);
			bouncerCount.setOutputValueClass(LongWritable.class);
			searcherCount.setOutputKeyClass(Text.class);
			searcherCount.setOutputValueClass(DoubleArrayWritable.class);

			//Specify Reducer classes
			bouncerCount.setReducerClass(BouncerImpressionReducer.class);
			searcherCount.setReducerClass(SearcherVDPReducer.class);

			//Specify output
			FileOutputFormat.setOutputPath(bouncerCount, new Path(args[2]+"/bouncerCount"));
			FileOutputFormat.setOutputPath(searcherCount, new Path(args[2]+"/searcherCount"));

			bouncerCount.submit();
			searcherCount.submit();

	        // Poll to see if both jobs are not finished, if not then sleep 
	        while (!bouncerCount.isComplete() || !searcherCount.isComplete()) {
	            Thread.sleep(5000);
			}
	        if (bouncerCount.isSuccessful()) 
	            System.out.println("Bouncer impression count job completed successfully!");
			else
				System.out.println("Bouncer impression count job failed!");
			
			if (searcherCount.isSuccessful()) 
			    System.out.println("Searcher VDP job completed successfully!");
			else
				System.out.println("Searcher VDP job failed!");
			
		    System.exit(bouncerCount.isSuccessful() && searcherCount.isSuccessful() ? 0 : 1);
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

