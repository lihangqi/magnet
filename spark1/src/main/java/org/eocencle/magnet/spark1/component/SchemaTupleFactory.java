package org.eocencle.magnet.spark1.component;

import org.apache.spark.sql.Row;
import scala.*;

import java.util.List;

/**
 * 生成元组工厂
 * @author: huan
 * @Date: 2020-03-10
 * @Description:
 */
public class SchemaTupleFactory {
    /**
     * 生成元组
     * @Author huan
     * @Date 2020-03-10
     * @Param [fields, row]
     * @Return java.lang.Object
     * @Exception
     * @Description
     **/
    public static Object getSchemaTuple(List<String> fields, Row row) {
        Object tuple = null;
        switch (fields.size()) {
            case 1:
                tuple = new Tuple1(row.get(row.fieldIndex(fields.get(0))));
                break;
            case 2:
                tuple = new Tuple2(row.get(row.fieldIndex(fields.get(0))), row.get(row.fieldIndex(fields.get(1))));
                break;
            case 3:
                tuple = new Tuple3(row.get(row.fieldIndex(fields.get(0))), row.get(row.fieldIndex(fields.get(1))),
                        row.get(row.fieldIndex(fields.get(2))));
                break;
            case 4:
                tuple = new Tuple4(row.get(row.fieldIndex(fields.get(0))), row.get(row.fieldIndex(fields.get(1))),
                        row.get(row.fieldIndex(fields.get(2))), row.get(row.fieldIndex(fields.get(3))));
                break;
            case 5:
                tuple = new Tuple5(row.get(row.fieldIndex(fields.get(0))), row.get(row.fieldIndex(fields.get(1))),
                        row.get(row.fieldIndex(fields.get(2))), row.get(row.fieldIndex(fields.get(3))),
                        row.get(row.fieldIndex(fields.get(4))));
                break;
            case 6:
                tuple = new Tuple6(row.get(row.fieldIndex(fields.get(0))), row.get(row.fieldIndex(fields.get(1))),
                        row.get(row.fieldIndex(fields.get(2))), row.get(row.fieldIndex(fields.get(3))),
                        row.get(row.fieldIndex(fields.get(4))), row.get(row.fieldIndex(fields.get(5))));
                break;
            case 7:
                tuple = new Tuple7(row.get(row.fieldIndex(fields.get(0))), row.get(row.fieldIndex(fields.get(1))),
                        row.get(row.fieldIndex(fields.get(2))), row.get(row.fieldIndex(fields.get(3))),
                        row.get(row.fieldIndex(fields.get(4))), row.get(row.fieldIndex(fields.get(5))),
                        row.get(row.fieldIndex(fields.get(6))));
                break;
            case 8:
                tuple = new Tuple8(row.get(row.fieldIndex(fields.get(0))), row.get(row.fieldIndex(fields.get(1))),
                        row.get(row.fieldIndex(fields.get(2))), row.get(row.fieldIndex(fields.get(3))),
                        row.get(row.fieldIndex(fields.get(4))), row.get(row.fieldIndex(fields.get(5))),
                        row.get(row.fieldIndex(fields.get(6))), row.get(row.fieldIndex(fields.get(7))));
                break;
            case 9:
                tuple = new Tuple9(row.get(row.fieldIndex(fields.get(0))), row.get(row.fieldIndex(fields.get(1))),
                        row.get(row.fieldIndex(fields.get(2))), row.get(row.fieldIndex(fields.get(3))),
                        row.get(row.fieldIndex(fields.get(4))), row.get(row.fieldIndex(fields.get(5))),
                        row.get(row.fieldIndex(fields.get(6))), row.get(row.fieldIndex(fields.get(7))),
                        row.get(row.fieldIndex(fields.get(8))));
                break;
            case 10:
                tuple = new Tuple10(row.get(row.fieldIndex(fields.get(0))), row.get(row.fieldIndex(fields.get(1))),
                        row.get(row.fieldIndex(fields.get(2))), row.get(row.fieldIndex(fields.get(3))),
                        row.get(row.fieldIndex(fields.get(4))), row.get(row.fieldIndex(fields.get(5))),
                        row.get(row.fieldIndex(fields.get(6))), row.get(row.fieldIndex(fields.get(7))),
                        row.get(row.fieldIndex(fields.get(8))), row.get(row.fieldIndex(fields.get(9))));
                break;
            default:
                break;
        }

        return tuple;
    }

}
