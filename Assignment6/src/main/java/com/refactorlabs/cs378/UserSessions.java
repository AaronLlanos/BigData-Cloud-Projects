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

	public static class MapClass extends Mapper<LongWritable, Text, Text, AvroValue<Session>> {

		/**
		 * Each count output from the map() function is "1", so to minimize small
		 * object creation we can use a constant for this output value/object.
		 */

		public final static long ONE = 1L;

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
				map.put(temp[0], temp[1]);
			}


			//Create key value for word out
			uid = map.get("uid");
			apikey = map.get("apikey");
			wordBuilder = uid+":"+apikey;
			word.set(wordBuilder);


			//Create impression from record
			Impression.Builder impressionBuilder = Impression.newBuilder();
			Vertical vertical = Vertical.valueOf(map.get("vertical").toUpperCase());
			impressionBuilder.setVertical(vertical);
			impressionBuilder.setCity(map.get("city"));
			impressionBuilder.setState(map.get("state"));
			impressionBuilder.setDomain(map.get("domain"));
			impressionBuilder.setTimestamp(Long.parseLong(map.get("timestamp")));
			if(map.get("type") != null){
				ImpressionType type;
				String iType = map.get("type").toUpperCase();
				if(iType.equalsIgnoreCase("action")){
					type = ImpressionType.valueOf("ACTION");	
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
				impressionBuilder.setStartIndex(Long.parseLong(map.get("start_index")));
			}
			if(map.get("total") != null){
				impressionBuilder.setTotal(Long.parseLong(map.get("total")));
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
			if(map.get("zip") != null){
				impressionBuilder.setZip(map.get("zip"));
			}
			Impression impression = impressionBuilder.build();


			//Create session from record
			Session.Builder sessionBuilder = Session.newBuilder();
			sessionBuilder.setUserId(uid);
			sessionBuilder.setApiKey(apikey);
			sessionBuilder.setActivex(ActiveX.valueOf(map.get("activex").toUpperCase()));
			sessionBuilder.setResolution(map.get("res"));
			sessionBuilder.setUserAgent(map.get("uagent"));
			sessionBuilder.setImpressions(Arrays.asList(impression));

			//Write out from mapper
			context.write(word, new AvroValue<com.refactorlabs.cs378.Session>(sessionBuilder.build()));
			context.getCounter("Mapper Counts", "Input Words").increment(ONE);
		}
	}

	/**
	 * The Reduce class for word count.  Extends class Reducer, provided by Hadoop.
	 * This class defines the reduce() function for the word count example.
	 */
	public static class ReduceClass
			extends Reducer<Text, AvroValue<Session>,
			AvroKey<Pair<CharSequence, Session>>, NullWritable> {

		@Override
		public void reduce(Text key, Iterable<AvroValue<Session>> values, Context context)
				throws IOException, InterruptedException {

			//Create sessionBuilder and add to it
			Session.Builder sessionBuilder = Session.newBuilder();
			boolean first = true;

			// Add every impression to final list of impressions
			List<Impression> impressionList = new ArrayList<Impression>();
			for (AvroValue<Session> value : values) {
				impressionList.addAll(value.datum().getImpressions());
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

			//Write out from the reducer
			context.getCounter("Reducer Counts", "Words Out").increment(1L);
			context.write(
					new AvroKey<Pair<CharSequence, Session>>
							(new Pair<CharSequence, Session>(key.toString(), sessionBuilder.build())),
			 		NullWritable.get());
		}
	}

	/**
	 * The run() method is called (indirectly) from main(), and contains all the job
	 * setup and configuration.
	 */
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: UserSessions <input path> <output path>");
			return -1;
		}

		Configuration conf = getConf();
		Job job = new Job(conf, "UserSessions");
		String[] appArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

		// Identify the JAR file to replicate to all machines.
		job.setJarByClass(UserSessions.class);
		// Use this JAR first in the classpath (We also set a bootstrap script in AWS)
		conf.set("mapreduce.user.classpath.first", "true");

		// Specify the Map
		job.setInputFormatClass(TextInputFormat.class);
		job.setMapperClass(MapClass.class);
		job.setMapOutputKeyClass(Text.class);
		AvroJob.setMapOutputValueSchema(job, Session.getClassSchema());

		// Specify the Reduce
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setReducerClass(ReduceClass.class);
		AvroJob.setOutputKeySchema(job, Pair.getPairSchema(Schema.create(Schema.Type.STRING), Session.getClassSchema()));
		job.setOutputValueClass(NullWritable.class);

		// Grab the input file and output directory from the command line.
		String[] inputPaths = appArgs[0].split(",");
		for ( String inputPath : inputPaths ) {
			FileInputFormat.addInputPath(job, new Path(inputPath));
		}
		FileOutputFormat.setOutputPath(job, new Path(appArgs[1]));

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

