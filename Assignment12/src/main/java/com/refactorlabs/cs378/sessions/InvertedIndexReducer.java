package com.refactorlabs.cs378.sessions;

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
import java.lang.StringBuffer;

/**
 * The Reduce class for word count.  Extends class Reducer, provided by Hadoop.
 * This class defines the reduce() function for the word count example.
 */
public class InvertedIndexReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		String stringValue;
		StringBuffer sb = new StringBuffer();
		// Sum up the counts for the current word, specified in object "key".
		for (Text value : values) {
			stringValue = value.toString();
			sb.append(stringValue+",");
		}
		// Emit the total count for the word.
		context.write(key, new Text(sb.toString()));
	}
}