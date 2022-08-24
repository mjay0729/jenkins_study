import os
from datetime import datetime, timedelta

from airflow.models import DAG, Variable
from airflow.operators.bash import BashOperator


# Define dag arguments
dag_id = os.path.basename(__file__).replace(".pyc", "").replace(".py", "")

default_args = {
    "owner": "jutopia",
    "start_date": datetime(2022, 7, 7),
    "schedule_interval": "@once",
    "depends_on_past" : False,
    "retries" : 0,
}

gitdev_token = Variable.get("gitdev_token", default_var=None)
gitdev_repo = Variable.get("gitdev_repo", default_var=None)

gitreal_token = Variable.get("gitreal_token", default_var=None)
gitreal_repo = Variable.get("gitreal_repo", default_var=None)


# Define task 
with DAG(
    dag_id=dag_id,
    description=dag_id,
    default_args=default_args,
    tags=["sample", "gitdev_to_gitreal"],
    catchup=False
    ) as dag:

    t1 = BashOperator(
        task_id="task_gitdev_to_gitreal",
        bash_command=f"""
        mkdir temp_gitsync &&\
        cd temp_gitsync &&\
        git clone --bare https://{gitdev_token}@git-dev.linecorp.com/WRS/{gitdev_repo}.git . &&\
        git remote set-url --push origin https://{gitreal_token}@git.linecorp.com/WalletPlatformPlan/{gitreal_repo}.git &&\
        git push --mirror &&\
        cd .. &&\
        rm -rf ./temp_gitsync
        """
    )


    t1
