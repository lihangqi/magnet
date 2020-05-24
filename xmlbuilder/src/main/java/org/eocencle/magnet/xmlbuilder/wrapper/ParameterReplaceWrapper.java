package org.eocencle.magnet.xmlbuilder.wrapper;

import org.eocencle.magnet.core.component.WorkStageComponent;
import org.eocencle.magnet.core.component.WorkStageParameter;
import org.eocencle.magnet.core.component.WorkStageResult;
import org.eocencle.magnet.core.component.wrapper.WorkStageComponentWrapper;
import org.eocencle.magnet.core.exception.UnsupportedException;
import org.eocencle.magnet.core.mapping.WorkStageInfo;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.xmlbuilder.parsing.GenericTokenParser;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 参数替换包装类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class ParameterReplaceWrapper extends WorkStageComponentWrapper {
    // 标识符查找器
    private GenericTokenParser parser;
    // 参数集合
    private StrictMap<Object> params;

    public ParameterReplaceWrapper(WorkStageComponent workStageComponent) {
        super(workStageComponent);
    }

    @Override
    public void before(WorkStageParameter parameter) {
        try {
            this.params = this.getParent().getParams();
            this.parser = new GenericTokenParser("${", "}", (String content) -> {
                    Object value = params.get(content);
                    if (value instanceof String) {
                        return value.toString();
                    } else {
                        throw new UnsupportedException("Types other than string are not supported!");
                    }
            });
            WorkStageInfo info = this.getWorkStageInfo();
            this.replace(info, info.getClass());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void after(WorkStageParameter parameter, List<WorkStageResult> results) {

    }

    /**
     * 遍历每个字段进行替换
     * @Author huan
     * @Date 2020-01-31
     * @Param [info, clazz]
     * @Return void
     * @Exception IllegalAccessException
     * @Description
     **/
    private void replace(WorkStageInfo info, Class clazz) throws IllegalAccessException {
        // 判断是否有父类，如果有则递归调用
        Class parentClass = clazz.getSuperclass();
        if (null != parentClass) {
            this.replace(info, parentClass);
        }

        // 获取字段并进行替换
        Field[] fields = clazz.getDeclaredFields();
        String fieldType = null;
        for (Field field: fields) {
            field.setAccessible(true);
            fieldType = field.getType().getName();
            if (null == field.get(info)) continue;
            if ("java.lang.String".equals(fieldType)) {
                field.set(info, this.parser.parse(field.get(info).toString()));
            } else if ("java.util.List".equals(fieldType)) {
                List<WorkStageInfo> list = (List<WorkStageInfo>) field.get(info);
                for (WorkStageInfo obj: list) {
                    this.replace(obj, obj.getClass());
                }
            } else if ("org.eocencle.magnet.util.StrictMap".equals(fieldType)) {
                StrictMap<WorkStageInfo> map = (StrictMap<WorkStageInfo>) field.get(info);
                for (Map.Entry<String, WorkStageInfo> entry: map.entrySet()) {
                    this.replace(entry.getValue(), entry.getValue().getClass());
                    map.put(this.parser.parse(entry.getKey()), entry.getValue(), true);
                }
            }
        }
    }
}
