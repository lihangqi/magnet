package org.eocencle.magnet.client;

import org.eocencle.magnet.client.component.WorkStageComponentFacade;
import org.eocencle.magnet.client.factory.ConfigParserFactory;
import org.eocencle.magnet.core.session.ProjectConfig;
import org.eocencle.magnet.core.validation.ValidationManager;

import java.io.FileNotFoundException;

/**
 * 主程序入口类
 * @author: huan
 * @Date: 2020-02-02
 * @Description:
 */
public class Runner {
    /**
     * 入口方法
     * @Author huan
     * @Date 2020-02-02
     * @Param [args]
     * @Return void
     * @Exception
     * @Description
     **/
    public static void main(String[] args) {
        try {
            run(args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行方法
     * @Author huan
     * @Date 2020-02-02
     * @Param [filePath]
     * @Return void
     * @Exception FileNotFoundException
     * @Description
     **/
    public static void run(String filePath) throws FileNotFoundException {
        // 获取配置信息
        ProjectConfig config = ConfigParserFactory.getProjectConfig(filePath);
        // 验证配置信息
        ValidationManager.init();
        ValidationManager.enterValid(config);
        // 配置工作组件
        WorkStageComponentFacade facade = new WorkStageComponentFacade(config);
        // 执行组件
        facade.execute();
    }
}
