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
import org.apache.hadoop.fs.Path;
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
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.net.URL;
import java.net.URLClassLoader;

public class LeadMapper extends Mapper<LongWritable, Text, Text, AvroValue<Session>> {

		/**
		 * Each count output from the map() function is "1", so to minimize small
		 * object creation we can use a constant for this output value/object.
		 */

		private final static long ONE = 1L;
		private Scanner scanner;
		Map<String, String> zipDML = new HashMap<String, String>();
		public void setup(Context context) throws IOException, InterruptedException{
			Path[] paths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
			for (Path path: paths) {
				scanner = new Scanner(new File(path.toString()));
				while(scanner.hasNext()){
					String[] tempSplit = scanner.next().split(",");
					zipDML.put(tempSplit[0], tempSplit[1]);
				}
			}
		}

		/**
		 * Local variable "word" will contain the word identified in the input.
		 * The Hadoop Text object is mutable, so we can reuse the same object and
		 * simply reset its value as each word in the input is encountered.
		 */
		private Text word = new Text();

		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			String uid;
			String apikey;
			String wordBuilder;
			String line = value.toString();
			context.getCounter("Mapper Counts", "Input Lines").increment(ONE);

			//Split the log entry into individual values
			HashMap<String, String> map = new HashMap<String, String>();
			String arr[] = line.split("\\|\t\\|");
			String temp[];
			boolean first = true;
			for (String x: arr) {
				temp = x.split(":");
				if(first){
					temp[0] = temp[0].split("\\t")[1];
					first = false;
				}

				//Error check the values
				if(temp.length == 2)
					map.put(temp[0], temp[1]);
				else if(temp.length > 2){
					String mapValue = "";
					for (int i = 1; i < temp.length; i++) {
						mapValue += temp[i];
					}
					map.put(temp[0], mapValue);
				}
				else
					map.put(temp[0], null);
			}


			//Create key value for word out
			uid = map.get("userid");
			apikey = map.get("apikey");
			wordBuilder = uid+":"+apikey;
			word.set(wordBuilder);

			//Load a new builder to build a record of "Lead"
			Lead.Builder leadBuilder = Lead.newBuilder();

			//Create lead from text
			if(map.get("lead_id") != null){
				leadBuilder.setLeadId(Long.parseLong(map.get("lead_id")));
			}
			if(map.get("type") != null){
				LeadType leadType = LeadType.valueOf(map.get("type").toUpperCase());
				leadBuilder.setType(leadType);
			}
			if(map.get("bidtype") != null){
				BidType bidType = BidType.valueOf(map.get("bidtype").toUpperCase());
				leadBuilder.setBidType(bidType);
			}
			if(map.get("advertiser") != null){
				leadBuilder.setAdvertiser(map.get("advertiser"));
			}
			if(map.get("campaign_id") != null){
				leadBuilder.setCampaignId(map.get("campaign_id"));
			}
			if(map.get("recordid") != null){
				leadBuilder.setId(Long.parseLong(map.get("recordid")));
			}
			if(map.get("lead_amount") != null){
				leadBuilder.setAmount(Float.parseFloat(map.get("lead_amount")));
			}
			if(map.get("revenue") != null){
				leadBuilder.setRevenue(Float.parseFloat(map.get("revenue")));
			}
			if(map.get("test") != null){
				leadBuilder.setTest(Boolean.parseBoolean(map.get("test")));
			}
			if(map.get("ab") != null){
				leadBuilder.setAb(map.get("ab"));
			}
			if(map.get("customer_zip") != null){
				leadBuilder.setCustomerZip(map.get("customer_zip"));
				leadBuilder.setCustomerDma(zipDML.get(map.get("customer_zip")));
			}
			if(map.get("zip") != null){
				leadBuilder.setVehicleZip(map.get("zip"));
				leadBuilder.setVehicleDma(zipDML.get(map.get("zip")));
			}
			
			Lead lead = leadBuilder.build();

			//Create session from record
			Session.Builder sessionBuilder = Session.newBuilder();
			sessionBuilder.setImpressions(new ArrayList<Impression>());
			sessionBuilder.setUserId(uid);
			sessionBuilder.setApiKey(apikey);
			sessionBuilder.setLeads(Arrays.asList(lead));

			//Write out from mapper
			context.write(word, new AvroValue<com.refactorlabs.cs378.Session>(sessionBuilder.build()));
			context.getCounter("Mapper Counts", "Input Words").increment(ONE);
		}
	}