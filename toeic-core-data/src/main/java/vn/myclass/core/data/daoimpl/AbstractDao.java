package vn.myclass.core.data.daoimpl;

import org.hibernate.*;
import vn.myclass.core.common.extend.CoreConstant;
import vn.myclass.core.common.utils.HibernateUtil;
import vn.myclass.core.data.dao.GenericDAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class AbstractDao<ID extends Serializable, T> implements GenericDAO<ID, T> {

    private Class<T> persistenceClass;

    public AbstractDao(){
        //trả về getGenericSuperclass
        //trả về class User
        //get Actual Type Arguments : get thực tế ở vị trí 1
//        ID vị trí thứ 0
        this.persistenceClass = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

//    protected Session getSession(){
//        return HibernateUtil.getSessionFactory().openSession();
//    }
    public String getPersistenceClassName(){
        return persistenceClass.getSimpleName();
    }

    public List<T> findAll(){

        List<T> list = new ArrayList<T>();
        //        Mỗi lần thực thi hàm thì phải tạo một session mới
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;   //thực hiện các phương thức trong session như là thêm sửa xóa
        transaction = session.beginTransaction();
        try {
            //hql
            StringBuilder sql = new StringBuilder(" from ");
            sql.append(persistenceClass.getSimpleName()); //ten class
            Query query = session.createQuery(sql.toString());
            list = query.list();
            //Sql nactive
//            StringBuilder stringBuilder = new StringBuilder("select totalRecord ");
//            stringBuilder.append(",T1.TYPE type ");
//            stringBuilder.append(",T1.IS_SYNONIM isSynonim ");
//            stringBuilder.append(",T1.DESCRIPTION description ");
//            stringBuilder.append(",T1.CR_NUMBER crNumber ");
//            stringBuilder.append(",T1.LATITUDE latitude ");
//            stringBuilder.append(",T1.LONGITUDE longitude ");
//            stringBuilder.append(",T1.CAT_STATION_ID catStationId ");
//            stringBuilder.append(",nvl(T1.NAME,'') name ");
//            stringBuilder.append(",T1.CODE code ");
//            stringBuilder.append(",T1.ADDRESS address ");
//            stringBuilder.append(",T1.STATUS status ");
//            stringBuilder.append(",T1.START_POINT_ID startPointId ");
//            stringBuilder.append(",T1.END_POINT_ID endPointId ");
//            stringBuilder.append(",T1.LINE_TYPE_ID lineTypeId ");
//            stringBuilder.append(",T1.LINE_LENGTH lineLength ");
//            stringBuilder.append(",T1.EMISSION_DATE emissionDate ");
//            stringBuilder.append(",T1.SCOPE scope ");
//            stringBuilder.append(",T1.SCOPE_NAME scopeName ");
//            stringBuilder.append(",T1.START_POINT_TYPE startPointType ");
//            stringBuilder.append(",T1.END_POINT_TYPE endPointType ");
//            stringBuilder.append(",T1.PARENT_ID parentId ");
//            stringBuilder.append(",T1.DISTANCE_ODD distanceOdd ");
//            stringBuilder.append(",T1.AREA_LOCATION areaLocation ");
//            stringBuilder.append(",T1.CREATED_DATE createdDate ");
//            stringBuilder.append(",T1.UPDATED_DATE updatedDate ");
//            stringBuilder.append(",T1.CREATED_USER createdUser ");
//            stringBuilder.append(",T1.UPDATED_USER updatedUser ");
//            stringBuilder.append(",T1.CAT_STATION_TYPE_ID catStationTypeId ");
//            stringBuilder.append(",T1.CAT_PROVINCE_ID catProvinceId ");
//            stringBuilder.append(",T1.CAT_STATION_HOUSE_ID catStationHouseId ");
//            SQLQuery query = getSession().createSQLQuery(stringBuilder.toString());
//            SQLQuery queryCount = getSession().createSQLQuery(sqlCount.toString());
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            throw  e;
        }finally {
            session.close();
        }
        return list;
    }

    public T update(T entity) {
        T result = null;
//        Mỗi lần thực thi hàm thì phải mở một session mới
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            //merge: dùng để Update
            Object object = session.merge(entity);
            result = (T) object;
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
        return result;
    }

    public void save(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(entity);
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    public T findById(ID id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        T entity = null;
        try {
            entity = (T) session.get(persistenceClass, id);
            if(entity == null){
                throw new ObjectNotFoundException(" NOT FOUND : " + id, null);
            }
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
        return entity;
    }

    public Object[] findByProperty(String property, Object value, String sortExpression, String sortDirection) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<T> list = null;
        Object totalItem = null;
        try {
            StringBuilder sql = new StringBuilder(" from ");
            sql.append(getPersistenceClassName());
            sql.append(" where 1 = 1 ");
            if(property != null && value != null){
                sql.append(" AND ");
                sql.append(property);
                sql.append("= :value");
            }
            if(sortExpression != null && sortDirection != null){
                sql.append(" order by ");
                sql.append(sortExpression).append(" ");
                sql.append(sortDirection.equalsIgnoreCase(CoreConstant.SORT_SQL_ASC) ? " ASC": " DESC");
            }
            Query query = session.createQuery(sql.toString());
            list = query.list();
            StringBuilder sqlCount = new StringBuilder("select count(*) from ");
            sqlCount.append(getPersistenceClassName());
            sqlCount.append(" where 1 = 1 ");
            if(property != null && value != null){
                sqlCount.append(" AND ");
                sqlCount.append(property);
                sqlCount.append("= :value");
            }
            Query queryCount = session.createQuery(sqlCount.toString());
            if(value != null){
                query.setParameter("value", value);
                queryCount.setParameter("value", value);
            }
            totalItem = queryCount.list().get(0);
            transaction.commit();
        }catch (HibernateException e){
            //quay lai trang thai trc khi co su thay doi
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
        return new Object[]{totalItem, list};
    }

    public Long delete(List<ID> listId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Long countDelete = 0l;
        try{
            for(ID item: listId){
                T entity = (T) session.get(persistenceClass, item);
                session.delete(entity);
                countDelete++;
            }
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
        return countDelete;
    }


}
