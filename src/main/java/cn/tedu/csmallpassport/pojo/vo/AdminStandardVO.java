package cn.tedu.csmallpassport.pojo.vo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理员
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminStandardVO implements Serializable {
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
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否启用，1=启用，0=未启用
     */
    private Integer enable;

    /**
     * 最后登录IP地址（冗余）
     */
    private String lastLoginIp;

    /**
     * 累计登录次数（冗余）
     */
    private Integer loginCount;

    /**
     * 最后登录时间（冗余）
     */
    private Date gmtLastLogin;

    private static final long serialVersionUID = 1L;
}