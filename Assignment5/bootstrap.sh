#!/bin/bash

echo "export HADOOP_USER_CLASSPATH_FIRST=true" > /home/hadoop/conf/hadoop-user-env.sh

## Replace s3n://utcs378/jars/bdp-1.4.jar with the path to your JAR file.
echo "export HADOOP_CLASSPATH=\"s3n://utcs378/al26593/jars/bdp-1.4.jar\"" >> /home/hadoop/conf/hadoop-user-env.sh
