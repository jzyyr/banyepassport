package cn.tedu.csmallpassport.pojo.entity;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 管理员角色关联(AdminRole)实体类
 *
 * @author makejava
 * @since 2022-10-09 16:19:43
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRole implements Serializable {
    private static final long serialVersionUID = 753476431703683474L;
    
    private Long id;
    /**
     * 管理员id
     */
    private Long adminId;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 数据创建时间
     */
    private Date gmtCreate;
    /**
     * 数据最后修改时间
     */
    private Date gmtModified;
}
