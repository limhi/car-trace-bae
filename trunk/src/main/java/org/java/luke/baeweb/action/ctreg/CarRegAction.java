package org.java.luke.baeweb.action.ctreg;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.java.luke.baedb.model.Carreg;
import org.java.luke.baedb.model.CarregCriteria;
import org.java.luke.baedb.service.CarregService;
import org.java.luke.baedb.service.MyApplicationContext;
import org.java.luke.baeweb.lib.ActionBaseImp;
import org.java.luke.baeweb.lib.CTCommon;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

public class CarRegAction extends ActionBaseImp {
  private static final Logger logger = Logger.getLogger(CarRegAction.class);

  public CarRegAction(ServletContext servletContext, HttpServletRequest servletRequest, JSONObject para) {
    super(servletContext, servletRequest, para);
  }

  @Override
  protected Response merge() throws Exception {
    JSONObject para = (JSONObject) _para.clone();

    String deviceid = para.getString("deviceid");
    String appversion = para.getString("appversion");
    String registerid = para.getString("registerid");
    String senderid = para.getString("senderid");

    // check deviceID, appVersion
    if (StringUtils.isBlank(deviceid)) {
      throw new Exception("deviceid must provided");
    }
    if (StringUtils.isBlank(appversion)) {
      throw new Exception("appversion must provided");
    }
    if (StringUtils.isBlank(registerid)) {
      throw new Exception("registerid must provided");
    }
    if (StringUtils.isBlank(senderid)) {
      throw new Exception("senderid must provided");
    }

    CarregService service = MyApplicationContext.getInstance().getBean(CarregService.class);
    CarregCriteria cri = new CarregCriteria();
    List<Carreg> list = Lists.newArrayList();
    Carreg bean = new Carreg();
    int count = 0;

    cri.clear();
    cri.or().andDeviceIDEqualTo(deviceid).andAppVersionEqualTo(appversion);
    count = service.countByExample(cri);
    String dateStr = CTCommon.getNowTime();
    if (count == 0) {
      // 第一次註冊，新增資料
      bean.setAppVersion(appversion);
      bean.setDeviceID(deviceid);
      bean.setDeviceIP(_servletRequest.getRemoteAddr());
      bean.setRegisterID(registerid);
      bean.setSenderID(senderid);

      bean.setAddTime(dateStr);
      bean.setModTime(dateStr);
      service.insert(bean);
      // log.info("新增一筆設備註冊記錄：" + JSON.toJSONString(retCR));
    } else if (count == 1) {
      bean = service.selectByExample(cri).get(0);
      bean.setAppVersion(appversion);
      bean.setDeviceID(deviceid);
      bean.setDeviceIP(_servletRequest.getRemoteAddr());
      bean.setRegisterID(registerid);
      bean.setSenderID(senderid);

      bean.setModTime(dateStr);
      service.updateByExample(bean, cri);
      // log.info("修改一筆設備註冊記錄：" + JSON.toJSONString(retCR));
    } else {
      // log.info("設備記錄數目異常，不處理");s
      throw new Exception("設備記錄數目異常，不處理");
    }

    return packResponseWithObj(bean);
  }
}
