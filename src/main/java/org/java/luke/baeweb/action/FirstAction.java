package org.java.luke.baeweb.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.java.luke.baeweb.lib.ActionBaseImp;

import com.alibaba.fastjson.JSONObject;

public class FirstAction extends ActionBaseImp {
  private static final Logger logger = Logger.getLogger(FirstAction.class);

  public FirstAction(ServletContext servletContext, HttpServletRequest servletRequest, JSONObject para) {
    super(servletContext, servletRequest, para);
    System.out.println("into FirstAction");
  }

  @Override
  protected Response select() throws Exception {
    System.out.println("into FirstAction.select()");
    logger.info("into FirstAction.select()");
    String s_from_json = _para.toJSONString();
    JSONObject json = new JSONObject();
    json.put("get_para", s_from_json);
    json.put("custom_str", "aaaa");
    json.put("custom_int", 111);
    return packResponseWithJson(json);
  }

}
