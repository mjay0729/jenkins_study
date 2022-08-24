#!/bin/bash

##################################
# 주의!
# 1. MLU 에서 실행 가정
# 2. Hadoop Cli 설치 완료 가정
# 3. IU keytab register 완료 가정
# 4. spark master 는 local (당분간)
##################################

export PYSPARK_PYTHON=/opt/conda/bin/python  


spark-submit \
--packages com.datastax.spark:spark-cassandra-connector_2.11:2.5.1,com.redislabs:spark-redis_2.11:2.4.2 \
--conf spark.cassandra.connection.host="10.241.0.110" \
--conf spark.sql.extensions=com.datastax.spark.connector.CassandraSparkExtensions \
--conf spark.redis.host="10.241.9.94" \
--conf spark.redis.port=7000 \
--conf spark.redis.auth=Featurestore0513 \
--master local[*] \
$1