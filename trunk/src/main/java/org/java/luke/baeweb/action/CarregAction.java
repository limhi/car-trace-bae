package org.java.luke.baeweb.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.java.luke.baedb.service.MyApplicationContext;
import org.java.luke.baeweb.lib.ActionBaseImp;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

public class CarregAction extends ActionBaseImp {

  public CarregAction(ServletContext servletContext, HttpServletRequest servletRequest, JSONObject para) {
    super(servletContext, servletRequest, para);
  }

  @Override
  protected Response select() throws Exception {
    JSONObject para = (JSONObject) _para.clone();
    
    
    
    
    JSONObject json = new JSONObject();
    return packResponse(json);
  }

}
