package vn.myclass.core.test;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;
import vn.myclass.core.dao.RoleDao;
import vn.myclass.core.daoimpl.RoleDaoImpl;
import vn.myclass.core.persistence.entity.RoleEntity;

import java.util.ArrayList;
import java.util.List;

public class RoleTest {
    @Test
    public void checkFindAll(){
        //fix loi No appenders could be found for logger(log4j)?
        BasicConfigurator.configure();
        RoleDao roleDao = new RoleDaoImpl();
        List<RoleEntity> list = roleDao.findAll();
        System.out.println(list);
    }

    @Test
    public void checkUpdateRole(){
        RoleDao roleDao = new RoleDaoImpl();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleId(2l);
        roleEntity.setName("Hien");
        roleDao.update(roleEntity);
    }

    @Test
    public void addRole(){
        //để thực hiện truy vấn
        RoleDao roleDao = new RoleDaoImpl();
        RoleEntity roleEntityAdd = new RoleEntity();
        roleEntityAdd.setRoleId(1l);
        roleEntityAdd.setName("ADMIN");

        RoleEntity roleEntityAddTwo = new RoleEntity();
        roleEntityAddTwo.setRoleId(2l);
        roleEntityAddTwo.setName("USER");

        roleDao.save(roleEntityAdd);
        roleDao.save(roleEntityAddTwo);
    }

    @Test
    public void findById(){
        //để thực hiện truy vấn
        RoleDao roleDao = new RoleDaoImpl();
        roleDao.findById(1l);
    }

    @Test
    public void testFindByProperty(){
        RoleDao roleDao = new RoleDaoImpl();
        String property =  null;
        Object objectValue = null;
        String sortString = null;
        String sortDirection = null;
        Object[] objects = roleDao.findByProperty(property, objectValue, sortString, sortDirection);
    }

    @Test
    public void testDelet(){
        List<Long> list = new ArrayList<Long>();
        list.add(1l);
        list.add(2l);
        RoleDao roleDao = new RoleDaoImpl();
        Long countDelete = roleDao.delete(list);
    }
}
