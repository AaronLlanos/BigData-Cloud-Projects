package com.refactorlabs.cs378.assign2;

import com.google.common.collect.Maps;
import com.refactorlabs.cs378.utils.DoubleArrayWritable;
import com.refactorlabs.cs378.utils.LongArrayWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * MapReduce program to collect word statistics (per input document).
 *
 * @author David Franke (dfranke@cs.utexas.edu)
 */
public class WordStatistics {

	/**
	 * The Map class for word statistics.  Extends class Mapper, provided by Hadoop.
	 * This class defines the map() function for the word statistics example.
	 */
	public static class MapClass extends Mapper<LongWritable, Text, Text, LongArrayWritable> {

		private static final Integer INITIAL_COUNT = 1;

		/**
		 * Document count for each word output from the map() function is "1", so to minimize small
		 * object creation we can use a constant for this output value/object.
		 */
		private final static LongWritable ONE = new LongWritable(1L);

		/**
		 * Local variable "word" will contain a word identified in the input.
		 * The Hadoop Text object is mutable, so we can reuse the same object and
		 * simply reset its value as data for each word output.
		 */
		private Text word = new Text();

		/**
		 * Local variable "valueArray" will contain the values output for each word.
		 * This object is mutable, so we can reuse the same object and
		 * simply reset its value as data for each word output.
		 */
		private LongArrayWritable valueArray = new LongArrayWritable();

		/**
		 * Local variable writableValue is used to construct the array of Writable
		 * values that will be added to valueArray.
		 */
		private Writable[] writableValues = new Writable[3];

		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(line);

			context.getCounter("Mapper Counts", "Input Documents").increment(1L);

			Map<String, Integer> wordCountMap = Maps.newHashMap();
			// For each word in the input document, determine the number of times the
			// word occurs.  Keep the current counts in a hash map.
			while (tokenizer.hasMoreTokens()) {
				String nextWord = tokenizer.nextToken();
				Integer count = wordCountMap.get(nextWord);

				if (count == null) {
					wordCountMap.put(nextWord, INITIAL_COUNT);
				} else {
					wordCountMap.put(nextWord, count.intValue() + 1);
				}
			}

			// Create the output value for each word, and output the key/value pair.
			for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
				int count = entry.getValue().intValue();
				word.set(entry.getKey());
				writableValues[0] = ONE;
				writableValues[1] = new LongWritable(count);
				writableValues[2] = new LongWritable(count * count);
				valueArray.set(writableValues);
				context.write(word, valueArray);
				context.getCounter("Mapper Counts", "Output Words").increment(1L);
			}
		}
	}

	/**
	 * The Combiner class for word statistics.  Extends class Reducer, provided by Hadoop.
	 * This class defines a reduce() method, for combining (summing) map output in the word statistics job.
	 * All the work is done by helper methods on the class LongArrayWritable.
	 */
	public static class CombinerClass extends Reducer<Text, LongArrayWritable, Text, LongArrayWritable> {

		@Override
		public void reduce(Text key, Iterable<LongArrayWritable> values, Context context)
				throws IOException, InterruptedException {
			context.write(key, LongArrayWritable.make(LongArrayWritable.sum(values)));
		}
	}

	/**
	 * The Reduce class for word statistics.  Extends class Reducer, provided by Hadoop.
	 * This class defines the reduce() function for the word statistics example.
	 */
	public static class ReduceClass extends Reducer<Text, LongArrayWritable, Text, ArrayWritable> {

		@Override
		public void reduce(Text key, Iterable<LongArrayWritable> values, Context context)
				throws IOException, InterruptedException {
			long[] sum = LongArrayWritable.sum(values);

			context.getCounter("Reducer Counts", "Input Words").increment(1L);

			// Output the sums for now, to check the values
			DoubleArrayWritable outputValue = new DoubleArrayWritable();
			Writable[] writableArray = new Writable[3];
			double mean = (double)sum[1] / sum[0];

			writableArray[0] = new DoubleWritable(sum[0]);
			writableArray[1] = new DoubleWritable(mean);
			writableArray[2] = new DoubleWritable((double)sum[2] / sum[0] - mean * mean);
			outputValue.set(writableArray);
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

		Job job = new Job(conf, "WordStatistics");
		// Identify the JAR file to replicate to all machines.
		job.setJarByClass(WordStatistics.class);

		// Set the output key and value types (for map and reduce).
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongArrayWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(ArrayWritable.class);

		// Set the map and reduce classes.
		job.setMapperClass(MapClass.class);
		job.setCombinerClass(CombinerClass.class);
		job.setReducerClass(ReduceClass.class);

		// Set the input and output file formats.
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		// Grab the input file and output directory from the command line.
		String[] inputPaths = appArgs[0].split(",");
		for ( String inputPath : inputPaths ) {
			FileInputFormat.addInputPath(job, new Path(inputPath));
		}
		FileOutputFormat.setOutputPath(job, new Path(appArgs[1]));

		// Initiate the map-reduce job, and wait for completion.
		job.waitForCompletion(true);
	}

}
