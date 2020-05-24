package org.eocencle.magnet.core.session;

import org.eocencle.magnet.core.util.StrictMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩展配置类
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class ExpandConfig {
    // udf包
    private List<String> udfPackages = new ArrayList<>();
    // js脚本
    private StrictMap<Object> jjs = new StrictMap<Object>("Nashorn expand");

    public List<String> getUdfPackages() {
        return udfPackages;
    }

    public void setUdfPackages(List<String> udfPackages) {
        this.udfPackages = udfPackages;
    }

    public StrictMap<Object> getJjs() {
        return jjs;
    }

    public void setJjs(StrictMap<Object> jjs) {
        this.jjs = jjs;
    }
}
