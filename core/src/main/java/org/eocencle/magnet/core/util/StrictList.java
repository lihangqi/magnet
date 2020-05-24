package org.eocencle.magnet.core.util;

import java.io.*;
import java.util.ArrayList;

/**
 * 严格规范的list
 * @author: huan
 * @Date: 2020-04-14
 * @Description:
 */
public class StrictList<T> extends ArrayList<T> {
    /**
     * 深度复制
     * @Author huan
     * @Date 2020-04-14
     * @Param []
     * @Return org.eocencle.magnet.util.StrictList<T>
     * @Exception
     * @Description
     **/
    public <T> StrictList<T> deepCopy() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(this);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        StrictList<T> dest = (StrictList<T>) in.readObject();
        return dest;
    }

}
