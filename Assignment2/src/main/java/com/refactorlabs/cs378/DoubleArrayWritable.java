package com.refactorlabs.cs378;


import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.DoubleWritable;

public class DoubleArrayWritable extends ArrayWritable {
        public DoubleArrayWritable() {
                super(DoubleWritable.class);
        }

        public DoubleArrayWritable(DoubleWritable[] values) {
                super(DoubleWritable.class, values);
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