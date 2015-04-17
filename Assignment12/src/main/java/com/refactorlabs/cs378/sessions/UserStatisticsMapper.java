package com.refactorlabs.cs378.sessions;

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

public class UserStatisticsMapper extends Mapper<LongWritable, Text, Text, DoubleArrayWritable> {

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
			HashMap<String, List<Double>> m = new HashMap<String, List<Double>>();
			token = tokenizer.nextToken();
			if(m.get(token) == null){
				m.put(token, new ArrayList<Double>(Arrays.asList(Double.valueOf(tokenizer.nextToken()))));
			}else{
				m.get(token).add(Double.valueOf(tokenizer.nextToken()));
			}

			// Write out all of the values (count) of the keys (words) in map
			double count;
		    for (Map.Entry<String, List<Double>> entry : m.entrySet()) {
				word.set(entry.getKey());
		      	for (Double x: entry.getValue()) {
		      		count = x.doubleValue();
					doubleWritables[0] = ONE;
					doubleWritables[1] = new DoubleWritable(count);
					doubleWritables[2] = new DoubleWritable(count * count);
					outputValue.set(doubleWritables);
					context.write(word, outputValue);
					context.getCounter("Mapper Counts", "Output Words").increment(1L);
		      	}
			}
		}
	}