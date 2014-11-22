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
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * WordStatistics example using Avro defined class for the word count data,
 * to demonstrate how to use Avro defined objects.
 *
 * Here the outputs (key and value) are both Avro objects and will be combined into
 * a Pair as the key, and null as the value.
 * written with output format: TextOutputFormat (creates an Avro container file).
 */
public class WordStatistics extends Configured implements Tool {

	public static class MapClass extends Mapper<LongWritable, Text, Text, AvroValue<WordStatisticsData>> {

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

			String line = value.toString();
			String token;
			StringTokenizer tokenizer = new StringTokenizer(line);
			HashMap<String, Long> map = new HashMap<String, Long>();

			context.getCounter("Mapper Counts", "Input Lines").increment(ONE);

			// For each word in the input line, emit a count of 1 for that word.
			while (tokenizer.hasMoreTokens()) {

				token = (tokenizer.nextToken());

				if(map.get(token) == null){
					map.put(token, new Long(ONE));
				}else{
					map.put(token, new Long(map.get(token).longValue()+ONE));
				}

			}
			long totalCount;
			long sumOfSquares;
			for (Map.Entry<String, Long> entry : map.entrySet()) {
				WordStatisticsData.Builder builder = WordStatisticsData.newBuilder();
				totalCount = entry.getValue().longValue();
				sumOfSquares = totalCount*totalCount;
				word.set(entry.getKey());
				builder.setTotalCount(totalCount);
				builder.setDocumentCount(ONE);
				builder.setSumOfSquares(sumOfSquares);
				context.write(word, new AvroValue<com.refactorlabs.cs378.WordStatisticsData>(builder.build()));
				context.getCounter("Mapper Counts", "Input Words").increment(ONE);
			}
		}
	}

	/**
	 * The Reduce class for word count.  Extends class Reducer, provided by Hadoop.
	 * This class defines the reduce() function for the word count example.
	 */
	public static class ReduceClass
			extends Reducer<Text, AvroValue<WordStatisticsData>,
			AvroKey<Pair<CharSequence, WordStatisticsData>>, NullWritable> {

		@Override
		public void reduce(Text key, Iterable<AvroValue<WordStatisticsData>> values, Context context)
				throws IOException, InterruptedException {

			long totalCount = 0L;
			long documentCount = 0L;
			long sumOfSquares = 0L;
			double mean = 0.0;
			double variance = 0.0;

			// Sum up the counts for the current word, specified in object "key".
			for (AvroValue<WordStatisticsData> value : values) {
				totalCount += value.datum().getTotalCount();
				documentCount += value.datum().getDocumentCount();
				sumOfSquares += value.datum().getSumOfSquares();
			}

			mean = (double)totalCount/(double)documentCount;
			variance = ((double)sumOfSquares/(double)documentCount)-(mean*mean);

			// Emit the total count for the word.
			WordStatisticsData.Builder builder = WordStatisticsData.newBuilder();
			builder.setTotalCount(totalCount);
			builder.setDocumentCount(documentCount);
			builder.setSumOfSquares(sumOfSquares);
			builder.setMean(new Double(mean));
			builder.setVariance(new Double(variance));

			context.getCounter("Reducer Counts", "Words Out").increment(1L);
			context.write(
					new AvroKey<Pair<CharSequence, WordStatisticsData>>
							(new Pair<CharSequence, WordStatisticsData>(key.toString(), builder.build())),
					NullWritable.get());
		}
	}

	/**
	 * The run() method is called (indirectly) from main(), and contains all the job
	 * setup and configuration.
	 */
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: WordStatistics <input path> <output path>");
			return -1;
		}

		Configuration conf = getConf();
		Job job = new Job(conf, "WordStatistics");
		String[] appArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

		// Identify the JAR file to replicate to all machines.
		job.setJarByClass(WordStatistics.class);
		// Use this JAR first in the classpath (We also set a bootstrap script in AWS)
		conf.set("mapreduce.user.classpath.first", "true");

		// Specify the Map
		job.setInputFormatClass(TextInputFormat.class);
		job.setMapperClass(MapClass.class);
		job.setMapOutputKeyClass(Text.class);
		AvroJob.setMapOutputValueSchema(job, WordStatisticsData.getClassSchema());

		// Specify the Reduce
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setReducerClass(ReduceClass.class);
		AvroJob.setOutputKeySchema(job,
				Pair.getPairSchema(Schema.create(Schema.Type.STRING), WordStatisticsData.getClassSchema()));
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
		int res = ToolRunner.run(new Configuration(), new WordStatistics(), args);
		System.exit(res);
	}
}

