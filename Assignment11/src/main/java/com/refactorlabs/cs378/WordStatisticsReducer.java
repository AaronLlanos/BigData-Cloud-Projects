package com.refactorlabs.cs378;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * The Reduce class for word statistics.  Extends class Reducer, provided by Hadoop.
 * This class defines the reduce() function for the word statistics example.
 *
 * @author David Franke (dfranke@cs.utexas.edu)
 */
public class WordStatisticsReducer extends Reducer<Text, WordStatisticsWritable, Text, WordStatisticsWritable> {

	@Override
	public void reduce(Text key, Iterable<WordStatisticsWritable> values, Context context)
			throws IOException, InterruptedException {
		context.write(key, WordStatisticsWritable.sum(values));
	}

}
