package cn.tedu.csmallpassport.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
    * 添加管理员的DTO类
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminAddNewDTO implements Serializable {

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
    private Byte enable;


    private Long[] roleIds;

    private static final long serialVersionUID = 1L;
}