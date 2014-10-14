package org.java.luke.baeweb.mapper;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.alibaba.fastjson.JSONObject;

@Provider
public class MyExceptionMapper implements ExceptionMapper<Exception> {

  public Response toResponse(Exception ex) {
    ResponseBuilder builder = null;
    try {
      builder = Response.status(HttpServletResponse.SC_BAD_REQUEST);
      builder.header("Content-Type", MediaType.APPLICATION_JSON + ";charset=utf-8");
      JSONObject json = new JSONObject();
      json.put("classname", ex.getClass().getName());
      json.put("message", ex.getMessage());
      // StringBuffer sb = new StringBuffer();
      // sb.append(ex.getClass().getName() + "<BR>");
      // sb.append(ex.getMessage());
      // builder.entity(sb.toString());
      builder.entity(json.toJSONString());

      ex.printStackTrace();

      return builder.build();
    } catch (Exception e) {
      ResponseBuilder b = Response.status(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
      builder.entity("補捉到錯誤! => " + e.getMessage());
      return builder.build();
    }
  }
}
