package cn.tedu.csmallpassport.mapper;


import cn.tedu.csmallpassport.mapper.AdminRoleMapper;
import cn.tedu.csmallpassport.pojo.entity.AdminRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class AdminRoleMapperTests {

    @Autowired
    AdminRoleMapper mapper;

    @Test
    void testInsertBatch() {
        List<AdminRole> adminRoleList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(100L);
            adminRole.setRoleId(i + 0L);
            adminRoleList.add(adminRole);
        }

        int rows = mapper.insertBatch(adminRoleList);
        log.debug("批量插入数据完成！受影响的行数={}", rows);
    }
    
}
