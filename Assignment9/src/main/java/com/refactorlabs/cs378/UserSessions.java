package com.refactorlabs.cs378;

import org.apache.avro.Schema;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.avro.mapred.AvroWrapper;
import org.apache.avro.mapred.Pair;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * UserSessions example using Avro defined class for the word count data,
 * to demonstrate how to use Avro defined objects.
 *
 * Here the outputs (key and value) are both Avro objects and will be combined into
 * a Pair as the key, and null as the value.
 * written with output format: TextOutputFormat (creates an Avro container file).
 */
public class UserSessions extends Configured implements Tool {

	/**
	 * The Reduce class for word count.  Extends class Reducer, provided by Hadoop.
	 * This class defines the reduce() function for the word count example.
	 */
	public static class ReduceClass
			extends Reducer<Text, AvroValue<Session>, Text, Text> {

		private MultipleOutputs multipleOutputs;
		private Random rands = new Random(); 
		private Double percentageN;

		public void setup(Context context) {
			multipleOutputs = new MultipleOutputs(context);
			String strPercentageN = context.getConfiguration().get("filter_percentageN");
            percentageN = Double.parseDouble(strPercentageN)/100.0;
		}

		public void cleanup(Context context) throws IOException, InterruptedException{
		    multipleOutputs.close();
		}

		@Override
		public void reduce(Text key, Iterable<AvroValue<Session>> values, Context context)
				throws IOException, InterruptedException {

			//Create sessionBuilder and add to it
			Session.Builder sessionBuilder = Session.newBuilder();
			boolean first = true;
			boolean hasCarfax = false;
			String impressionType;
			String impressionActionName;

			// Add every impression and lead to final list of impressions and leads respectively
			List<Impression> impressionList = new ArrayList<Impression>();
			List<Lead> leadList = new ArrayList<Lead>();
			int SRPCount = 0;
			for (AvroValue<Session> value : values) {
				List<Impression> tempImpressionList = value.datum().getImpressions();
				impressionList.addAll(tempImpressionList);
				leadList.addAll(value.datum().getLeads());

				//Must go through impressions and count number of SRP impressions for categorizing
				for(Impression tempImpression: tempImpressionList){
					impressionType = tempImpression.getImpressionType().toString();
					impressionActionName = tempImpression.getActionName().toString();
					if(impressionType.equals("SRP"))
						SRPCount++;
					if(impressionActionName.equals("VIEWED_CARFAX_REPORT") || impressionActionName.equals("VIEWED_CARFAX_REPORT_UNHOSTED"))
						hasCarfax = true;
				}
				if(first){
					sessionBuilder.setUserId(value.datum().getUserId());
					sessionBuilder.setApiKey(value.datum().getApiKey());
					sessionBuilder.setActivex(value.datum().getActivex());
					sessionBuilder.setResolution(value.datum().getResolution());
					sessionBuilder.setUserAgent(value.datum().getUserAgent());
					first = false;
				}
			}

			//Sort the impressions
			Collections.sort(impressionList, new Comparator<Impression>() {
		        public int compare(Impression x, Impression y) {
		            //Sorts by 'getTimestamp' property
		            return x.getTimestamp()<y.getTimestamp() ?-1 :x.getTimestamp()>y.getTimestamp() ?1:0;
		        }
		    });

			//Add Impressions to the Session
			sessionBuilder.setImpressions(impressionList);
			sessionBuilder.setLeads(leadList);

			//Write out from the reducer based on what category it is
			if(leadList.size() > 0){
				//Category is submitter output all these sessions
				multipleOutputs.write("userType", key.toString(), sessionBuilder.build().toString(), "submitter");
			}
			else if(leadList.size() == 0 && impressionList.size() == 1){
				//Category is a bouncer select a random sample at M%, where M = N / 10
				double percentageM = percentageN/10.0;
				if (rands.nextDouble() < percentageM) { 
					multipleOutputs.write("userType", key.toString(), sessionBuilder.build().toString(), "bouncer");
				}
			}
			else if(SRPCount == impressionList.size()){
				//Category is a browser select a random sample at N%
				if (rands.nextDouble() < percentageN) { 
					multipleOutputs.write("userType", key.toString(), sessionBuilder.build().toString(), "browser");
				}
			}
			else{
				//Category is a searcher, only select those with VIEWED_CARFAX_REPORT, or VIEWED_CARFAX_REPORT_UNHOSTED
				//From these, select a random sample at N% (each session has a N% chance of being selected).
				if(hasCarfax){
					if (rands.nextDouble() < percentageN) { 
						multipleOutputs.write("userType", key.toString(), sessionBuilder.build().toString(), "searcher");
					}
				}
			}
		}
	}

	/**
	 * The run() method is called (indirectly) from main(), and contains all the job
	 * setup and configuration.
	 */
	public int run(String[] args) throws Exception {
		if (args.length != 4) {
			System.err.println("Usage: UserSessions <impression_input path> <lead_input path> <output path> <percentageN number>");
			return -1;
		}

		Configuration conf = getConf();
		conf.set("filter_percentageN", args[3]);
		Job job = new Job(conf, "UserSessions");
		String[] appArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

		// Identify the JAR file to replicate to all machines.
		job.setJarByClass(UserSessions.class);
		// Use this JAR first in the classpath (We also set a bootstrap script in AWS)
		conf.set("mapreduce.user.classpath.first", "true");

		// Specify the Map configurations
		job.setMapOutputKeyClass(Text.class);
		AvroJob.setMapOutputValueSchema(job, Session.getClassSchema());

		// Specify the Reduce
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setReducerClass(ReduceClass.class);
		AvroJob.setOutputKeySchema(job,
				Pair.getPairSchema(Schema.create(Schema.Type.STRING), Session.getClassSchema()));
		job.setOutputValueClass(NullWritable.class);

		// Grab the input file and output directory from the command line.
		MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, ImpressionMapper.class);
		MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, LeadMapper.class);
		FileOutputFormat.setOutputPath(job, new Path(appArgs[2]));

		//Set outputs
		MultipleOutputs.addNamedOutput(job, "userType", TextOutputFormat.class, Text.class, Text.class);
 		MultipleOutputs.setCountersEnabled(job, true);

		// Initiate the map-reduce job, and wait for completion.
		job.waitForCompletion(true);

		return 0;
	}
	public static void printClassPath() {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		URL[] urls = ((URLClassLoader) cl).getURLs();
		System.out.println("classpath BEGIN");
		for (URL url : urls) {
			System.out.println(url.getFile());
		}
		System.out.println("classpath END");
	}

	/**
	 * The main method specifies the characteristics of the map-reduce job
	 * by setting values on the Job object, and then initiates the map-reduce
	 * job and waits for it to complete.
	 */
	public static void main(String[] args) throws Exception {
		printClassPath();
		int res = ToolRunner.run(new Configuration(), new UserSessions(), args);
		System.exit(res);
	}
}

