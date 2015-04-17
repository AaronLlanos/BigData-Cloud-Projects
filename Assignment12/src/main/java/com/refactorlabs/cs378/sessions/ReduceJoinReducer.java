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
import java.util.*;

/**
 * The Reduce class for word count.  Extends class Reducer, provided by Hadoop.
 * This class defines the reduce() function for the word count example.
 */
public class ReduceJoinReducer extends Reducer<Text, Text, Text, Text> {

	private Text outKey = new Text();
	private Text outValue = new Text();

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		String stringValue;
		List<String> list = new ArrayList<String>();
		int price = 0;
		// Sum up the counts for the current word, specified in object "key".
		for (Text value : values) {
			stringValue = value.toString();
			try {
		        price = Integer.parseInt(stringValue);
		    }
		    catch(Exception e) {
				if (stringValue != null && !stringValue.equals("")){
					for (String userId: stringValue.split(",")) {
						list.add(userId);
					}
				}
		    }
		}
		if (price != 0){
			// Emit the total count for the word.
			for (String x: list){
				outKey.set(x);
				outValue.set(Integer.toString(price));
				context.write(outKey, outValue);
			}
		}
		
	}
}