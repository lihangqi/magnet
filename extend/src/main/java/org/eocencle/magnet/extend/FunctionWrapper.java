package org.eocencle.magnet.extend;

/**
 * 自定义函数封装类
 * @author: huan
 * @Date: 2020-06-04
 * @Description:
 */
public class FunctionWrapper {
    // 注册名称
    private String regName;
    // 函数class
    private Class<?> funcClass;

    public FunctionWrapper(String regName, Class<?> funcClass) {
        this.regName = regName;
        this.funcClass = funcClass;
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    public Class<?> getFuncClass() {
        return funcClass;
    }

    public void setFuncClass(Class<?> funcClass) {
        this.funcClass = funcClass;
    }
}
