package org.java.luke.baeweb.lib;

import javax.ws.rs.core.Response;

public interface ActionBaseIF {
  public Response process(String method) throws Exception;
}
