package cn.tedu.csmallpassport.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 管理员
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLogInVO implements Serializable {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（密文）
     */
    private String password;


    /**
     * 是否启用，1=启用，0=未启用
     */
    private Integer enable;

    /**
     * 管理员权限列表
     */
    private List<String> permissions;

    private static final long serialVersionUID = 1L;
}