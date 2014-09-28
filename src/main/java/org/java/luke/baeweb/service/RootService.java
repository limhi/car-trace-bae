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

import org.java.luke.baeweb.action.CarregAction;
import org.java.luke.baeweb.action.FirstAction;
import org.java.luke.baeweb.action.PhoneregAction;

import com.alibaba.fastjson.JSONObject;

@Path("/")
public class RootService {
  @Context
  private ServletContext servletContext;

  @Context
  private HttpServletRequest servletRequest;

  @Path("/first/{method}")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response firstJsonResponse(JSONObject para, @PathParam("method") String method) throws Exception {
    System.out.println("RootService firstJsonResponse");
    return new FirstAction(servletContext, servletRequest, para).process(method);
  }

  @Path("/carreg/{method}")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response carregJsonResponse(JSONObject para, @PathParam("method") String method) throws Exception {
    return new CarregAction(servletContext, servletRequest, para).process(method);
  }
  
  @Path("/phonereg/{method}")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response phoneregJsonResponse(JSONObject para, @PathParam("method") String method) throws Exception {
    return new PhoneregAction(servletContext, servletRequest, para).process(method);
  }
}
