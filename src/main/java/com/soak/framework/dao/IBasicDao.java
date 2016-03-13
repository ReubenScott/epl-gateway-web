package com.soak.framework.dao;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

public interface IBasicDao extends IBaseDao {

  public <T> List<T> getBean(String name);

  public List findByXmlSqlMapper(String sqlName, String value);

  public Workbook createExcelBySQl(String sql, Object... params);

}