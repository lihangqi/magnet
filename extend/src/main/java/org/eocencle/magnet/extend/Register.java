package org.eocencle.magnet.extend;

import org.apache.spark.sql.UDFRegistration;

import java.util.List;

/**
 * 自定义函数注册接口
 * @author: huan
 * @Date: 2020-06-04
 * @Description:
 */
public interface Register {
    List<FunctionWrapper> regFunc(List<FunctionWrapper> list);

    void regSQLFunc(UDFRegistration register);
}
