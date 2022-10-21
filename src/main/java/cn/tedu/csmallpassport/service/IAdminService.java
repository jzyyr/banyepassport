package cn.tedu.csmallpassport.service;

import cn.tedu.csmallpassport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmallpassport.pojo.dto.AdminLoginInDTO;
import cn.tedu.csmallpassport.pojo.entity.Admin;
import cn.tedu.csmallpassport.pojo.vo.AdminListVO;
import cn.tedu.csmallpassport.pojo.vo.AdminLogInVO;
import cn.tedu.csmallpassport.pojo.vo.AdminStandardVO;

import java.util.List;

public interface IAdminService {

    int deleteById(Long id);

    int insert(Admin record);

    int insertSelective(Admin record);

    AdminStandardVO selectById(Long id);

    int updateByIdSelective(Admin record);

//    int updateById(Admin record);

    int updateBatch(List<Admin> list);

    int batchInsert(List<Admin> list);

    int countByUsername(String username);

    int countByPhone(String phone);

    int countByEmail(String email);

    void adminAddNewDTO(AdminAddNewDTO adminAddNewDTO);

    List<AdminListVO> adminList();

    void updateEnable(Long id);

    AdminLogInVO getLoginInfoByUsername(String username);

    String login(AdminLoginInDTO adminLoginInDTO);
}
