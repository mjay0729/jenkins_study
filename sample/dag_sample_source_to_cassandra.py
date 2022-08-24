import os
from datetime import datetime, timedelta

from airflow.models import DAG, Variable
from jutopia_airflow_plugin.jutopia_plugin.operators.git_hash_operator import GitHashOperator
from jutopia_airflow_plugin.jutopia_plugin.operators.mlu_papermill_operator import MLUPapermillOperator


# Define dag arguments
dag_id = os.path.basename(__file__).replace(".pyc", "").replace(".py", "")

default_args = {
    "owner": "jutopia",
    "start_date": datetime(2022, 7, 7),
    "schedule_interval": "@once",
    "depends_on_past" : False,
    "retries" : 2,
    "retry_delay" : timedelta(minutes=10),
    "github_cfg": {
        "access_token": Variable.get("jutopia_git_access_token", default_var=None),
        "domain": "git.linecorp.com",
        "organization": Variable.get("jutopia_git_organization", default_var=None),
        "repo": Variable.get("jutopia_git_repo", default_var=None),
        "branch": Variable.get("jutopia_git_branch", default_var="master"),
        "hash": "\\\"{{ task_instance.xcom_pull(task_ids='task_get_git_hash') }}\\\"",
    },
    "cluster_cfg": {
        "name": Variable.get("jutopia_cluster_name", default_var=None),
        "user_id": Variable.get("jutopia_cluster_user_id", default_var=None),
        "user_pw": Variable.get("jutopia_cluster_user_password", default_var=None),
        "keytab_base64": Variable.get("jutopia_cluster_keytab_base64", default_var=None),
    }
}


cassandra_hostname = Variable.get("featurestore_cassandra_hostname", default_var='10.241.0.110')
cassandra_username = Variable.get("featurestore_cassandra_username")
cassandra_password = Variable.get("featurestore_cassandra_password")


# Define task 
with DAG(
    dag_id=dag_id,
    description=dag_id,
    default_args=default_args,
    tags=["sample", "load"],
    catchup=False
    ) as dag:

    # 1. task_get_git_hash
    t1 = GitHashOperator(
        task_id="task_get_git_hash"
    )

    # 2. task_sample_source_to_cassandra
    t2 = MLUPapermillOperator(
        task_id="task_sample_source_to_cassandra",
        name="base",
        image='harbor.linecorp.com/jutopia/base/centos7/cuda11.1/cudnn8/iu/py3.6/jupyter/tf2.6-torch1.9-keras2.6-cv4.5:latest',
        jupyter_cfg={
            "papermill": {
                "kernel_name": "pyspark-driver-mem-16g",
                "start_timeout": 600, # 10 min
                "log_output": True,
            },
            "kernel": {
                "spark": {
                    "queue": "linewallet-slo",
                    "user_defined_args": [
                        "--conf spark.executor.cores=4",
                        "--conf spark.executor.memory=16g",
                        "--conf spark.driver.cores=4",
                        "--conf spark.driver.memory=16g",
                        "--conf spark.executor.instances=30",
                        "--conf spark.scheduler.mode=FAIR",
                        "--conf spark.yarn.am.cores=4",
                        "--conf spark.sql.hive.convertMetastoreOrc=true", ## enables built-in orc reader and writer for hive tables with the orc storage format
                        "--conf spark.sql.orc.enabled=true", ## enables new orc format to read/write hive tables 
                        "--conf spark.debug.maxToStringFields=100",
                        "--conf spark.hadoop.hive.exec.dynamic.partition.mode=nonstrict",
                        "--packages com.datastax.spark:spark-cassandra-connector_2.11:2.5.1",
                        "--conf spark.sql.extensions=com.datastax.spark.connector.CassandraSparkExtensions",
                        f"--conf spark.cassandra.connection.host={cassandra_hostname}",
                        f"--conf spark.cassandra.auth.password={cassandra_username}",
                        f"--conf spark.cassandra.auth.username={cassandra_password}"
                    ],
                    "python_ver": 3,
                },
            }
        },
        target={
            "step_full_dir": "sample/",
            "parameterized_yaml": """
            date: {{ ds_nodash }}
            dummy_param: {{ params.dummy_key }}
            """,
            "target_file": ["nb_sample_source_to_cassandra.ipynb"]
        },
        params={
            "dummy_key": "dummy_value -> param injected"
        },
        resources={
            "request_cpu": "4",
            "limit_cpu": "4",
            "request_memory": "16Gi",
            "limit_memory": "16Gi",
            "limit_gpu": "1"
        },        
        execution_timeout=timedelta(seconds=1800) # 30 min
    )


    # Define task order 
    t1 >> t2
