package org.java.luke.baeweb.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.java.luke.baeweb.lib.ActionBaseImp;

import com.alibaba.fastjson.JSONObject;

public class FirstAction extends ActionBaseImp {

  public FirstAction(ServletContext servletContext, HttpServletRequest servletRequest, JSONObject para) {
    super(servletContext, servletRequest, para);
  }

  @Override
  protected Response select() throws Exception {
    String s_from_json = _para.toJSONString();
    JSONObject json = new JSONObject();
    json.put("get_para", s_from_json);
    json.put("custom_str", "aaaa");
    json.put("custom_int", 111);
    return packResponse(json);
  }

}
