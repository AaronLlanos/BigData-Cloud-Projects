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
 * The Reduce class for word count.  Extends class Reducer, provided by Hadoop.
 * This class defines the reduce() function for the word count example.
 */
public class SearcherVDPReducer extends Reducer<Text, DoubleArrayWritable, Text, DoubleArrayWritable> {

	private DoubleWritable[] doubleWritables = new DoubleWritable[3];
	private DoubleArrayWritable outputValue = new DoubleArrayWritable();

	@Override
	public void reduce(Text key, Iterable<DoubleArrayWritable> values, Context context) throws IOException, InterruptedException {

			double countVDP = 0.0;
			double idCountInSRP = 0.0;
			double CTR = 0.0;
			Writable[] temp;
			DoubleWritable doubleWritable;

			System.out.println("Set up the reducer in VDP, Bitch!!!");
			for( DoubleArrayWritable value : values){
				temp = value.get();
				countVDP += ((DoubleWritable)temp[0]).get();
				idCountInSRP += ((DoubleWritable)temp[1]).get();
			}
			CTR = countVDP/idCountInSRP;

			doubleWritables[0] = new DoubleWritable(countVDP);
			doubleWritables[1] = new DoubleWritable(idCountInSRP);
			doubleWritables[2] = new DoubleWritable(CTR);
			outputValue.set(doubleWritables);
			context.write(key, outputValue);

	}
}