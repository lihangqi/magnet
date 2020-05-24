package org.eocencle.magnet.spark1.component.handler.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.*;
import org.eocencle.magnet.core.mapping.GroupInfo;
import org.eocencle.magnet.core.util.CoreTag;
import org.eocencle.magnet.spark1.component.SchemaTupleFactory;
import org.eocencle.magnet.spark1.component.SparkWorkStageResult;
import org.eocencle.magnet.spark1.component.handler.SparkGrouper;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Spark默认分组类
 * @author: huan
 * @Date: 2020-03-10
 * @Description:
 */
public class SparkDefaultGrouper implements SparkGrouper {

    @Override
    public JavaRDD<Row> createRDD(GroupInfo groupInfo, SparkWorkStageResult prevResult) {
        JavaPairRDD<Object, List<Row>> pairRdd = prevResult.getRdd().mapToPair((Row row) -> {
            List<Row> list = new ArrayList<>();
            list.add(row);
            return new Tuple2<>(SchemaTupleFactory.getSchemaTuple(groupInfo.getGroupFieldsStr(), row), list);
        }).reduceByKey((List<Row> v1, List<Row> v2) -> {
            List<Row> list = new ArrayList<>();
            list.addAll(v1);
            list.addAll(v2);
            return list;
        });

        return pairRdd.flatMap(new AfterGroupProcess(groupInfo.getOrderFields(), groupInfo.getRownumField(), prevResult.getDf().schema()));
    }

    @Override
    public DataFrame createDataFrame(SQLContext sqlContext, GroupInfo groupInfo, SparkWorkStageResult prevResult, JavaRDD<Row> rdd) {
        StructType structType = prevResult.getDf().schema();

        String rownumField = groupInfo.getRownumField();
        if (StringUtils.isNotBlank(rownumField)) {
            structType = structType.add(rownumField, DataTypes.LongType, true);
        }

        return sqlContext.createDataFrame(rdd, structType);
    }

    /**
     * 分组后进行处理类
     */
    private static class AfterGroupProcess implements FlatMapFunction<Tuple2<Object,List<Row>>, Row> {

        private List<GroupInfo.OrderField> orders;

        private String rownumField;

        private StructType structType;

        public AfterGroupProcess(List<GroupInfo.OrderField> orders, String rownumField, StructType structType) {
            this.orders = orders;
            this.rownumField = rownumField;
            this.structType = structType;
        }

        @Override
        public Iterable<Row> call(Tuple2<Object, List<Row>> tuple) throws Exception {
            Stream<Row> stream = tuple._2.stream();

            if (null != this.orders && 0 != this.orders.size()) {
                stream = stream.sorted(this.fieldSorted());
            }

            List<Row> rows = stream.collect(Collectors.toList());

            if (StringUtils.isNotBlank(this.rownumField)) {
                rows = addRownum(rows);
            }

            return rows;
        }

        /**
         * 根据字段排序
         * @return
         */
        public Comparator<Row> fieldSorted() {
            Comparator<Row> comparator = null;
            StructField[] fields = this.structType.fields();
            int fieldIndex = this.structType.fieldIndex(this.orders.get(0).getFieldName());
            DataType dataType = fields[fieldIndex].dataType();

            if (DataTypes.IntegerType == dataType) {
                comparator = Comparator.comparing((Row row) -> row.getInt(fieldIndex),
                        CoreTag.ORDERBY_ASC.equalsIgnoreCase(this.orders.get(0).getHasAsc())
                                ? Comparator.naturalOrder(): Comparator.reverseOrder());
            } else if (DataTypes.BooleanType == dataType) {
                comparator = Comparator.comparing((Row row) -> row.getBoolean(fieldIndex),
                        CoreTag.ORDERBY_ASC.equalsIgnoreCase(this.orders.get(0).getHasAsc())
                                ? Comparator.naturalOrder(): Comparator.reverseOrder());
            } else if (DataTypes.ByteType == dataType) {
                comparator = Comparator.comparing((Row row) -> row.getByte(fieldIndex),
                        CoreTag.ORDERBY_ASC.equalsIgnoreCase(this.orders.get(0).getHasAsc())
                                ? Comparator.naturalOrder(): Comparator.reverseOrder());
            } else if (DataTypes.DateType == dataType) {
                comparator = Comparator.comparing((Row row) -> row.getDate(fieldIndex),
                        CoreTag.ORDERBY_ASC.equalsIgnoreCase(this.orders.get(0).getHasAsc())
                                ? Comparator.naturalOrder(): Comparator.reverseOrder());
            } else if (DataTypes.DoubleType == dataType) {
                comparator = Comparator.comparing((Row row) -> row.getDouble(fieldIndex),
                        CoreTag.ORDERBY_ASC.equalsIgnoreCase(this.orders.get(0).getHasAsc())
                                ? Comparator.naturalOrder(): Comparator.reverseOrder());
            } else if (DataTypes.FloatType == dataType) {
                comparator = Comparator.comparing((Row row) -> row.getFloat(fieldIndex),
                        CoreTag.ORDERBY_ASC.equalsIgnoreCase(this.orders.get(0).getHasAsc())
                                ? Comparator.naturalOrder(): Comparator.reverseOrder());
            } else if (DataTypes.LongType == dataType) {
                comparator = Comparator.comparing((Row row) -> row.getLong(fieldIndex),
                        CoreTag.ORDERBY_ASC.equalsIgnoreCase(this.orders.get(0).getHasAsc())
                                ? Comparator.naturalOrder(): Comparator.reverseOrder());
            } else if (DataTypes.ShortType == dataType) {
                comparator = Comparator.comparing((Row row) -> row.getShort(fieldIndex),
                        CoreTag.ORDERBY_ASC.equalsIgnoreCase(this.orders.get(0).getHasAsc())
                                ? Comparator.naturalOrder(): Comparator.reverseOrder());
            } else if (DataTypes.TimestampType == dataType) {
                comparator = Comparator.comparing((Row row) -> row.getTimestamp(fieldIndex),
                        CoreTag.ORDERBY_ASC.equalsIgnoreCase(this.orders.get(0).getHasAsc())
                                ? Comparator.naturalOrder(): Comparator.reverseOrder());
            } else if (dataType instanceof DecimalType) {
                comparator = Comparator.comparing((Row row) -> row.getDecimal(fieldIndex),
                        CoreTag.ORDERBY_ASC.equalsIgnoreCase(this.orders.get(0).getHasAsc())
                                ? Comparator.naturalOrder(): Comparator.reverseOrder());
            } else {
                comparator = Comparator.comparing((Row row) -> row.get(fieldIndex).toString(),
                        CoreTag.ORDERBY_ASC.equalsIgnoreCase(this.orders.get(0).getHasAsc())
                                ? Comparator.naturalOrder(): Comparator.reverseOrder());
            }
            boolean isFirst = true;
            for (GroupInfo.OrderField orderField: this.orders) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                dataType = fields[this.structType.fieldIndex(orderField.getFieldName())].dataType();
                if (DataTypes.IntegerType == dataType) {
                    comparator = comparator.thenComparing((Row row) -> row.getInt(row.fieldIndex(orderField.getFieldName())),
                            CoreTag.ORDERBY_ASC.equalsIgnoreCase(orderField.getHasAsc())
                                    ? Comparator.naturalOrder(): Comparator.reverseOrder());
                } else if (DataTypes.BooleanType == dataType) {
                    comparator = comparator.thenComparing((Row row) -> row.getBoolean(row.fieldIndex(orderField.getFieldName())),
                            CoreTag.ORDERBY_ASC.equalsIgnoreCase(orderField.getHasAsc())
                                    ? Comparator.naturalOrder(): Comparator.reverseOrder());
                } else if (DataTypes.ByteType == dataType) {
                    comparator = comparator.thenComparing((Row row) -> row.getByte(row.fieldIndex(orderField.getFieldName())),
                            CoreTag.ORDERBY_ASC.equalsIgnoreCase(orderField.getHasAsc())
                                    ? Comparator.naturalOrder(): Comparator.reverseOrder());
                } else if (DataTypes.DateType == dataType) {
                    comparator = comparator.thenComparing((Row row) -> row.getDate(row.fieldIndex(orderField.getFieldName())),
                            CoreTag.ORDERBY_ASC.equalsIgnoreCase(orderField.getHasAsc())
                                    ? Comparator.naturalOrder(): Comparator.reverseOrder());
                } else if (DataTypes.DoubleType == dataType) {
                    comparator = comparator.thenComparing((Row row) -> row.getDouble(row.fieldIndex(orderField.getFieldName())),
                            CoreTag.ORDERBY_ASC.equalsIgnoreCase(orderField.getHasAsc())
                                    ? Comparator.naturalOrder(): Comparator.reverseOrder());
                } else if (DataTypes.FloatType == dataType) {
                    comparator = comparator.thenComparing((Row row) -> row.getFloat(row.fieldIndex(orderField.getFieldName())),
                            CoreTag.ORDERBY_ASC.equalsIgnoreCase(orderField.getHasAsc())
                                    ? Comparator.naturalOrder(): Comparator.reverseOrder());
                } else if (DataTypes.LongType == dataType) {
                    comparator = comparator.thenComparing((Row row) -> row.getLong(row.fieldIndex(orderField.getFieldName())),
                            CoreTag.ORDERBY_ASC.equalsIgnoreCase(orderField.getHasAsc())
                                    ? Comparator.naturalOrder(): Comparator.reverseOrder());
                } else if (DataTypes.ShortType == dataType) {
                    comparator = comparator.thenComparing((Row row) -> row.getShort(row.fieldIndex(orderField.getFieldName())),
                            CoreTag.ORDERBY_ASC.equalsIgnoreCase(orderField.getHasAsc())
                                    ? Comparator.naturalOrder(): Comparator.reverseOrder());
                } else if (DataTypes.TimestampType == dataType) {
                    comparator = comparator.thenComparing((Row row) -> row.getTimestamp(row.fieldIndex(orderField.getFieldName())),
                            CoreTag.ORDERBY_ASC.equalsIgnoreCase(orderField.getHasAsc())
                                    ? Comparator.naturalOrder(): Comparator.reverseOrder());
                } else if (dataType instanceof DecimalType) {
                    comparator = comparator.thenComparing((Row row) -> row.getDecimal(row.fieldIndex(orderField.getFieldName())),
                            CoreTag.ORDERBY_ASC.equalsIgnoreCase(orderField.getHasAsc())
                                    ? Comparator.naturalOrder(): Comparator.reverseOrder());
                } else {
                    comparator = comparator.thenComparing((Row row) -> row.get(row.fieldIndex(orderField.getFieldName())).toString(),
                            CoreTag.ORDERBY_ASC.equalsIgnoreCase(orderField.getHasAsc())
                                    ? Comparator.naturalOrder(): Comparator.reverseOrder());
                }
            }

            return comparator;
        }

        /**
         * 添加行号
         * @param r
         * @return
         */
        public List<Row> addRownum(List<Row> r) {
            ListIterator<Row> it = r.listIterator();

            Row row = null;
            Object[] fields = null;
            List<Row> rows = new ArrayList<>();
            Long idx = 1l;
            StructField[] fieldTypes = this.structType.fields();
            while (it.hasNext()) {
                row = it.next();
                fields = new Object[fieldTypes.length + 1];
                for (int i = 0; i < fieldTypes.length; i++) {
                    if (DataTypes.IntegerType == fieldTypes[i].dataType()) {
                        if (!row.isNullAt(i)) {
                            fields[i] = row.getInt(i);
                        } else {
                            fields[i] = null;
                        }
                    } else if (DataTypes.BooleanType == fieldTypes[i].dataType()) {
                        if (!row.isNullAt(i)) {
                            fields[i] = row.getBoolean(i);
                        } else {
                            fields[i] = null;
                        }
                    } else if (DataTypes.ByteType == fieldTypes[i].dataType()) {
                        if (!row.isNullAt(i)) {
                            fields[i] = row.getByte(i);
                        } else {
                            fields[i] = null;
                        }
                    } else if (DataTypes.DateType == fieldTypes[i].dataType()) {
                        if (!row.isNullAt(i)) {
                            fields[i] = row.getDate(i);
                        } else {
                            fields[i] = null;
                        }
                    } else if (DataTypes.DoubleType == fieldTypes[i].dataType()) {
                        if (!row.isNullAt(i)) {
                            fields[i] = row.getDouble(i);
                        } else {
                            fields[i] = null;
                        }
                    } else if (DataTypes.FloatType == fieldTypes[i].dataType()) {
                        if (!row.isNullAt(i)) {
                            fields[i] = row.getFloat(i);
                        } else {
                            fields[i] = null;
                        }
                    } else if (DataTypes.LongType == fieldTypes[i].dataType()) {
                        if (!row.isNullAt(i)) {
                            fields[i] = row.getLong(i);
                        } else {
                            fields[i] = null;
                        }
                    } else if (DataTypes.ShortType == fieldTypes[i].dataType()) {
                        if (!row.isNullAt(i)) {
                            fields[i] = row.getShort(i);
                        } else {
                            fields[i] = null;
                        }
                    } else if (DataTypes.TimestampType == fieldTypes[i].dataType()) {
                        if (!row.isNullAt(i)) {
                            fields[i] = row.getTimestamp(i);
                        } else {
                            fields[i] = null;
                        }
                    } else if (fieldTypes[i].dataType() instanceof DecimalType) {
                        if (!row.isNullAt(i)) {
                            DecimalType dt = ((DecimalType) fieldTypes[i].dataType());
                            fields[i] = Decimal.apply(row.getDecimal(i), dt.precision(), dt.scale());
                        } else {
                            fields[i] = null;
                        }
                    } else {
                        if (!row.isNullAt(i)) {
                            fields[i] = row.get(i).toString();
                        } else {
                            fields[i] = null;
                        }
                    }
                }
                fields[fieldTypes.length] = idx;
                idx ++;
                rows.add(RowFactory.create(fields));
            }

            return rows;
        }
    }

}
