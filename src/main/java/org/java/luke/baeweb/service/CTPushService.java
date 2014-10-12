package org.java.luke.baeweb.service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.java.luke.baeweb.action.ctpush.CarPhonePushNotificationAction;
import org.java.luke.baeweb.action.ctpush.PhoneCarPushNotificationAction;

import com.alibaba.fastjson.JSONObject;

@Path("/ctpush")
public class CTPushService {
  @Context
  private ServletContext servletContext;

  @Context
  private HttpServletRequest servletRequest;

  @Path("/cppn/{method}")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response cppnJsonResponse(JSONObject para, @PathParam("method") String method) throws Exception {
    return new CarPhonePushNotificationAction(servletContext, servletRequest, para).process(method);
  }

  @Path("/pcpn/{method}")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response pcpnJsonResponse(JSONObject para, @PathParam("method") String method) throws Exception {
    return new PhoneCarPushNotificationAction(servletContext, servletRequest, para).process(method);
  }
}
