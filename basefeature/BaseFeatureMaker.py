import pymysql
import pandas as pd
from abc import ABCMeta

class AbstractMetaDB(metaclass=ABCMeta):

    def __init__(self):
        self.data = ""
        self.table_name = ""

    def operating(self):
        pass

    def db_execute(self, query):
        db_session = pymysql.connect(
            user='line',
            password='line',
            host='lab.bigle.ai',
            db='feature_store'
        )
        cursor = db_session.cursor()
        cursor.execute(query)
        result = cursor.fetchall()
        db_session.commit()
        db_session.close()
        return result


class BaseFeatureGroup(AbstractMetaDB):

    def __init__(self):
        self.data = pd.read_csv("./baseFeatureGroup.csv")
        self.query = "INSERT INTO base_feature_group(name, description, total_count, updated_interval, depth_path) VALUES('{}', '-', 0, 'D','{}')"

    def operating(self):
        for column_name, item in self.data.iterrows():
            row_query = self.query.format(item['baseFeatureGroup 이름(name)'],  item['depthPath'])
            self.db_execute(row_query)


class BaseFeature(AbstractMetaDB):
    def __init__(self):
        self.data = pd.read_csv("./baseFeature.csv")
        self._base_feature_group_select_query = "SELECT * FROM base_feature_group WHERE name = '{}'"
        self._base_feature_insert_query = "INSERT INTO  base_feature(attribute_class, description, name, type, base_feature_group_id) VALUES('{}', '{}', '{}', '{}', '{}')"

    def operating(self):
        for column_name, item in self.data.iterrows():
            select_query = self._base_feature_group_select_query.format(item['base_feature_group'])
            base_feature_group = self.db_execute(query=select_query)[0]
            insert_query = self._base_feature_insert_query.format(item["attributeClass"], item["description"], item["name"], item["type"], base_feature_group[0])
            self.db_execute(query=insert_query)

class Label(AbstractMetaDB):
    def __init__(self):
        self.data = pd.read_csv("")
        self._base_feature_group_select_query = "SELECT * FROM base_feature_group WHERE name = '{}'"
        self._base_feature_select_query = "SELECT * FROM base_feature WHERE name = {} and base_feature_group_id = {}"
        insert_query = "INSERT INTO LABEL() VALUES ()"

if __name__ == '__main__':
    base_feature = BaseFeature()
    base_feature.operating()