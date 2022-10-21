package cn.tedu.csmallpassport.mapper;

import cn.tedu.csmallpassport.pojo.entity.AdminRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRoleMapper {

    int insertBatch(List<AdminRole> list);

    int deleteByAdminId(long id);

}

