package org.eocencle.magnet.core.mapping;

import java.io.Serializable;

/**
 * 作业节点信息接口
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public abstract class WorkStageInfo implements Serializable {
    // 信息id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
