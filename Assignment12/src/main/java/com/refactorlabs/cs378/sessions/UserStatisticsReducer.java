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
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import java.io.IOException;
import java.util.*;

public class UserStatisticsReducer extends Reducer<Text, DoubleArrayWritable, Text, DoubleArrayWritable> {

		private DoubleWritable[] doubleWritables = new DoubleWritable[8];
		private DoubleArrayWritable outputValue = new DoubleArrayWritable();

		@Override
		public void reduce(Text key, Iterable<DoubleArrayWritable> values, Context context) throws IOException, InterruptedException {

				
				Writable[] temp;
				List<Double> prices = new ArrayList<Double>();

				for(DoubleArrayWritable value : values){
					temp = value.get();
					prices.add(((DoubleWritable)temp[1]).get());
				}
				double[] dsArray = new double[prices.size()];
				for(int i = 0; i < dsArray.length; i++){
					dsArray[i] = prices.get(i);
				}
				Arrays.sort(dsArray);
				// Calculate median (middle number)
		    	double median = 0;
		    	double pos1 = Math.floor((dsArray.length - 1.0) / 2.0);
		    	double pos2 = Math.ceil((dsArray.length - 1.0) / 2.0);
		    	if (pos1 == pos2 ) {
		    	   median = dsArray[(int)pos1];
		    	} else {
		    	   median = (dsArray[(int)pos1] + dsArray[(int)pos2]) / 2.0 ;
		    	}
				DescriptiveStatistics ds = new DescriptiveStatistics(dsArray);

				doubleWritables[0] = new DoubleWritable((double)ds.getN());
				doubleWritables[1] = new DoubleWritable(ds.getMin());
				doubleWritables[2] = new DoubleWritable(ds.getMax());
				doubleWritables[3] = new DoubleWritable(ds.getMean());
				doubleWritables[4] = new DoubleWritable(median);
				doubleWritables[5] = new DoubleWritable(Math.sqrt(ds.getVariance()));
				doubleWritables[6] = new DoubleWritable(ds.getSkewness());
				doubleWritables[7] = new DoubleWritable(ds.getKurtosis());
				outputValue.set(doubleWritables);
				context.write(key, outputValue);

		}
	}