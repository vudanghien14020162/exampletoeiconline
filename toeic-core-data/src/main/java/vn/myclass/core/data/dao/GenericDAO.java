package vn.myclass.core.data.dao;

import java.io.Serializable;
import java.util.List;

//GenericDAO Id dựa vào id của bảng và tên bảng T
public interface GenericDAO<ID extends Serializable, T>{
     List<T> findAll();
     T update(T entity);
     void save(T entity);
     T findById(ID id);
     Object[] findByProperty(String property, Object value, String sortExpression, String sortDirection);
     Long delete(List<ID> listId);

}
