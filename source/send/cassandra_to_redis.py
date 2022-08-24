#!/usr/conda/bin/python

from pyspark.sql import SparkSession


if __name__ == "__main__":

    # 1. spark session 생성
    spark = SparkSession.builder.enableHiveSupport().getOrCreate()

    # 2. cassandra 데이터를 데이터프레임으로 로드
    cassandra_df = spark.read.format("org.apache.spark.sql.cassandra").options(table="wallet_user_segments", keyspace="featurestore").load()
    cassandra_df.createOrReplaceTempView("temp_view_mid")

    # 3. 데이터프레임을 redis 에 쓰기
    redis_df = spark.sql("select mid from temp_view_mid")
    redis_df.write.format("org.apache.spark.sql.redis").option("table", "featurestore").save()