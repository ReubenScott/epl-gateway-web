package com.kindustry.infoRecod.action;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kindustry.common.json.JsonUtil;
import com.kindustry.framework.action.BaseAction;
import com.kindustry.framework.service.IBasicService;
import com.kindustry.framework.thread.ThreadManager;
import com.kindustry.infoRecod.job.XDBLJob;
import com.kindustry.infoRecod.service.InfoRecordService;

public class XDBLAction extends BaseAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1327853763505261778L;

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  private IBasicService basicService;

  public IBasicService getBasicService() {
    return basicService;
  }

  public void setBasicService(IBasicService basicService) {
    this.basicService = basicService;
  }
  
  
  public void xdbl(){

    InfoRecordService infoService = this.getBean("infoService");
    infoService.hello();
  }

  public void xdbl2() {
    basicService.createExcelBySQL("1","SELECT COUNT(1) FROM edw.ykjd_ln_duebill ");

    

//    XDBLJob job = new XDBLJob();
    // 通过ProxyFactoryBean获取IComputer接口实现类的实例
    XDBLJob job = this.getBean("job");
    Thread t = new Thread(job);
    t.setName("XDBL");
    ThreadManager threadManager = ThreadManager.getInstance();
    threadManager.push(t);
    t.start();

    // 创建JSONObject对象
    Map json = new HashMap();
    // 向json中添加数据
    json.put("status", "start");
    // json.put("thread_id", t.getId());

    super.ajaxResponse(JsonUtil.toJson(json));
  }

  public void xdbl23() {

    Thread t = new Thread() {

      public void run() {
        while (!this.isInterrupted()) {
          System.out.println(" thread : " + Thread.currentThread().getId() + "    " + Thread.currentThread().getName() + " " + this.isInterrupted());
        }
      }
    };

    t.setName("XDBL");
    ThreadManager threadManager = ThreadManager.getInstance();
    threadManager.push(t);
    t.start();

    // 创建JSONObject对象
    Map json = new HashMap();
    // 向json中添加数据
    json.put("status", "start");
    json.put("thread_id", t.getId());

    super.ajaxResponse(JsonUtil.toJson(json));
  }

  public void xdblStop() {

    ThreadManager threadManager = ThreadManager.getInstance();
    threadManager.stop("XDBL");
    System.out.println("222222222222222222222222222222222222");

    // 创建JSONObject对象
    Map json = new HashMap();
    // 向json中添加数据
    json.put("status", "stop");

    super.ajaxResponse(JsonUtil.toJson(json));

  }

}
