package com.refactorlabs.cs378;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Writable;
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
import java.util.*;

/**
 * Example MapReduce program that performs word count.
 *
 * @author David Franke (dfranke@cs.utexas.edu)
 */
public class WordStatistics {

	/**
	 * The Map class for word count.  Extends class Mapper, provided by Hadoop.
	 * This class defines the map() function for the word count example.
	 */
	public static class MapClass extends Mapper<LongWritable, Text, Text, DoubleArrayWritable> {

		/**
		 * Local variable "word" will contain the word identified in the input.
		 * The Hadoop Text object is mutable, so we can reuse the same object and
		 * simply reset its value as each word in the input is encountered.
		 */
		private Text word = new Text();

		/**
		 * Each count output from the map() function is "1", so to minimize small
		 * object creation we can use a constant for this output value/object.
		 */
		private final static DoubleWritable ONE = new DoubleWritable(1L);

		private DoubleWritable[] doubleWritables = new DoubleWritable[3];
		private DoubleArrayWritable outputValue = new DoubleArrayWritable();

		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(line);
			String token;

			// For each word in the input line, add to hashMap, if not in map, add it.
			HashMap<String, Double> m = new HashMap<String, Double>();
			while (tokenizer.hasMoreTokens()) {
				token = tokenizer.nextToken();
				if(m.get(token) == null){
					m.put(token, new Double(1.0));
				}else{
					double temp = (m.get(token)).doubleValue();
					m.put(token, new Double(temp+1.0));
				}
			}

			// Write out all of the values (count) of the keys (words) in map
		    for (Map.Entry<String, Double> entry : m.entrySet()) {
		      	double count = entry.getValue().doubleValue();
				word.set(entry.getKey());
				doubleWritables[0] = ONE;
				doubleWritables[1] = new DoubleWritable(count);
				doubleWritables[2] = new DoubleWritable(count * count);
				outputValue.set(doubleWritables);
				context.write(word, outputValue);
				context.getCounter("Mapper Counts", "Output Words").increment(1L);
			}
		}
	}

	/**
	* The combiner class for word statistics.
	*
	**/
	public static class CombinerClass extends Reducer<Text, DoubleArrayWritable, Text, DoubleArrayWritable>{
		
		private DoubleWritable[] doubleWritables = new DoubleWritable[3];
		private DoubleArrayWritable outputValue = new DoubleArrayWritable();

		@Override
		public void reduce(Text key, Iterable<DoubleArrayWritable> values, Context context) throws IOException, InterruptedException {

			double files = 0.0;
			double occurence = 0.0;
			double soccurence = 0.0;
			Writable[] temp;

			for( DoubleArrayWritable value : values){
				temp = value.get();
				files += ((DoubleWritable)temp[0]).get();
				occurence += ((DoubleWritable)temp[1]).get();
				soccurence += ((DoubleWritable)temp[2]).get();
			}

			doubleWritables[0] = new DoubleWritable(files);
			doubleWritables[1] = new DoubleWritable(occurence);
			doubleWritables[2] = new DoubleWritable(soccurence);
			outputValue.set(doubleWritables);
			context.write(key, outputValue);

		}
	}
	/**
	 * The Reduce class for word count.  Extends class Reducer, provided by Hadoop.
	 * This class defines the reduce() function for the word count example.
	 */
	public static class ReduceClass extends Reducer<Text, DoubleArrayWritable, Text, DoubleArrayWritable> {

		private DoubleWritable[] doubleWritables = new DoubleWritable[3];
		private DoubleArrayWritable outputValue = new DoubleArrayWritable();

		@Override
		public void reduce(Text key, Iterable<DoubleArrayWritable> values, Context context) throws IOException, InterruptedException {

				double files = 0.0;
				double occurence = 0.0;
				double soccurence = 0.0;
				Writable[] temp;
				DoubleWritable doubleWritable;

				for( DoubleArrayWritable value : values){
					temp = value.get();
					files += ((DoubleWritable)temp[0]).get();
					occurence += ((DoubleWritable)temp[1]).get();
					soccurence += ((DoubleWritable)temp[2]).get();
				}
				double mean = occurence/files;
				double variance = (soccurence/files)-(mean*mean);

				doubleWritables[0] = new DoubleWritable(files);
				doubleWritables[1] = new DoubleWritable(mean);
				doubleWritables[2] = new DoubleWritable(variance);
				outputValue.set(doubleWritables);
				context.write(key, outputValue);

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

		Job job = new Job(conf, "wordstatistics");
		// Identify the JAR file to replicate to all machines.
		job.setJarByClass(WordStatistics.class);

		// Set the output key and value types (for map and reduce).
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(DoubleArrayWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleArrayWritable.class);

		// Set the map, combiner, and reduce classes.
		job.setMapperClass(MapClass.class);
		job.setCombinerClass(CombinerClass.class);
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
