package org.java.luke.baeweb.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.java.luke.baedb.model.Phonereg;
import org.java.luke.baedb.model.PhoneregCriteria;
import org.java.luke.baedb.service.MyApplicationContext;
import org.java.luke.baedb.service.PhoneregService;
import org.java.luke.baeweb.lib.ActionBaseImp;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

public class PhoneregAction extends ActionBaseImp {

  public PhoneregAction(ServletContext servletContext, HttpServletRequest servletRequest, JSONObject para) {
    super(servletContext, servletRequest, para);
  }

  @Override
  protected Response select() throws Exception {
    JSONObject para = (JSONObject) _para.clone();

    PhoneregService service = MyApplicationContext.getInstance().getBean(PhoneregService.class);
    PhoneregCriteria cri = new PhoneregCriteria();
    List<Phonereg> list = Lists.newArrayList();
    Phonereg bean = new Phonereg();
    int count = 0;

    cri.clear();
    // count = service.countByExample(cri);
    // list = service.selectByExample(cri, 0, count-1);
    list = service.selectByExample(cri);

    JSONObject json = new JSONObject();
    json.put("results", list);
    return packResponse(json);
  }

}
