package cn.tedu.csmallpassport.security;

import cn.tedu.csmallpassport.pojo.vo.AdminLogInVO;
import cn.tedu.csmallpassport.service.impl.IAdminServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IAdminServiceImpl service;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        AdminLogInVO adminLogInVO=service.getLoginInfoByUsername(s);
        if (adminLogInVO==null){
            log.info("此用户名【{}】不存在，即将向Spring Security返回为null的UserDetails值", s);
            throw new BadCredentialsException("登录失败");
        }
        log.debug("Spring Security调用了loadUserByUsername()方法，参数：{}", s);
        // 暂时使用模拟数据来处理登录认证，假设正确的用户名和密码分别是root和123456

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : adminLogInVO.getPermissions()) {
            GrantedAuthority authority = new SimpleGrantedAuthority(permission);
            authorities.add(authority);
        }

        AdminDetails adminDetails=new AdminDetails
                (adminLogInVO.getUsername(),
                adminLogInVO.getPassword(),
                adminLogInVO.getEnable()==1
        ,authorities);
        adminDetails.setId(adminLogInVO.getId());
        log.info("即将返回UserDetails接口对象:{}",adminDetails);
//        if (adminLogInVO.getUsername().equals(s)) {
//            UserDetails userDetails = User.builder()
//                    .username(adminLogInVO.getUsername())
//                    .password(adminLogInVO.getPassword())
//                    .accountExpired(false)
//                    .accountLocked(false)
//                    .disabled(adminLogInVO.getEnable()==0)
//                    .authorities("这是一个山寨的权限标识") // 权限，注意，此方法的参数不可以为null，在不处理权限之前，可以写一个随意的字符串值
//                    .build();
//            log.info("即将向Spring Security返回UserDetails对象：{}", userDetails);
//            return userDetails;
//
//
//        }
//        log.info("{}密码错误", s);
        return adminDetails;
    }

}
