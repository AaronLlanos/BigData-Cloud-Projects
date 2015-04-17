package com.refactorlabs.cs378.sessions;

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

public class InvertedIndexMapper extends Mapper<AvroKey<Pair<CharSequence, Session>>, NullWritable, Text, Text> {

	/**
	 * Local variable "word" will contain the word identified in the input.
	 * The Hadoop Text object is mutable, so we can reuse the same object and
	 * simply reset its value as each word in the input is encountered.
	 */
	private final static long ONE = 1L;
	private Text out = new Text();

	@Override
	public void map(AvroKey<Pair<CharSequence, Session>> key, NullWritable value, Context context)
			throws IOException, InterruptedException {

		context.getCounter("Mapper Counts", "Input Lines").increment(ONE);

		String type;
		String user_id = key.datum().value().getUserId().toString();
		for (Impression impression: key.datum().value().getImpressions()) {
			type = impression.getImpressionType().toString();
			if(type.equals("VDP")){
				out.set(user_id);
				for (Long id : impression.getId()){
					context.getCounter("Mapper Counts", "Input Words").increment(ONE);
					context.write (new Text(id.toString()), out);
				}
			}
		}

	}
}