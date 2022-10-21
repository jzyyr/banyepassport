package cn.tedu.csmallpassport.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色(Role)VO类
 *
 * @author makejava
 * @since 2022-10-10 14:21:33
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleListItemVO implements Serializable {
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

}
