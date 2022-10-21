package cn.tedu.csmallpassport.pojo.entity;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 角色(Role)实体类
 *
 * @author makejava
 * @since 2022-10-10 14:21:33
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    private static final long serialVersionUID = 197265946508520149L;
    
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 自定义排序序号
     */
    private Integer sort;
    /**
     * 数据创建时间
     */
    private Date gmtCreate;
    /**
     * 数据最后修改时间
     */
    private Date gmtModified;
}
