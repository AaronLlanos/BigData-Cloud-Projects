package com.refactorlabs.cs378;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.HashMap;

/**
 * Example MapReduce program that performs word count.
 *
 * @author David Franke (dfranke@cs.utexas.edu)
 */
public class InvertedIndex {


	/**
	 * The Map class for word count.  Extends class Mapper, provided by Hadoop.
	 * This class defines the map() function for the word count example.
	 */
	public static class MapClass extends Mapper<LongWritable, Text, Text, Text> {

		/**
		 * Local variable "word" will contain the word identified in the input.
		 * The Hadoop Text object is mutable, so we can reuse the same object and
		 * simply reset its value as each word in the input is encountered.
		 */
		private Text word = new Text();

		@Override
		public void map(LongWritable key, Text value, Context context) 
			throws IOException, InterruptedException {

			//Key is one email
			String email = value.toString();
			String[] x;

			HashMap<String, String> parsed = MRDPUtils.transformEmailToMap(email);

			//Import values necessary from hashmap
			String messageID = parsed.get("Message-ID");
			String from = parsed.get("From");

			//May be sent to multiple people in form email1, email2
			String to = parsed.get("To");
			String cc = parsed.get("Cc");
			String bcc = parsed.get("Bcc");
			
			//Found key in "from" field
			if(from.length() > 0){
				word.set(messageID);
				context.write(new Text("From:"+from), word);
			}
			//Iterate through list of "To:"
			if(to!=null){
				x = to.toLowerCase().split(",");
				for (String str : x) {

					str = str.trim();

					//Found key in list of "To:"
						word.set(messageID);
						context.write(new Text("To:"+str), word);
				}
			}
			
			//Iterate through list of "cc:"
			if(cc!=null){
				x = to.toLowerCase().split(",");
				for (String str : x) {
					str = str.trim();
					System.out.println("cc = "+str);
					//Found key in list of "To:"
						word.set(messageID);
						context.write(new Text("Cc:"+str), word);
				}
			}
			//Iterate through list of "Bcc:"
			if(bcc!=null){
				x = to.toLowerCase().split(",");
				for (String str : x) {
					str = str.trim();
					System.out.println("bcc = "+str);
					//Found key in list of "To:"
						word.set(messageID);
						context.write(new Text("Bcc:"+str), word);
				}
			}
		}
	}

	/**
	 * The Reduce class for word count.  Extends class Reducer, provided by Hadoop.
	 * This class defines the reduce() function for the word count example.
	 */
	public static class ReduceClass extends Reducer<Text, Text, Text, Text> {

		private Text result = new Text();

		@Override
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			StringBuilder sb = new StringBuilder(); 
			boolean first = true;
			
			for (Text id : values) {
				if (first){ 
					first = false;
					sb.append(id.toString());

				}else{ 
					if(sb.toString().indexOf(id.toString()) < 0){
						sb.append(",");
						sb.append(id.toString());
					}
				}

            }
            result.set(sb.toString());
            context.write(key, result);
		}
	}

	/**
	 * The main method specifies the characteristics of the map-reduce job
	 * by setting values on the Job object, and then initiates the map-reduce
	 * job and waits for it to complete.
	 */
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] appArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

		Job job = new Job(conf, "invertedindex");
		// Identify the JAR file to replicate to all machines.
		job.setJarByClass(InvertedIndex.class);

		// Set the output key and value types (for map and reduce).
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// Set the map and reduce classes.
		job.setMapperClass(MapClass.class);
		job.setReducerClass(ReduceClass.class);

		// Set the input and output file formats.
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		// Grab the input file and output directory from the command line.
		FileInputFormat.addInputPath(job, new Path(appArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(appArgs[1]));

		// Initiate the map-reduce job, and wait for completion.
		job.waitForCompletion(true);
	}
}

