package org.eocencle.magnet.xmlbuilder.session;

import org.eocencle.magnet.core.session.ProjectConfig;
import org.eocencle.magnet.core.util.StrictMap;
import org.eocencle.magnet.xmlbuilder.parsing.XNode;

/**
 * ML项目配置类
 * @author: huan
 * @Date: 2020-04-15
 * @Description:
 */
public class XmlProjectConfig extends ProjectConfig {
    // 碎片信息
    private StrictMap<XNode> fragmentInfo = new StrictMap<XNode>("Fragment Info");

    public void putFragmentInfo(String id, XNode node) {
        this.fragmentInfo.put(id, node);
    }

    public XNode getFragmentInfo(String id) {
        return this.fragmentInfo.get(id);
    }

    public StrictMap<XNode> getFragmentInfo() {
        return fragmentInfo;
    }

    public void setFragmentInfo(StrictMap<XNode> scriptInfo) {
        this.fragmentInfo = scriptInfo;
    }
}
