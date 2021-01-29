package com.kindustry.orm.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kindustry.orm.entity.BaseEntity;
import com.kindustry.structure.model.ReflectHelper;

@Repository("baseDao")
public class BaseDaoImpl<T extends BaseEntity> implements IBaseDao {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * 保存
   */
  final String SQL_INSERT = ".insert";

  /**
   * 删除
   */
  final String SQL_DELETE = ".delete";

  /**
   * 更新
   */
  final String SQL_UPDATE = ".update";

  /**
   * 更新
   */
  final String SQL_SELECT_SINGLE = ".select";

  @Resource
  protected SqlSessionTemplate session;

  protected Class<T> entityClass;

  @SuppressWarnings("unchecked")
  public BaseDaoImpl() {
    this.entityClass = ReflectHelper.getSuperClassGenericType(getClass(), 0);
  }

  public SqlSession getSqlSession() {
    return session;
  }

  public void setSqlSession(SqlSessionTemplate sqlSession) {
    this.session = sqlSession;
  }

  // @Override
  public T selectSingle(Object param) {
    if (null == param) {
      logger.error("非法参数：param为空！");
      throw new IllegalArgumentException("非法参数：param为空！");
    }
    T result = null;
    try {
      result = getSqlSession().selectOne(entityClass.getName() + SQL_SELECT_SINGLE, param);
    } catch (Throwable e) {
      logger.error("{}", e);
    }
    return result;
  }

  // @Override
  public T insert(T entity) {
    if (null == entity) {
      logger.error("参数对象为null!");
      throw new IllegalArgumentException("参数不可为null！");
    }
    try {
      getSqlSession().insert(entityClass.getName() + SQL_INSERT, entity);
    } catch (Throwable e) {
      logger.error("{}", e);
      return null;
    }

    return entity;
  }

  // @Override
  public boolean update(T entity) {
    if (null == entity) {
      throw new IllegalArgumentException("参数不可为null！");
    }
    try {
      getSqlSession().update(entityClass.getName() + SQL_UPDATE, entity);
    } catch (Exception e) {
      logger.error("更新数据异常：", e);
      return false;
    }
    return true;
  }

  // @Override
  // public boolean delete(Object param) {
  // try {
  // getSqlSession().delete(entityClass.getName() + SQL_DELETE, param);
  // } catch (Exception e) {
  // logger.error("删除数据异常：", e);
  // return false;
  // }
  // return true;
  // }

  @Override
  public <T> int insert(String _mybitsId, T obj) {
    return session.insert(_mybitsId, obj);
  }

  public void add(Object entity) {
    try {
      session.insert(entity.getClass().getName() + ".add", entity);

      session.commit();
    } catch (Exception e) {
      e.printStackTrace();
      session.rollback();
    } finally {
      session.close();
    }
  }

  // public boolean addBatch(List<Object> entities) throws Exception {
  // // TODO Auto-generated method stub
  // int result = 1;
  // SqlSession batchSqlSession = null;
  // try {
  // // 获取批量方式的sqlsession
  // batchSqlSession =
  // this.getSqlSessionTemplate().getSqlSessionFactory().openSession(ExecutorType.BATCH,
  // false);
  // int batchCount = 1000; // 每批commit的个数
  // int batchLastIndex = batchCount; // 每批最后一个的下标
  // for (int index = 0; index < members.size();) {
  // if (batchLastIndex >= members.size()) {
  // batchLastIndex = members.size();
  // result = result *
  // batchSqlSession.insert("MutualEvaluationMapper.insertCrossEvaluation",
  // members.subList(index, batchLastIndex));
  // batchSqlSession.commit();
  // System.out.println("index:" + index + " batchLastIndex:" + batchLastIndex);
  // break;// 数据插入完毕，退出循环
  // } else {
  // result = result *
  // batchSqlSession.insert("MutualEvaluationMapper.insertCrossEvaluation",
  // members.subList(index, batchLastIndex));
  // batchSqlSession.commit();
  // System.out.println("index:" + index + " batchLastIndex:" + batchLastIndex);
  // index = batchLastIndex; // 设置下一批下标
  // batchLastIndex = index + (batchCount - 1);
  // }
  // }
  // batchSqlSession.commit();
  // } finally {
  // batchSqlSession.close();
  // }
  // return Tools.getBoolean(result);
  // }

  public void update(Object entity) {
    try {
      session.update(entity.getClass().getName() + ".update", entity);
      session.commit();
    } catch (Exception e) {
      e.printStackTrace();
      session.rollback();
    } finally {
      session.close();
    }
  }

  public void del(Class entityClass, int id) {
    try {
      session.delete(entityClass.getName() + ".del", id);
      session.commit();
    } catch (Exception e) {
      e.printStackTrace();
      session.rollback();
    } finally {
      session.close();
    }
  }

  public void del(Class entityClass, int[] ids) {
    try {
      for (int id : ids) {
        session.delete(entityClass.getName() + ".del", id);
      }
      session.commit();
    } catch (Exception e) {
      e.printStackTrace();
      session.rollback();
    } finally {
      session.close();
    }
  }

  public void del(Class entityClass, String[] ids) {
    try {
      for (String id : ids) {
        session.delete(entityClass.getName() + ".del", Integer.parseInt(id));
      }
      session.commit();
    } catch (Exception e) {
      e.printStackTrace();
      session.rollback();
    } finally {
      session.close();
    }
  }

  public void wipe(Class entityClass) {
    try {
      session.delete(entityClass.getName() + ".truncate");
      session.commit();
    } catch (Exception e) {
      e.printStackTrace();
      session.rollback();
    } finally {
      session.close();
    }
  }

  public Object findById(Class entityClass, int id) {
    try {
      return session.selectOne(entityClass.getName() + ".findById", id);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
    }
    return null;
  }

  public List findAll(Class entityClass) {
    try {
      return session.selectList(entityClass.getName() + ".findAll");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
    }
    return null;
  }

  /*
   * public PagerVO findPaginated(String sqlId, Map params) { SqlSession session
   * = MyBatisUtil.getSession(); List datas = null; int total = 0; try {
   * // 取出分页参数，设置到params中 // params.put("offset", SystemContext.getOffset()); //
   * params.put("pagesize", SystemContext.getPagesize());
   * datas = session.selectList(sqlId, params); total = (Integer)
   * session.selectOne(sqlId + "-count", params); } catch (Exception e) {
   * e.printStackTrace(); } finally { session.close(); } PagerVO pv = new
   * PagerVO(); pv.setDatas(datas); pv.setTotal(total); return pv; }
   */

  @Override
  public <T> int delete(String _mybitsId, T obj) {
    return session.delete(_mybitsId, obj);
  }

  @Override
  public <T> int update(String _mybitsId, T obj) {
    return session.update(_mybitsId, obj);
  }

  @Override
  public <T> List<T> query(String _mybitsId, Map<String, Object> _params) {
    return session.selectList(_mybitsId, _params);
  }

  @Override
  public <T> List<T> query(String _mybitsId, Object _params) {
    return session.selectList(_mybitsId, _params);
  }

  @Override
  public Object queryOne(String _mybitsId, Object object) {
    return session.selectOne(_mybitsId, object);
  }

}