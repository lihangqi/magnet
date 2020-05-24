package org.eocencle.magnet.spark1.component.handler;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.eocencle.magnet.core.mapping.DataSourceField;
import org.eocencle.magnet.core.mapping.TableInfo;
import org.eocencle.magnet.core.util.StrictMap;

/**
 * Spark表RDD优先加载器抽象类
 * @author: huan
 * @Date: 2020-02-01
 * @Description:
 */
public abstract class SparkTableRDDLoader extends SparkTableLoader {

    public SparkTableRDDLoader(TableInfo tableInfo) {
        super(tableInfo);
    }

    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-02-01
     * @Param [context, src]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    public abstract JavaRDD<Row> createRDD(JavaSparkContext context, String src);

    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-02-01
     * @Param [sqlContext, dst, rdd]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    public abstract DataFrame createDataFrame(SQLContext sqlContext, StrictMap<DataSourceField> dst, JavaRDD<Row> rdd);

}
