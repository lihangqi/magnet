package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.compress.*;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.mapping.OutputInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.spark1.util.HdfsUtil;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkOutputer;

/**
 * Spark文件输出器类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class SparkFileOutputer implements SparkOutputer {
    @Override
    public WorkStageResult output(OutputInfo outputInfo, SparkWorkStageResult result) {
        // 处理数据内容，去掉SQL执行后的null
        JavaRDD result_rdd = result.getRdd().map((Row row) -> {
            String[] val = new String[row.length()];
            for (int i = 0; i < row.length(); i++) {
                if (row.isNullAt(i)) {
                    val[i] = CoreTag.STRING_BLANK;
                } else {
                    val[i] = row.get(i).toString();
                }
            }
            return StringUtils.join(val, outputInfo.getSeparator());
        });

        // 根据输出类型和压缩格式输出
        if (CoreTag.OUTPUT_TYPE_CREATE.equalsIgnoreCase(outputInfo.getType())) {
            this.compress(result_rdd, outputInfo.getTarget(), outputInfo.getCompress());
        } else if (CoreTag.OUTPUT_TYPE_OVERRIDE.equalsIgnoreCase(outputInfo.getType())) {
            String tmpDir = outputInfo.getTarget() + CoreTag.STRING_UNDERLINE + System.currentTimeMillis();

            this.compress(result_rdd, tmpDir, outputInfo.getCompress());

            HdfsUtil.delDirectory(outputInfo.getTarget(), true);
            HdfsUtil.renameDirectory(tmpDir, outputInfo.getTarget());
        } else {
            throw new RuntimeException("output的type属性配置有误！");
        }
        return null;
    }

    /**
     * 压缩输出
     * @Author huan
     * @Date 2020-01-31
     * @Param [rdd, dir, compress]
     * @Return void
     * @Exception
     * @Description
     **/
    private void compress(JavaRDD<Row> rdd, String dir, String compress) {
        if (CoreTag.COMPRESS_NONE.equalsIgnoreCase(compress)) {
            rdd.saveAsTextFile(dir);
        } else if (CoreTag.COMPRESS_SNAPPY.equalsIgnoreCase(compress)) {
            rdd.saveAsTextFile(dir, SnappyCodec.class);
        } else if (CoreTag.COMPRESS_DEFAULT.equalsIgnoreCase(compress)) {
            rdd.saveAsTextFile(dir, DefaultCodec.class);
        } else if (CoreTag.COMPRESS_GZIP.equalsIgnoreCase(compress)) {
            rdd.saveAsTextFile(dir, GzipCodec.class);
        } else if (CoreTag.COMPRESS_BZIP2.equalsIgnoreCase(compress)) {
            rdd.saveAsTextFile(dir, BZip2Codec.class);
        } else if (CoreTag.COMPRESS_LZ4.equalsIgnoreCase(compress)) {
            rdd.saveAsTextFile(dir, Lz4Codec.class);
        }
    }
}
