package com.refactorlabs.cs378.sessions;

import org.apache.avro.Schema;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.avro.mapred.Pair;
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

public class ReduceJoinMapper extends Mapper<LongWritable, Text, Text, Text> {

	/**
	 * Local variable "word" will contain the word identified in the input.
	 * The Hadoop Text object is mutable, so we can reuse the same object and
	 * simply reset its value as each word in the input is encountered.
	 */
	private final static long ONE = 1L;
	private Text outKey = new Text();
	private Text outValue = new Text();

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		context.getCounter("Mapper Counts", "Input Lines").increment(ONE);
		boolean first = true;
		boolean second = false;
		String token;
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(value.toString(), " ,\t", false);
		while (st.hasMoreTokens()){
			context.getCounter("Mapper Counts", "Input Words").increment(ONE);
			token = st.nextToken().trim();
			if (first){
				second = true;
				first = false;
				outKey.set(token);
			}
			else if (second){
				second = false;
				try {
			        Double.parseDouble(token);
			        outValue.set(token);
					context.write(outKey, outValue);
			    }
			    catch( Exception e ) {
					sb.append(token+",");
			    }
			}
			else{
				sb.append(token+",");
			}
		}
		outValue.set(sb.toString());
		context.write(outKey, outValue);
	}
}