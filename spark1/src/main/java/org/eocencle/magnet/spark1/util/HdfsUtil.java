package org.eocencle.magnet.spark1.util;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * hdfs操作通用类
 * @author: huan
 * @Date: 2020-01-31
 * @Description:
 */
public class HdfsUtil {
    /**
     * 删除目录
     * @Author huan
     * @Date 2020-01-31
     * @Param [dir, recursion]
     * @Return void
     * @Exception
     * @Description
     **/
    public static void delDirectory(String dir, boolean recursion) {
        try {
            FileSystem hdfs = FileSystem.get(new URI("domain"), new org.apache.hadoop.conf.Configuration());
            Path path = new Path(dir);
            if (hdfs.exists(path)) {
                hdfs.delete(path, recursion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    /**
     * 重命名目录
     * @Author huan
     * @Date 2020-01-31
     * @Param [source, target]
     * @Return boolean
     * @Exception
     * @Description
     **/
    public static boolean renameDirectory(String source, String target) {
        try {
            FileSystem hdfs = FileSystem.get(new URI("domain"), new org.apache.hadoop.conf.Configuration());
            return hdfs.rename(new Path(source), new Path(target));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

}
