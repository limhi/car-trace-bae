package org.java.luke.baeweb.lib;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class myJSON {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(myJSON.class);

  private static myJSON context = null;

  private static myJSON getInstance() {
    try {
      if (context == null) {
        synchronized (myJSON.class) {
          if (context == null) {
            logger.info("first time try to get myJSON instance!");
            context = new myJSON();

          }
        }
      }
    } catch (Exception ee) {
      logger.error("into getInstance of org.alle.web.message.json.myJSON", ee); //$NON-NLS-1$
    }
    return context;
  }

  public static String toJSONString(Object obj) {
    getInstance();
    NameFilter filter = new NameFilter() {
      public String process(Object source, String name, Object value) {
        if (null != name)
          name = name.toLowerCase();

        return name;
      }
    };
    SerializeWriter out = new SerializeWriter();
    JSONSerializer serializer = new JSONSerializer(out);
    serializer.getNameFilters().add(filter);
    serializer.write(obj);

    return out.toString();
  }
}
