package com.refactorlabs.cs378.sessions;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.LongWritable;

public class LongArrayWritable extends ArrayWritable {
        public LongArrayWritable() {
                super(LongWritable.class);
        }

        public LongArrayWritable(LongWritable[] values) {
                super(LongWritable.class, values);
        }
        
        public String toString(){
                String [] strings = toStrings();
                String str = "(" + strings.length + ")[";
                for (int i = 0; i < strings.length; i++) {
                        str +=  strings[i] + ", ";
                }
                str += "]";
                return str;
        }
}