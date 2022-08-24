#!/usr/conda/bin/python

from pyspark.sql import SparkSession


if __name__ == "__main__":

    # 1. spark session 생성
    spark = SparkSession.builder.enableHiveSupport().getOrCreate()

    # 2. IU 데이터를 데이터프레임으로 로드
    statement = """ 
    select mid, recency_score, frequency_score, rf_score, activity_score, user_segment, target_dt, region
    from linewallet_business_pro.wallet_user_segments
    where target_dt = '20220617'
    and region = 'HK'
    """

    df = spark.sql(statement)

    # 3. 데이터프레임을 cassandra 에 쓰기
    df.write.format("org.apache.spark.sql.cassandra").mode("append").options(table="wallet_user_segments", keyspace="featurestore").save()