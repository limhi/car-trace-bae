package org.java.luke.baeweb.action.ctpush;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.java.luke.baeweb.lib.ActionBaseImp;

import com.alibaba.fastjson.JSONObject;

public class PhoneCarPushNotificationAction extends ActionBaseImp {
  private static final Logger logger = Logger.getLogger(PhoneCarPushNotificationAction.class);

  public PhoneCarPushNotificationAction(ServletContext servletContext, HttpServletRequest servletRequest, JSONObject para) {
    super(servletContext, servletRequest, para);
  }

  @Override
  protected Response merge() throws Exception {
    JSONObject para = (JSONObject) _para.clone();

    JSONObject json = new JSONObject();
    return packResponseWithJson(json);
  }

  @Override
  protected Response match() throws Exception {
    JSONObject para = (JSONObject) _para.clone();

    JSONObject json = new JSONObject();
    return packResponseWithJson(json);
  }

}
