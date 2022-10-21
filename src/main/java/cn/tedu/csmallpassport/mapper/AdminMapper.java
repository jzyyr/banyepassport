package cn.tedu.csmallpassport.mapper;

import cn.tedu.csmallpassport.pojo.entity.Admin;
import java.util.List;

import cn.tedu.csmallpassport.pojo.vo.AdminListVO;
import cn.tedu.csmallpassport.pojo.vo.AdminLogInVO;
import cn.tedu.csmallpassport.pojo.vo.AdminStandardVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(Admin record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(Admin record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    Admin selectByPrimaryKey(Long id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Admin record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Admin record);

    int updateBatch(List<Admin> list);

    int batchInsert(@Param("list") List<Admin> list);

    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteById(Long id);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    AdminStandardVO selectById(Long id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByIdSelective(Admin record);

    /**
     * update record
     *
     * @param admin
     * @return update count
     */
    int updateById(Admin admin);

    int countByUsername(String username);

    int countByPhone(String phone);

    int countByEmail(String email);

    List<AdminListVO> list();

    int setEnable(Long id);

    int setDisable(Long id);

    AdminLogInVO getLoginInfoByUsername(String username);
}