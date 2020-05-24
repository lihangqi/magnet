package org.eocencle.magnet.spark1.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.*;
import org.eocencle.magnet.core.mapping.DataSourceField;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.core.util.StrictMap;
import scala.Predef;
import scala.collection.JavaConverters;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * spark操作通用类
 * @author: huan
 * @Date: 2020-04-21
 * @Description:
 */
public class SparkUtil {
    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-04-22
     * @Param [context, src, separator, fieldMap]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    public static JavaRDD<Row> createRDD(JavaSparkContext context, String src, String separator, StrictMap<DataSourceField> fieldMap) {
        JavaRDD<String> lines = context.textFile(src);
        return createRDD(lines, separator, fieldMap);
    }

    /**
     * 创建RDD
     * @Author huan
     * @Date 2020-04-22
     * @Param [lines, separator, fieldMap]
     * @Return org.apache.spark.api.java.JavaRDD<org.apache.spark.sql.Row>
     * @Exception
     * @Description
     **/
    public static JavaRDD<Row> createRDD(JavaRDD<String> lines, String separator, StrictMap<DataSourceField> fieldMap) {
        return lines.map((String line) -> {
            String[] row = line.split(separator, -1);
            Object[] fields = new Object[fieldMap.size()];
            int i = 0;
            String type = null;
            for (Map.Entry<String, DataSourceField> entry: fieldMap.entrySet()) {
                type = entry.getValue().getType();
                fields[i] = rddValueMapping(type, row[i], entry.getValue().getFormat());
                i ++;
            }
            return RowFactory.create(fields);
        });
    }

    /**
     * rdd值映射
     * @Author huan
     * @Date 2020-04-28
     * @Param [type, value, format]
     * @Return java.lang.Object
     * @Exception
     * @Description
     **/
    public static Object rddValueMapping(String type, String value, String format) {
        Object field = null;
        SimpleDateFormat tagSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (CoreTag.TABLE_FIELD_TYPE_STRING.equalsIgnoreCase(type)) {
            field = value;
        } else if (CoreTag.TABLE_FIELD_TYPE_BOOLEAN.equalsIgnoreCase(type)
                || CoreTag.TABLE_FIELD_TYPE_BOOL.equalsIgnoreCase(type)) {
            if (StringUtils.isNotBlank(value)) {
                field = Boolean.valueOf(value);
            } else {
                field = null;
            }
        } else if (CoreTag.TABLE_FIELD_TYPE_DATETIME.equalsIgnoreCase(type)) {
            if (StringUtils.isNotBlank(value)) {
                SimpleDateFormat srcSdf = new SimpleDateFormat(format);
                try {
                    field = Timestamp.valueOf(tagSdf.format(srcSdf.parse(value)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                field = null;
            }
        } else if (CoreTag.TABLE_FIELD_TYPE_DOUBLE.equalsIgnoreCase(type)) {
            if (StringUtils.isNotBlank(value)) {
                field = Double.valueOf(value);
            } else {
                field = null;
            }
        } else if (CoreTag.TABLE_FIELD_TYPE_FLOAT.equalsIgnoreCase(type)) {
            if (StringUtils.isNotBlank(value)) {
                field = Float.valueOf(value);
            } else {
                field = null;
            }
        } else if (CoreTag.TABLE_FIELD_TYPE_BYTE.equalsIgnoreCase(type)) {
            if (StringUtils.isNotBlank(value)) {
                field = Byte.valueOf(value);
            } else {
                field = null;
            }
        } else if (CoreTag.TABLE_FIELD_TYPE_INTEGER.equalsIgnoreCase(type)
                || CoreTag.TABLE_FIELD_TYPE_INT.equalsIgnoreCase(type)) {
            if (StringUtils.isNotBlank(value)) {
                field = Integer.valueOf(value);
            } else {
                field = null;
            }
        } else if (CoreTag.TABLE_FIELD_TYPE_LONG.equalsIgnoreCase(type)) {
            if (StringUtils.isNotBlank(value)) {
                field = Long.valueOf(value);
            } else {
                field = null;
            }
        } else if (CoreTag.TABLE_FIELD_TYPE_SHORT.equalsIgnoreCase(type)) {
            if (StringUtils.isNotBlank(value)) {
                field = Short.valueOf(value);
            } else {
                field = null;
            }
        } else if (CoreTag.TABLE_FIELD_TYPE_DECIMAL.equalsIgnoreCase(type)) {
            if (StringUtils.isNotBlank(value)) {
                field = Decimal.apply(value);
            } else {
                field = null;
            }
        } else {
            field = value;
        }
        return field;
    }

    /**
     * 创建DataFrame
     * @Author huan
     * @Date 2020-04-22
     * @Param [sc, fieldMap, rdd]
     * @Return org.apache.spark.sql.DataFrame
     * @Exception
     * @Description
     **/
    public static DataFrame createDataFrame(SQLContext sqlContext, StrictMap<DataSourceField> fieldMap, JavaRDD<Row> rdd) {
        List<StructField> structFields = new ArrayList<StructField>();
        String name = null, type = null, p = null;
        DataType fieldType = null;
        DataSourceField field = null;
        for (Map.Entry<String, DataSourceField> entry: fieldMap.entrySet()) {
            field = entry.getValue();
            name = field.getName();
            type = field.getType();
            p = field.getPrecision();
            fieldType = dataTypeMapping(type, p);

            structFields.add(DataTypes.createStructField(name, fieldType, true));
        }
        StructType structType = DataTypes.createStructType(structFields);
        return sqlContext.createDataFrame(rdd, structType);
    }

    /**
     * DataType映射
     * @Author huan
     * @Date 2020-04-28
     * @Param [type, p]
     * @Return org.apache.spark.sql.types.DataType
     * @Exception
     * @Description
     **/
    public static DataType dataTypeMapping(String type, String p) {
        DataType fieldType = null;
        if (CoreTag.TABLE_FIELD_TYPE_STRING.equalsIgnoreCase(type)) {
            fieldType = DataTypes.StringType;
        } else if (CoreTag.TABLE_FIELD_TYPE_BINARY.equalsIgnoreCase(type)) {
            fieldType = DataTypes.BinaryType;
        } else if (CoreTag.TABLE_FIELD_TYPE_BOOLEAN.equalsIgnoreCase(type)
                || CoreTag.TABLE_FIELD_TYPE_BOOL.equalsIgnoreCase(type)) {
            fieldType = DataTypes.BooleanType;
        } else if (CoreTag.TABLE_FIELD_TYPE_DATETIME.equalsIgnoreCase(type) ||
                CoreTag.TABLE_FIELD_TYPE_TIMESTAMP.equalsIgnoreCase(type)) {
            fieldType = DataTypes.TimestampType;
        } else if (CoreTag.TABLE_FIELD_TYPE_CALENDARINTERVAL.equalsIgnoreCase(type)) {
            fieldType = DataTypes.CalendarIntervalType;
        } else if (CoreTag.TABLE_FIELD_TYPE_DOUBLE.equalsIgnoreCase(type)) {
            fieldType = DataTypes.DoubleType;
        } else if (CoreTag.TABLE_FIELD_TYPE_FLOAT.equalsIgnoreCase(type)) {
            fieldType = DataTypes.FloatType;
        } else if (CoreTag.TABLE_FIELD_TYPE_BYTE.equalsIgnoreCase(type)) {
            fieldType = DataTypes.ByteType;
        } else if (CoreTag.TABLE_FIELD_TYPE_INTEGER.equalsIgnoreCase(type)
                || CoreTag.TABLE_FIELD_TYPE_INT.equalsIgnoreCase(type)) {
            fieldType = DataTypes.IntegerType;
        } else if (CoreTag.TABLE_FIELD_TYPE_LONG.equalsIgnoreCase(type)) {
            fieldType = DataTypes.LongType;
        } else if (CoreTag.TABLE_FIELD_TYPE_SHORT.equalsIgnoreCase(type)) {
            fieldType = DataTypes.ShortType;
        } else if (CoreTag.TABLE_FIELD_TYPE_DECIMAL.equalsIgnoreCase(type)) {
            if (StringUtils.isNotBlank(p)) {
                String[] precision = p.split(CoreTag.SPLIT_COMMA);
                if (2 == precision.length) {
                    fieldType = DataTypes.createDecimalType(Integer.parseInt(precision[0]),Integer.parseInt(precision[1]));
                } else {
                    fieldType = DataTypes.createDecimalType();
                }
            } else {
                fieldType = DataTypes.createDecimalType();
            }
        } else {
            fieldType = DataTypes.NullType;
        }
        return fieldType;
    }

    /**
     * 将Java的Map转化成Scala的Map
     * @Author huan
     * @Date 2020-05-05
     * @Param [m]
     * @Return scala.collection.immutable.Map<A,B>
     * @Exception
     * @Description
     */
    public static <A,B> scala.collection.immutable.Map<A,B> toScalaMap(Map<A,B> m) {
        return JavaConverters.mapAsScalaMapConverter(m).asScala().toMap(Predef.conforms());
    }

    /**
     * 将Java的Set转化成Scala的Set
     * @Author huan
     * @Date 2020-05-05
     * @Param [s]
     * @Return scala.collection.immutable.Set<A>
     * @Exception
     * @Description
     */
    public static <A> scala.collection.immutable.Set<A> toScalaSet(Set<A> s) {
        return JavaConverters.asScalaSetConverter(s).asScala().toSet();
    }
}
