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

public class ImpressionMapper extends Mapper<LongWritable, Text, Text, AvroValue<Session>> {

		/**
		 * Each count output from the map() function is "1", so to minimize small
		 * object creation we can use a constant for this output value/object.
		 */

		private final static long ONE = 1L;

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
			uid = map.get("uid");
			apikey = map.get("apikey");
			wordBuilder = uid+":"+apikey;
			word.set(wordBuilder);


			//Create impression from record
			Impression.Builder impressionBuilder = Impression.newBuilder();
			
			if(map.get("city") != null){
				impressionBuilder.setCity(map.get("city"));
			}
			if(map.get("state") != null){
				impressionBuilder.setState(map.get("state"));
			}
			if(map.get("domain") != null){
				impressionBuilder.setDomain(map.get("domain"));
			}
			if(map.get("timestamp") != null){
				impressionBuilder.setTimestamp(Long.parseLong(map.get("timestamp")));
			}
			if(map.get("vertical") != null){
				Vertical vertical = Vertical.valueOf(map.get("vertical").toUpperCase());
				impressionBuilder.setVertical(vertical);
			}
			if(map.get("type") != null){
				ImpressionType type;
				String iType = map.get("type").toUpperCase();
				if(iType.equalsIgnoreCase("action")){
					type = ImpressionType.valueOf("ACTION");	
				}
				else if(iType.equalsIgnoreCase("thankyou")){
 					type = ImpressionType.valueOf("THANK_YOU");
				}
				else{
					type = ImpressionType.valueOf("VDP");	
				}
				impressionBuilder.setImpressionType(type);
			}
			if(map.get("action") != null){
				Action action = Action.valueOf(map.get("action").toUpperCase());
				impressionBuilder.setAction(action);
			}
			if(map.get("phone_type") != null){
				PhoneType phoneType = PhoneType.valueOf(map.get("phone_type").toUpperCase());
				impressionBuilder.setPhoneType(phoneType);
			}
			if(map.get("action_name") != null){
				ActionName actionName = ActionName.valueOf(map.get("action_name").toUpperCase());
				impressionBuilder.setActionName(actionName);
			}
			if(map.get("id") != null){
				String[] idArray = map.get("id").split(",");
				List idList = new ArrayList();
				for(String id: idArray){
					idList.add(Long.parseLong(id));
				}
				impressionBuilder.setId(idList);
			}
			if(map.get("ab") != null){
				impressionBuilder.setAb(map.get("ab"));
			}
			if(map.get("start_index") != null){
				impressionBuilder.setStartIndex(Integer.parseInt(map.get("start_index")));
			}
			if(map.get("total") != null){
				if(map.get("total").equals("Millions"))
					impressionBuilder.setTotal(Integer.valueOf(10000000));
				else
					impressionBuilder.setTotal(Integer.parseInt(map.get("total")));
			}
			if(map.get("lat") != null){
				if(!map.get("lat").equalsIgnoreCase("[object Object]"))
					impressionBuilder.setLat(Double.parseDouble(map.get("lat")));
			}
			if(map.get("lon") != null){
				if(!map.get("lon").equalsIgnoreCase("[object Object]"))
					impressionBuilder.setLon(Double.parseDouble(map.get("lon")));
			}
			if(map.get("address") != null){
				impressionBuilder.setAddress(map.get("address"));
			}
			Impression impression = impressionBuilder.build();


			//Create session from record
			Session.Builder sessionBuilder = Session.newBuilder();
			sessionBuilder.setUserId(uid);
			sessionBuilder.setApiKey(apikey);
			sessionBuilder.setLeads(new ArrayList<Lead>());
			sessionBuilder.setActivex(ActiveX.valueOf(map.get("activex").toUpperCase()));
			sessionBuilder.setResolution(map.get("res"));
			sessionBuilder.setUserAgent(map.get("uagent"));
			sessionBuilder.setImpressions(Arrays.asList(impression));

			//Write out from mapper
			context.write(word, new AvroValue<com.refactorlabs.cs378.Session>(sessionBuilder.build()));
			context.getCounter("Mapper Counts", "Input Words").increment(ONE);
		}
	}