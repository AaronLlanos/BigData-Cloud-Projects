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

public class BouncerImpressionMapper extends Mapper<AvroKey<Pair<CharSequence, Session>>, NullWritable, Text, LongWritable> {

	private final static LongWritable ONE = new LongWritable(1L);
	private LongWritable outputValue;
	
	/**
	 * Local variable "word" will contain the word identified in the input.
	 * The Hadoop Text object is mutable, so we can reuse the same object and
	 * simply reset its value as each word in the input is encountered.
	 */
	private Text word = new Text();

	@Override
	public void map(AvroKey<Pair<CharSequence, Session>> key, NullWritable value, Context context)
			throws IOException, InterruptedException {

		HashMap<String, Long> map = new HashMap<String, Long>();
		String type;
		for (Impression impression: key.datum().value().getImpressions()) {
			type = impression.getImpressionType().toString();
			if(map.get(type) == null){
				map.put(type, new Long(1L));
			}else{
				long temp = (map.get(type)).longValue();
				map.put(type, new Long(temp+1L));
			}
		}
		
		// // Write out all of the values (count) of the keys (words) in map
	    for (Map.Entry<String, Long> entry : map.entrySet()) {
			context.getCounter("Mapper Counts", "Output Words").increment(1L);
	      	Long count = entry.getValue();
			word.set(entry.getKey());
			outputValue = new LongWritable(count);
			context.write(word, outputValue);
		}
	}
}