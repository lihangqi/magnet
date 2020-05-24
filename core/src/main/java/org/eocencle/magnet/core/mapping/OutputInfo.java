package org.eocencle.magnet.core.mapping;

import org.eocencle.magnet.core.util.StrictMap;

/**
 * 输出信息类
 * @author: huan
 * @Date: 2020-01-19
 * @Description:
 */
public class OutputInfo extends WorkFlowInfo {
    // 引用id
    private String ref;
    // 输出方式
    private String style;
    // 目标地址
    private String target;
    // 写入类型
    private String type;
    // 压缩格式
    private String compress;
    // 分隔符
    private String separator;
    // 输出配置
    private StrictMap<InfoParam> outputConfig = new StrictMap<InfoParam>("Output params");

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompress() {
        return compress;
    }

    public void setCompress(String compress) {
        this.compress = compress;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public StrictMap<InfoParam> getOutputConfig() {
        return outputConfig;
    }

    public void setOutputConfig(StrictMap<InfoParam> outputConfig) {
        this.outputConfig = outputConfig;
    }

    public void putOutputConfig(InfoParam param) {
        this.outputConfig.put(param.getKey(), param);
    }

    public String getOutputConfig(String key) {
        return this.outputConfig.get(key).getValue();
    }
}
