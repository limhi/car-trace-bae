package org.java.luke.baeweb.action.ctreg;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.java.luke.baeweb.lib.ActionBaseImp;

import com.alibaba.fastjson.JSONObject;

public class PhoneRegAction extends ActionBaseImp {

  public PhoneRegAction(ServletContext servletContext, HttpServletRequest servletRequest, JSONObject para) {
    super(servletContext, servletRequest, para);
  }

  @Override
  protected Response merge() throws Exception {
    JSONObject para = (JSONObject) _para.clone();

    JSONObject json = new JSONObject();
    return packResponse(json);
  }

}
