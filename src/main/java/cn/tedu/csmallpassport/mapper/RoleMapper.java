package cn.tedu.csmallpassport.mapper;

import cn.tedu.csmallpassport.pojo.vo.RoleListItemVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {
        List<RoleListItemVO> list();

}
