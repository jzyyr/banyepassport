package cn.tedu.csmallpassport.service;


import cn.tedu.csmallpassport.ex.ServiceException;
import cn.tedu.csmallpassport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmallpassport.service.IAdminService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class AdminServiceTests {

    @Autowired
    IAdminService service;

    @Test
    void testAddNew() {
        AdminAddNewDTO adminAddNewDTO = new AdminAddNewDTO();
        adminAddNewDTO.setUsername("wangkejing6");
        adminAddNewDTO.setPassword("123456");
        adminAddNewDTO.setPhone("13800138006");
        adminAddNewDTO.setEmail("wangkejing6@baidu.com");
        adminAddNewDTO.setRoleIds(new Long[]{3L, 4L, 5L});

        try {
            service.adminAddNewDTO(adminAddNewDTO);
            log.info("添加管理员成功！");
        } catch (ServiceException e) {
            log.info("{}", e.getMessage());
        }
    }

    @Test
    void testDelete() {
        Long id = 9L;

        try {
            service.deleteById(id);
            System.out.println("删除成功！");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testList() {
        List<?> list = service.adminList();
        System.out.println("查询列表完成，列表中的数据的数量=" + list.size());
        for (Object item : list) {
            System.out.println(item);
        }
    }

    @Test
    void testSetEnable(){
        service.updateEnable(88l);
    }


    @Test
    void testGetLoginInfoByUsername() {
        String username = "fanchuanqi";
        Object result = service.getLoginInfoByUsername(username);
        System.out.println("根据username=" + username + "查询登录信息完成，结果=" + result);
    }



}
