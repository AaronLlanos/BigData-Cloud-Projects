package com.refactorlabs.cs378;

import org.apache.avro.Schema;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.avro.mapred.Pair;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.mapreduce.AvroKeyOutputFormat;
import org.apache.avro.mapreduce.AvroMultipleOutputs;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
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
 * The Map class for word count.  Extends class Mapper, provided by Hadoop.
 * This class defines the map() function for the word count example.
 */
public class SearcherVDPMapper extends Mapper<AvroKey<Pair<CharSequence, Session>>, NullWritable, Text, DoubleArrayWritable> {

	/**
	 * Local variable "word" will contain the word identified in the input.
	 * The Hadoop Text object is mutable, so we can reuse the same object and
	 * simply reset its value as each word in the input is encountered.
	 */
	private Text word = new Text("VDPCount, idCountInSRP, CTR");

	/**
	 * Each count output from the map() function is "1", so to minimize small
	 * object creation we can use a constant for this output value/object.
	 */
	private final static DoubleWritable ONE = new DoubleWritable(1L);

	private DoubleWritable[] doubleWritables = new DoubleWritable[2];
	private DoubleArrayWritable outputValue = new DoubleArrayWritable();

	@Override
	public void map(AvroKey<Pair<CharSequence, Session>> key, NullWritable value, Context context)
			throws IOException, InterruptedException {

		String type;
		double countVDP = 0.0;
		double idCountInSRP = 0.0;
		System.out.println("Set up the mapper in VDP, Bitch!!!");
		for (Impression impression: key.datum().value().getImpressions()) {
			System.out.println("New Impression in VDP!!!");
			type = impression.getImpressionType().toString();
			if(type.equals("VDP")){
				countVDP++;
			}
			else if(type.equals("SRP")){
				if(impression.getId() != null)
					idCountInSRP += impression.getId().size();
			}
		}

		// Write out all of the values (count) of the keys (words) in map
		context.getCounter("Mapper Counts", "Output Words").increment(1L);
		doubleWritables[0] = new DoubleWritable(countVDP);
		doubleWritables[1] = new DoubleWritable(idCountInSRP);
		outputValue.set(doubleWritables);
		System.out.println("Write this to reducer!!!");
		context.write(word, outputValue);
	}
}