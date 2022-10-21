package cn.tedu.csmallpassport.service;

import cn.tedu.csmallpassport.ex.ServiceException;
import cn.tedu.csmallpassport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmallpassport.pojo.entity.Admin;
import cn.tedu.csmallpassport.service.IAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminTests {
    @Autowired
    IAdminService service;
    @Test
    void testCountByUsername(){
        int count = service.countByUsername("root");
        System.out.println(count);
    }
    @Test
    void testCountByPhone(){
        int count = service.countByPhone("13900139001");
        System.out.println(count);
    }
    @Test
    void testCountByEmail(){
        int count = service.countByEmail("root@baidu.com");
        System.out.println(count);
    }

    @Test
    void testAdminAddNew(){
        try {
            AdminAddNewDTO adminAddNewDTO =new AdminAddNewDTO();
            adminAddNewDTO.setUsername("154787546");
            adminAddNewDTO.setPhone("137843");
            adminAddNewDTO.setEmail("4685574");
            service.adminAddNewDTO(adminAddNewDTO);
        }catch (ServiceException e){

        }

    }


}
