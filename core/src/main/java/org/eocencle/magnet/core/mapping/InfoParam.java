package org.eocencle.magnet.core.mapping;

import org.eocencle.magnet.core.util.StrictMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 信息参数类
 * @author: huan
 * @Date: 2020-03-01
 * @Description:
 */
public class InfoParam extends WorkStageInfo {
    // key值
    private String key;
    // value值
    private String value;
    // list值
    private List<String> list = new ArrayList<>();
    // map值
    private StrictMap<String> map = new StrictMap<>("Map params");

    public InfoParam() {
    }

    public InfoParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public StrictMap<String> getMap() {
        return map;
    }

    public void setMap(StrictMap<String> map) {
        this.map = map;
    }
}
