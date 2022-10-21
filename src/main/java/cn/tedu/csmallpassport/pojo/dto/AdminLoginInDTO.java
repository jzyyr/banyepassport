package cn.tedu.csmallpassport.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
    * 添加管理员的DTO类
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginInDTO implements Serializable {

    private String username;

    /**
    * 密码（密文）
    */
    private String password;

    private static final long serialVersionUID = 1L;
}