package org.eocencle.magnet.spark1.context;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.hive.HiveContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.eocencle.magnet.core.context.Context;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.io.Resources;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.extend.Register;

/**
 * Spark1.6.0版执行环境
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class Spark1Context extends Context {
    // spark配置
    private SparkConf sparkConf;
    // spark上下文
    private JavaSparkContext sc;
    // sparkSQL上下文
    private SQLContext sqlContext;
    // sparkStream上下文
    private JavaStreamingContext ssc;

    @Override
    public void start() {
        this.sparkConf = new SparkConf().setAppName(this.getAppName());
        // 环境模式判断
        if (this.getEnvMode().toUpperCase().startsWith(CoreTag.ENV_MODE_LOCAL.toUpperCase())) {
            this.sparkConf.setMaster(this.getEnvMode().toLowerCase());
        }

        // 执行模式判断
        if (CoreTag.PROCESS_MODE_BATCH.equalsIgnoreCase(this.getProcessMode())) {
            this.sc = new JavaSparkContext(this.sparkConf);
            if (CoreTag.SQL_ENGINE_SPARK.equalsIgnoreCase(this.getSqlEngine())) {
                this.sqlContext = new SQLContext(this.sc);
            } else if (CoreTag.SQL_ENGINE_HIVE.equalsIgnoreCase(this.getSqlEngine())) {
                this.sqlContext = new HiveContext(this.sc);
            } else {
                throw new UnsupportedException(this.getSqlEngine() + " SQL engine not supported");
            }
        } else if (CoreTag.PROCESS_MODE_STREAM.equalsIgnoreCase(this.getProcessMode())) {
            this.ssc = new JavaStreamingContext(this.sparkConf, Durations.seconds(this.getDuration()));
            this.sc = this.ssc.sparkContext();
            if (CoreTag.SQL_ENGINE_SPARK.equalsIgnoreCase(this.getSqlEngine())) {
                this.sqlContext = new SQLContext(this.ssc.sparkContext());
            } else if (CoreTag.SQL_ENGINE_HIVE.equalsIgnoreCase(this.getSqlEngine())) {
                this.sqlContext = new HiveContext(this.ssc.sparkContext());
            } else {
                throw new UnsupportedException(this.getSqlEngine() + " SQL engine not supported");
            }
        } else {
            throw new UnsupportedException("Unknown execution mode " + this.getProcessMode());
        }

        // 注册自定义函数
        if (StringUtils.isNoneBlank(this.getExtendRegister())) {
            try {
                Class<?> clazz = Resources.classForName(this.getExtendRegister());
                Register register = (Register) clazz.newInstance();
                register.regSQLFunc(this.sqlContext.udf());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        if (CoreTag.PROCESS_MODE_BATCH.equalsIgnoreCase(this.getProcessMode())) {
            this.sc.stop();
        } else if (CoreTag.PROCESS_MODE_STREAM.equalsIgnoreCase(this.getProcessMode())) {
            this.ssc.start();
            this.ssc.awaitTermination();
            this.ssc.close();
        }
    }

    @Override
    public Object getContext() {
        return this.sc;
    }

    @Override
    public Object getSQLContext() {
        return this.sqlContext;
    }

    @Override
    public Object getStreamContext() {
        return this.ssc;
    }
}
