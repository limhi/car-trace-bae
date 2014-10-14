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

import org.apache.log4j.Logger;
import org.java.luke.baeweb.action.ctrandom.CarPhoneRandomAction;

import com.alibaba.fastjson.JSONObject;

@Path("/ctrandom")
public class CTRandomService {
  @Context
  private ServletContext servletContext;

  @Context
  private HttpServletRequest servletRequest;
  private static final Logger logger = Logger.getLogger(CTRandomService.class);

  @Path("/cprandom/{method}")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response cprandomJsonResponse(JSONObject para, @PathParam("method") String method) throws Exception {
    logger.info("in CTRandomService, cprandomJsonResponse, para = " + para + ", method = " + method);
    return new CarPhoneRandomAction(servletContext, servletRequest, para).process(method);
  }
}
