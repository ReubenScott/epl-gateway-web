package com.soak.attendance.service;import java.util.List;import com.soak.attendance.model.ScheduleType;public interface AtndRecordAdditionService {    /**   * 第一步 导入打卡记录   *    * @param filePath   */  public void loadPunchRecord(String dirPath);  /**   * 获取所有排班类型   */  public List<ScheduleType> getAllScheduletypes();      }