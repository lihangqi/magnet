package org.eocencle.magnet.spark1.component.handler;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.core.mapping.TableInfo;

/**
 * Spark表DF优先加载器抽象类
 * @author: huan
 * @Date: 2020-02-01
 * @Description:
 */
public abstract class SparkTableDataFrameLoader extends SparkTableLoader {

    public SparkTableDataFrameLoader(TableInfo tableInfo) {
        super(tableInfo);
    }

    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-02-01
     * @Param [df]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    public abstract JavaRDD<Row> createRDD(DataFrame df);

    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-02-01
     * @Param [context, src]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    public abstract DataFrame createDataFrame(Context context, String src);

}
