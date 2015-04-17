package com.refactorlabs.cs378;

import com.google.common.collect.Maps;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * WordStatistics app that uses a custom Writable to hold the computed values.
 *
 * @author David Franke (dfranke@cs.utexas.edu)
 */
public class WordStatistics {

	 public static class FakeInputSplit extends InputSplit implements Writable {
	        public void readFields(DataInput arg0) throws IOException {
	        }
	        public void write(DataOutput arg0) throws IOException {
	        }
	        public long getLength() throws IOException, InterruptedException {
	            return 0;
			}
	        public String[] getLocations() throws IOException,
	                InterruptedException {
	            return new String[0];
	        }
	}
	public static class RandomRecordReader extends RecordReader<Text, NullWritable> {
		private int numRecordsToCreate = 0;
		private int createdRecords = 0;
		private Text key = new Text();
		private NullWritable value = NullWritable.get();
		private Random rndm = new Random();
		String[] words = {"The", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog", "sir"};

		public void initialize(InputSplit split, TaskAttemptContext context)
		        throws IOException, InterruptedException {
		    // Get the number of records to create from the configuration
		    this.numRecordsToCreate = context.getConfiguration().getInt(
		            RandomInputFormat.NUM_RECORDS_PER_TASK, -1);
		}


		public boolean nextKeyValue() throws IOException, InterruptedException {
		    // If we still have records to create
		    if (createdRecords < numRecordsToCreate) {
		        // Create a string of text from the random words
	            key.set(getRandomText());
	            ++createdRecords;
	            return true;
			}else{
				// We are done creating records 
				return false;
			} 
		}
	    private String getRandomText() {
	        StringBuilder bldr = new StringBuilder();
	        double numWords = rndm.nextGaussian() * Math.sqrt(10) + 50;
			for(int i = 0; i < numWords; ++i){ 
				bldr.append(words[rndm.nextInt(10)] + " ");
			}
	        return bldr.toString();
	    }
	    public Text getCurrentKey() throws IOException, InterruptedException {
			return key; 
		}
	    public NullWritable getCurrentValue() throws IOException, InterruptedException {
	        return value;
	    }
	    public float getProgress() throws IOException, InterruptedException {
	        return (float) createdRecords / (float) numRecordsToCreate;
		}
	    public void close() throws IOException {
	        // nothing to do here...
		} 
	}

	public static class RandomInputFormat extends InputFormat<Text, NullWritable>{
		public static final String NUM_MAP_TASKS = "random.generator.map.tasks";
		public static final String NUM_RECORDS_PER_TASK = "random.generator.num.records.per.map.task";
		public List<InputSplit> getSplits(JobContext job) throws IOException {
	        // Get the number of map tasks configured for
	        int numSplits = job.getConfiguration().getInt(NUM_MAP_TASKS, -1);
	        // Create a number of input splits equivalent to the number of tasks
	        ArrayList<InputSplit> splits = new ArrayList<InputSplit>();
	        for (int i = 0; i < numSplits; ++i) {
	            splits.add(new FakeInputSplit());
	        }
	        return splits;
	    }

	    public RecordReader<Text, NullWritable> createRecordReader(InputSplit split, TaskAttemptContext context)
	            throws IOException, InterruptedException {
	                // Create a new RandomRecordReader and initialize it
	                RandomRecordReader rr = new RandomRecordReader();
	                rr.initialize(split, context);
	                return rr; 
	    }

	    public static void setNumMapTasks(Job job, int i) {
	        job.getConfiguration().setInt(NUM_MAP_TASKS, i);
	    }
	    
	    public static void setNumRecordPerTask(Job job, int i) {
	        job.getConfiguration().setInt(NUM_RECORDS_PER_TASK, i);
	    }
	}

	/**
	 * The Map class for word statistics.  Extends class Mapper, provided by Hadoop.
	 * This class defines the map() function for the word statistics example.
	 */
	public static class MapClass extends Mapper<Text, NullWritable, Text, WordStatisticsWritable> {

		private static final Integer INITIAL_COUNT = 1;

		/**
		 * Local variable "word" will contain a word identified in the input.
		 * The Hadoop Text object is mutable, so we can reuse the same object and
		 * simply reset its value as data for each word output.
		 */
		private Text word = new Text();

		/**
		 * This object is mutable, so we can reuse the same object and
		 * simply reset its value as data for each word is output.
		 */
		private WordStatisticsWritable wordStats = new WordStatisticsWritable();

		@Override
		public void map(Text key, NullWritable value, Context context)
				throws IOException, InterruptedException {
			String line = key.toString();
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
			for ( Map.Entry<String, Integer> entry : wordCountMap.entrySet() ) {
				int count = entry.getValue().intValue();

				word.set(entry.getKey());
				wordStats.clear();
				wordStats.setDocumentCount(1L);
				wordStats.setTotalCount(count);
				wordStats.setSquaresSumIndex((long)count * count);
				context.write(word, wordStats);
				context.getCounter("Mapper Counts", "Output Words").increment(1L);
			}
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
		job.setMapOutputValueClass(WordStatisticsWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(WordStatisticsWritable.class);

		// Set the map and reduce classes.
		job.setMapperClass(MapClass.class);
		job.setCombinerClass(WordStatisticsReducer.class);
		job.setReducerClass(WordStatisticsReducer.class);

		// Set the input and output file formats.
		job.setInputFormatClass(RandomInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		RandomInputFormat.setNumMapTasks(job, 10);
        RandomInputFormat.setNumRecordPerTask(job, 100000);

		// Grab the input file and output directory from the command line.
		TextOutputFormat.setOutputPath(job, new Path(appArgs[0]));

		// Initiate the map-reduce job, and wait for completion.
		job.waitForCompletion(true);
	}
}