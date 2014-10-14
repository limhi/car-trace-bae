package org.java.luke.baeweb.action.ctrandom;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.java.luke.baedb.model.Carreg;
import org.java.luke.baedb.model.CarregCriteria;
import org.java.luke.baedb.model.Cprannum;
import org.java.luke.baedb.model.CprannumCriteria;
import org.java.luke.baedb.model.Cprelation;
import org.java.luke.baedb.model.CprelationCriteria;
import org.java.luke.baedb.model.Phonereg;
import org.java.luke.baedb.model.PhoneregCriteria;
import org.java.luke.baedb.service.CarregService;
import org.java.luke.baedb.service.CprannumService;
import org.java.luke.baedb.service.CprelationService;
import org.java.luke.baedb.service.MyApplicationContext;
import org.java.luke.baedb.service.PhoneregService;
import org.java.luke.baeweb.lib.ActionBaseImp;
import org.java.luke.baeweb.lib.CTCommon;
import org.java.luke.baeweb.lib.myJSON;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

public class CarPhoneRandomAction extends ActionBaseImp {
  private static final Logger logger = Logger.getLogger(CarPhoneRandomAction.class);

  public CarPhoneRandomAction(ServletContext servletContext, HttpServletRequest servletRequest, JSONObject para) {
    super(servletContext, servletRequest, para);
  }

  @Override
  protected Response merge() throws Exception {
    JSONObject para = (JSONObject) _para.clone();
    CleanOutOfDate();

    String carid = para.getString("carid");
    Boolean isprimary = para.getBoolean("isprimary");

    if (StringUtils.isBlank(carid)) {
      throw new Exception("carid must provided");
    }
    if (null == isprimary) {
      throw new Exception("isprimary must provided");
    }

    CarregService car_service = MyApplicationContext.getInstance().getBean(CarregService.class);
    CarregCriteria car_cri = new CarregCriteria();
    List<Carreg> car_list = Lists.newArrayList();
    Carreg car_bean = new Carreg();
    int car_count = 0;

    CprannumService ran_service = MyApplicationContext.getInstance().getBean(CprannumService.class);
    CprannumCriteria ran_cri = new CprannumCriteria();
    List<Cprannum> ran_list = Lists.newArrayList();
    Cprannum ran_bean = new Cprannum();
    int ran_count = 0;

    JSONObject json = null;
    String dateStr = CTCommon.getNowTime();

    car_cri.clear();
    car_cri.or().andEncodedKeyEqualTo(carid);
    car_count = car_service.countByExample(car_cri);
    // 該carID已註冊
    if (car_count > 0) {
      logger.info("該carid已註冊");
      ran_cri.clear();
      ran_cri.or().andCarIDEqualTo(carid).andDeadTimeGreaterThanOrEqualTo(dateStr);
      ran_count = ran_service.countByExample(ran_cri);
      if (ran_count > 0) {
        logger.info("該CarPhoneRandomNumber尚未到期");
        ran_bean = ran_service.selectByExample(ran_cri, 0, 0).get(0);
        json = JSON.parseObject(myJSON.toJSONString(ran_bean));
      } else {
        // 先檢查是否有相同的關結
        boolean notOnly = true;
        int count = 0;
        do {
          ran_bean = new Cprannum();
          ran_bean.setIsPrimary(isprimary ? "1" : "0");
          ran_bean.setCarID(carid);
          ran_bean.setRandomID(generateRandom());

          ran_cri.clear();
          ran_cri.or().andRandomIDEqualTo(ran_bean.getRandomID()).andDeadTimeGreaterThanOrEqualTo(dateStr);
          ran_count = ran_service.countByExample(ran_cri);
          if (ran_count == 0) {
            ran_bean.setDeadTime(CTCommon.getDeadTime(Calendar.MINUTE, 5));
            ran_service.insert(ran_bean);
            logger.info("產生新的CarPhoneRandomNumber:" + myJSON.toJSONString(ran_bean));
            notOnly = false;
          }
        } while (notOnly && (count++) <= 3);// 最多只試三次

        if (!notOnly) {
          json = JSON.parseObject(myJSON.toJSONString(ran_bean));
        } else {
          throw new Exception("目前人數過多，請稍後再試");
        }
      }
    } else {
      throw new Exception("尚未註冊, carid = " + carid);
    }

    return packResponseWithJson(json);
  }

  @Override
  protected Response match() throws Exception {
    JSONObject para = (JSONObject) _para.clone();
    CleanOutOfDate();

    String phoneid = para.getString("phoneid");
    String randomid = para.getString("randomid");

    if (StringUtils.isBlank(phoneid)) {
      throw new Exception("phoneid must provided");
    }
    if (StringUtils.isBlank(randomid)) {
      throw new Exception("randomid must provided");
    }

    PhoneregService phone_service = MyApplicationContext.getInstance().getBean(PhoneregService.class);
    PhoneregCriteria phone_cri = new PhoneregCriteria();
    List<Phonereg> phone_list = Lists.newArrayList();
    Phonereg phone_bean = new Phonereg();
    int phone_count = 0;

    CprannumService ran_service = MyApplicationContext.getInstance().getBean(CprannumService.class);
    CprannumCriteria ran_cri = new CprannumCriteria();
    List<Cprannum> ran_list = Lists.newArrayList();
    Cprannum ran_bean = new Cprannum();
    int ran_count = 0;

    CprelationService rel_service = MyApplicationContext.getInstance().getBean(CprelationService.class);
    CprelationCriteria rel_cri = new CprelationCriteria();
    List<Cprelation> rel_list = Lists.newArrayList();
    Cprelation rel_bean = new Cprelation();
    int rel_count = 0;

    JSONObject json = null;
    String dateStr = CTCommon.getNowTime();

    phone_cri.clear();
    phone_cri.or().andEncodedKeyEqualTo(phoneid);
    phone_count = phone_service.countByExample(phone_cri);

    if (phone_count > 0) {
      // 該phoneID已註冊
      logger.info("該phoneid已註冊:" + phoneid + ", 使用randomid進行連結:" + randomid);
      ran_cri.clear();
      ran_cri.or().andRandomIDEqualTo(randomid).andDeadTimeGreaterThanOrEqualTo(dateStr);
      ran_count = ran_service.countByExample(ran_cri);
      if (ran_count > 0) {
        logger.info("找到配對的randomid:" + randomid);
        ran_bean = ran_service.selectByExample(ran_cri, 0, 0).get(0);
        String carid = ran_bean.getCarID();
        // 清除使用亂數配對的記錄
        ran_cri.clear();
        ran_cri.or().andEncodedKeyEqualTo(ran_bean.getEncodedKey());
        ran_service.deleteByExample(ran_cri);

        rel_cri.clear();
        rel_cri.or().andCarIDEqualTo(carid).andPhoneIDEqualTo(phoneid);
        rel_count = rel_service.countByExample(rel_cri);
        if (rel_count > 1) {
          throw new Exception("連結資料異常:carid:" + carid + ", phoneid=" + phoneid);
        }

        // 曾經配對成功過
        if (rel_count == 1) {
          // 更新記錄
          rel_bean = rel_service.selectByExample(rel_cri, 0, 0).get(0);
          rel_bean.setIsPrimary(ran_bean.getIsPrimary());
          rel_bean.setModTime(dateStr);
          rel_service.updateByExample(rel_bean, rel_cri);
        } else {
          // 未曾配對成功過
          // 新增記錄
          rel_bean = new Cprelation();
          rel_bean.setCarID(carid);
          rel_bean.setPhoneID(phoneid);
          rel_bean.setIsPrimary(ran_bean.getIsPrimary());
          rel_bean.setAddTime(dateStr);
          rel_bean.setModTime(dateStr);
          rel_service.insert(rel_bean);
        }
        json = JSON.parseObject(myJSON.toJSONString(rel_bean));
      } else {
        throw new Exception("找不到配對的randomid:" + randomid);
      }

    } else {
      throw new Exception("尚未註冊, phoneid = " + phoneid);
    }

    return packResponseWithJson(json);
  }

  private String generateRandom() {
    String randomID;
    Random rand = new Random(System.currentTimeMillis());
    Long randLong = (long) (rand.nextDouble() * 1000000);
    randomID = "" + randLong;
    randomID = StringUtils.leftPad(randomID, 6, "0");
    return randomID;
  }

  private void CleanOutOfDate() {
    // 設定5/100的機率下，會去清除過期的資料
    Random random = new Random(System.currentTimeMillis());
    if (random.nextDouble() < 0.05) {
      logger.info("試著清除過期的資料");
      String dateStr = CTCommon.getNowTime();
      CprannumService service = MyApplicationContext.getInstance().getBean(CprannumService.class);
      CprannumCriteria cri = new CprannumCriteria();
      List<Cprannum> list = Lists.newArrayList();
      Cprannum bean = new Cprannum();
      int count = 0;

      cri.clear();
      cri.or().andDeadTimeLessThanOrEqualTo(dateStr);
      if (count > 0) {
        try {
          service.deleteByExample(cri);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
