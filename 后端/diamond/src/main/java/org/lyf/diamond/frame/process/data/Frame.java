package org.lyf.diamond.frame.process.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @program: some_middle
 * @description:
 * @author: GG-lyf
 * @create: 2022-04-04 13:33:44
 */
@SuppressWarnings("all")
public interface Frame<T> {
  int insert();

  int deleteById();

  int deleteByMap();

  int delete();

  int deleteBatchIds();

  int updateById();

  int update();

  T selectById();

  List<T> selectBatchIds();

  List<T> selectByMap();

  T selectOne();

  boolean exists();

  Long selectCount();

  List<T> selectList();

  List<Map<String, Object>> selectMaps();

  List<Object> selectObjs();

  T selectPage();

  T selectMapsPage();




  boolean save();

  boolean saveBatch();

  boolean saveOrUpdateBatch();

  boolean removeById(Serializable id);

  boolean removeByMap(Map<String, Object> columnMap);

  boolean remove();

  boolean removeByIds();

  boolean removeBatchByIds(Collection<?> list);

  boolean removeBatchByIds(Collection<?> list, boolean useFill);

  boolean removeBatchByIds(Collection<?> list, int batchSize);

  boolean removeBatchByIds(Collection<?> list, int batchSize, boolean useFill);

  boolean updateById(T entity);

  boolean updateBatchById(Collection<T> entityList);

  boolean updateBatchById(Collection<T> entityList, int batchSize);

  boolean saveOrUpdate(T entity);

  T getById(Serializable id);

  List<T> listByIds(Collection<? extends Serializable> idList);

  List<T> listByMap(Map<String, Object> columnMap);

  T getOne();

  Map<String, Object> getMap();

  long count();

  List<T> list();

  List<Map<String, Object>> listMaps();

  <V> List<V> listObjs();

  T pageMaps();

  T getBaseMapper();

  Class<T> getEntityClass();

  T query();

  T ktQuery();

  T ktUpdate();

  boolean saveOrUpdate();


}
