package com.refactorlabs.cs378;

import org.apache.hadoop.io.Writable;
import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;

public class WordStatisticsWritable implements Writable{
	private long documentCount;
	private long totalCount;
	private long sumOfSquares;
	private double mean;
	private double variance;

	public void set_documentCount(long x){
		documentCount = x;
	}
	public long get_documentCount(){
		return documentCount;
	}
	public void set_totalCount(long x){
		totalCount = x;
	}
	public long get_totalCount(){
		return totalCount;
	}
	public void set_sumOfSquares(long x){
		sumOfSquares = x;
	}
	public long get_sumOfSquares(){
		return sumOfSquares;
	}
	public void set_mean(double x){
		mean = x;
	}
	public double get_mean(){
		return mean;
	}
	public void set_variance(double x){
		variance = x;
	}
	public double get_variance(){
		return variance;
	}

	public void readFields(DataInput in) throws IOException { 
		// Read the data out in the order it is written
		documentCount = in.readLong();
	   	totalCount = in.readLong();
	   	sumOfSquares = in.readLong();
		mean = in.readDouble();
		variance = in.readDouble();

	}
	public void write(DataOutput out) throws IOException {
		// Write the data out in the order it is read
		out.writeLong(documentCount);
		out.writeLong(totalCount);
		out.writeLong(sumOfSquares);
		out.writeDouble(mean);
		out.writeDouble(variance);

	}

	public String toString(){
		return "\t"+documentCount+","+totalCount+","+sumOfSquares+","+mean+","+variance;
	}
}