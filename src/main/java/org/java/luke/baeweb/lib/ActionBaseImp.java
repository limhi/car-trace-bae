package org.java.luke.baeweb.lib;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ActionBaseImp implements ActionBaseIF {
  protected ServletContext _servletContext;
  protected HttpServletRequest _servletRequest;
  protected JSONObject _para;
  protected String className;

  public ActionBaseImp(ServletContext servletContext, HttpServletRequest servletRequest, JSONObject para) {
    _servletContext = servletContext;
    _servletRequest = servletRequest;
    _para = para;
    className = this.getClass().getName();
  }

  public Response process(String method) throws Exception {
    if (StringUtils.isBlank(method)) {
      throw new Exception(className + " process() must provide method");
    }
    if ("select".equals(method)) {
      return select();
    } else if ("insert".equals(method)) {
      return insert();
    } else if ("update".equals(method)) {
      return update();
    } else if ("merge".equals(method)) {
      return merge();
    } else if ("match".equals(method)) {
      return match();
    } else if ("send".equals(method)) {
      return send();
    } else if ("delete".equals(method)) {
      return delete();
    } else {
      throw new Exception(className + " process() non-support method = " + method);
    }
  }

  protected Response select() throws Exception {
    throw new Exception(className + " select() not implements");
  }

  protected Response insert() throws Exception {
    throw new Exception(className + " insert() not implements");
  }

  protected Response update() throws Exception {
    throw new Exception(className + " update() not implements");
  }

  protected Response merge() throws Exception {
    throw new Exception(className + " merge() not implements");
  }

  protected Response match() throws Exception {
    throw new Exception(className + " match() not implements");
  }

  protected Response send() throws Exception {
    throw new Exception(className + " send() not implements");
  }

  protected Response delete() throws Exception {
    throw new Exception(className + " delete() not implements");
  }

  protected Response packResponseWithObj(Object obj) {
    JSONObject json = null;
    if (null == obj)
      json = new JSONObject();
    else
      json = JSON.parseObject(myJSON.toJSONString(obj));
    return packResponseWithJson(json);
  }

  protected Response packResponseWithJson(JSONObject json) {
    ResponseBuilder builder = Response.status(HttpServletResponse.SC_OK);
    builder.header("Content-Type", MediaType.APPLICATION_JSON + ";charset=utf-8");
    if (null == json)
      json = new JSONObject();
    builder.entity(myJSON.toJSONString(json));
    return builder.build();
  }

}
