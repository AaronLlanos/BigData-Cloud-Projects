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


/**
 * Example MapReduce program that performs word count.
 *
 * @author David Franke (dfranke@cs.utexas.edu)
 */
public class WordStatisticsAggregator {

	/**
	 * The Map class for word count.  Extends class Mapper, provided by Hadoop.
	 * This class defines the map() function for the word count example.
	 */
	public static class MapClass extends Mapper<LongWritable, Text, Text, WordStatisticsWritable>{

		// Our output key and value Writables
		private Text outWord = new Text();
		public WordStatisticsWritable outTuple = new WordStatisticsWritable();
		private final long ONE = 1L;

		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			StringTokenizer str = new StringTokenizer(value.toString());
			outWord.set(str.nextToken());
			String xvalue = str.nextToken();
			String[] lineValues = xvalue.split(",");

			// For each word in the input file, assign it to outWord and pass off
			outTuple.set_documentCount(Long.parseLong(lineValues[0], 10));
			outTuple.set_totalCount(Long.parseLong(lineValues[1], 10));
 			outTuple.set_sumOfSquares(Long.parseLong(lineValues[2], 10));
			outTuple.set_mean(Double.parseDouble(lineValues[3]));
			outTuple.set_variance(Double.parseDouble(lineValues[4]));

			//Send the value out
			context.write(outWord, outTuple);

		}
	}

	/**
	 * The Reduce class for word count.  Extends class Reducer, provided by Hadoop.
	 * This class defines the reduce() function for the word count example.
	 */
	public static class ReduceClass extends Reducer<Text, WordStatisticsWritable, Text, WordStatisticsWritable> {

		private WordStatisticsWritable finalOutTuple = new WordStatisticsWritable();

		@Override
		public void reduce(Text key, Iterable<WordStatisticsWritable> values, Context context) throws IOException, InterruptedException {

				long documentCount = 0L;
				long totalCount = 0L;
				long sumOfSquares = 0L;
				double mean = 0.0;
				double variance = 0.0;


				for(WordStatisticsWritable value : values){
					documentCount += value.get_documentCount();
					totalCount += value.get_totalCount();
					sumOfSquares += value.get_sumOfSquares();
				}

				mean = (double)totalCount/(double)documentCount;
				variance = ( ((double)sumOfSquares/(double)totalCount)-(mean*mean) );
				finalOutTuple.set_totalCount(totalCount);
				finalOutTuple.set_documentCount(documentCount);
				finalOutTuple.set_sumOfSquares(sumOfSquares);
				finalOutTuple.set_mean(mean);
				finalOutTuple.set_variance(variance);

				context.write(key, finalOutTuple);

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

		Job job = new Job(conf, "wordstatisticsaggregator");
		// Identify the JAR file to replicate to all machines.
		job.setJarByClass(WordStatisticsAggregator.class);

		// Set the output key and value types (for map and reduce).
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(WordStatisticsWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(WordStatisticsWritable.class);

		// Set the map, combiner, and reduce classes.
		job.setMapperClass(MapClass.class);
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
