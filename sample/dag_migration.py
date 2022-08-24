import os
from datetime import datetime, timedelta

from airflow.models import DAG, Variable
from jutopia_airflow_plugin.jutopia_plugin.operators.git_hash_operator import GitHashOperator
from jutopia_airflow_plugin.jutopia_plugin.operators.mlu_papermill_operator import MLUPapermillOperator


# Define dag arguments
dag_id = os.path.basename(__file__).replace(".pyc", "").replace(".py", "")

default_args = {
    "owner": "jutopia",
    "start_date": datetime(2022, 7, 23),
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


cassandra_hostname = Variable.get(
    "featurestore_cassandra_hostname_cluster",
    default_var='10.241.0.110'
)
cassandra_username = Variable.get("featurestore_cassandra_username")
cassandra_password = Variable.get("featurestore_cassandra_password")

# Define task 
with DAG(
    dag_id=dag_id,
    description=dag_id,
    default_args=default_args,
    tags=["migration", "legacy_features"],
    schedule_interval="0 19 * * *",
    catchup=True
    ) as dag:

    # 0. task_get_git_hash
    t0 = GitHashOperator(
        task_id="task_get_git_hash"
    )
    
    # 1. task_demographics_to_cassandra
    t1 = MLUPapermillOperator(
        task_id="task_demographics_to_cassandra",
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
                        f"--conf spark.cassandra.auth.username={cassandra_username}",
                        f"--conf spark.cassandra.auth.password={cassandra_password}"

                    ],
                    "python_ver": 3,
                },
            }
        },
        target={
            "step_full_dir": "migration/",
            "parameterized_yaml": """
            date: {{ ds_nodash }}
            dummy_param: {{ params.dummy_key }}
            """,
            "target_file": ["nb_migration_demographcis.ipynb"]
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

    t2 = MLUPapermillOperator(
        task_id="task_activity_mobile_activities_to_cassandra",
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
                        f"--conf spark.cassandra.auth.username={cassandra_username}",
                        f"--conf spark.cassandra.auth.password={cassandra_password}"
                    ],
                    "python_ver": 3,
                },
            }
        },
        target={
            "step_full_dir": "migration/",
            "parameterized_yaml": """
            date: {{ ds_nodash }}
            dummy_param: {{ params.dummy_key }}
            """,
            "target_file": ["nb_migration_activity_mobile_activities.ipynb"]
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
    
    t3 = MLUPapermillOperator(
        task_id="task_relationship_friends_to_cassandra",
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
                        f"--conf spark.cassandra.auth.username={cassandra_username}",
                        f"--conf spark.cassandra.auth.password={cassandra_password}"
                    ],
                    "python_ver": 3,
                },
            }
        },
        target={
            "step_full_dir": "migration/",
            "parameterized_yaml": """
            date: {{ ds_nodash }}
            dummy_param: {{ params.dummy_key }}
            """,
            "target_file": ["nb_migration_relationship_friends.ipynb"]
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

    t4 = MLUPapermillOperator(
        task_id="task_service_activities_wallet_common_to_cassandra",
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
                        f"--conf spark.cassandra.auth.username={cassandra_username}",
                        f"--conf spark.cassandra.auth.password={cassandra_password}"
                    ],
                    "python_ver": 3,
                },
            }
        },
        target={
            "step_full_dir": "migration/",
            "parameterized_yaml": """
            date: {{ ds_nodash }}
            dummy_param: {{ params.dummy_key }}
            """,
            "target_file": ["nb_migration_service_activities_wallet_common.ipynb"]
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
        execution_timeout=timedelta(seconds=7200) # 120 min
    )

    t5 = MLUPapermillOperator(
        task_id="task_service_activities_pointclub_to_cassandra",
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
                        f"--conf spark.cassandra.auth.username={cassandra_username}",
                        f"--conf spark.cassandra.auth.password={cassandra_password}"
                    ],
                    "python_ver": 3,
                },
            }
        },
        target={
            "step_full_dir": "migration/",
            "parameterized_yaml": """
            date: {{ ds_nodash }}
            dummy_param: {{ params.dummy_key }}
            """,
            "target_file": ["nb_migration_service_activities_pointclub.ipynb"]
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

    t6 = MLUPapermillOperator(
        task_id="task_service_activities_coupon_to_cassandra",
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
                        f"--conf spark.cassandra.auth.username={cassandra_username}",
                        f"--conf spark.cassandra.auth.password={cassandra_password}"
                    ],
                    "python_ver": 3,
                },
            }
        },
        target={
            "step_full_dir": "migration/",
            "parameterized_yaml": """
            date: {{ ds_nodash }}
            dummy_param: {{ params.dummy_key }}
            """,
            "target_file": ["nb_migration_service_activities_coupon.ipynb"]
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

    t7 = MLUPapermillOperator(
        task_id="task_service_activities_mycards_to_cassandra",
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
                        f"--conf spark.cassandra.auth.username={cassandra_username}",
                        f"--conf spark.cassandra.auth.password={cassandra_password}"
                    ],
                    "python_ver": 3,
                },
            }
        },
        target={
            "step_full_dir": "migration/",
            "parameterized_yaml": """
            date: {{ ds_nodash }}
            dummy_param: {{ params.dummy_key }}
            """,
            "target_file": ["nb_migration_service_activities_mycards.ipynb"]
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
    
    t8 = MLUPapermillOperator(
        task_id="task_service_activities_ec_to_cassandra",
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
                        f"--conf spark.cassandra.auth.username={cassandra_username}",
                        f"--conf spark.cassandra.auth.password={cassandra_password}"
                    ],
                    "python_ver": 3,
                },
            }
        },
        target={
            "step_full_dir": "migration/",
            "parameterized_yaml": """
            date: {{ ds_nodash }}
            dummy_param: {{ params.dummy_key }}
            """,
            "target_file": ["nb_migration_service_activities_EC.ipynb"]
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
        execution_timeout=timedelta(seconds=3600) # 60 min
    )

    t9 = MLUPapermillOperator(
        task_id="task_service_activities_chirashi_to_cassandra",
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
                        f"--conf spark.cassandra.auth.username={cassandra_username}",
                        f"--conf spark.cassandra.auth.password={cassandra_password}"
                    ],
                    "python_ver": 3,
                },
            }
        },
        target={
            "step_full_dir": "migration/",
            "parameterized_yaml": """
            date: {{ ds_nodash }}
            dummy_param: {{ params.dummy_key }}
            """,
            "target_file": ["nb_migration_service_activities_chirashi.ipynb"]
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
    
    t10 = MLUPapermillOperator(
        task_id="task_service_activities_stockindex_to_cassandra",
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
                        f"--conf spark.cassandra.auth.username={cassandra_username}",
                        f"--conf spark.cassandra.auth.password={cassandra_password}"
                    ],
                    "python_ver": 3,
                },
            }
        },
        target={
            "step_full_dir": "migration/",
            "parameterized_yaml": """
            date: {{ ds_nodash }}
            dummy_param: {{ params.dummy_key }}
            """,
            "target_file": ["nb_migration_service_activities_stockindex.ipynb"]
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

    t11 = MLUPapermillOperator(
        task_id="task_service_activities_ladm_to_cassandra",
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
                        f"--conf spark.cassandra.auth.username={cassandra_username}",
                        f"--conf spark.cassandra.auth.password={cassandra_password}"
                    ],
                    "python_ver": 3,
                },
            }
        },
        target={
            "step_full_dir": "migration/",
            "parameterized_yaml": """
            date: {{ ds_nodash }}
            dummy_param: {{ params.dummy_key }}
            """,
            "target_file": ["nb_migration_service_activities_ladm.ipynb"]
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
    t0 >> [t1, t4]
    t1 >> t2 >> t3
    t4 >> t5 >> t6 >> t7 >> t8 >> t9 >> t10 >> t11