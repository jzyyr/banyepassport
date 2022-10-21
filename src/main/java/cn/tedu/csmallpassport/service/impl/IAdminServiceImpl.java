package cn.tedu.csmallpassport.service.impl;

import cn.tedu.csmallpassport.ex.ServiceException;
import cn.tedu.csmallpassport.mapper.AdminMapper;
import cn.tedu.csmallpassport.mapper.AdminRoleMapper;
import cn.tedu.csmallpassport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmallpassport.pojo.dto.AdminLoginInDTO;
import cn.tedu.csmallpassport.pojo.entity.Admin;
import cn.tedu.csmallpassport.pojo.entity.AdminRole;
import cn.tedu.csmallpassport.pojo.vo.AdminListVO;
import cn.tedu.csmallpassport.pojo.vo.AdminLogInVO;
import cn.tedu.csmallpassport.pojo.vo.AdminStandardVO;
import cn.tedu.csmallpassport.security.AdminDetails;
import cn.tedu.csmallpassport.service.IAdminService;
import cn.tedu.csmallpassport.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class IAdminServiceImpl implements IAdminService {
    Logger logger = LoggerFactory.getLogger(IAdminServiceImpl.class);
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;


    @Value("${csmall.jwt.secret-Key}")
    String secretKey;

    @Value("${csmall.jwt.duration-in-minute}")
    long duration;

    @Override
    public String login(AdminLoginInDTO adminLoginInDTO) {
        logger.info("开始处理管理员登录业务,{}", adminLoginInDTO);
        // 调用AuthenticationManager对象的authenticate()方法处理认证
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                adminLoginInDTO.getUsername(), adminLoginInDTO.getPassword());
        Authentication  authentication1=  authenticationManager.authenticate(authentication);


        logger.debug("返回principal:{}",authentication1.getPrincipal());
        logger.debug("执行认证成功");

        AdminDetails user= (AdminDetails) authentication1.getPrincipal();

        logger.debug("准备生成JWT数据");
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");

        Map<String, Object> claims = new HashMap<>();


        claims.put("username", user.getUsername());// 向JWT中封装id
        claims.put("id", user.getId());// 向JWT中封装username
        claims.put("authorities", JSON.toJSONString(user.getAuthorities())); // 向JWT中封装权限,封装前先转为json字符串,不然后面拿不到(因为类型不一样)


        Date expirationDate = new Date(System.currentTimeMillis() + duration*60*1000);

        System.out.println("过期时间：" + expirationDate);



        String jwt = Jwts.builder()
                //Header
                .setHeaderParams(headers)
                // Payload 载荷(内容)
                .setClaims(claims)
                //过期时间
                .setExpiration(expirationDate)
                // Signature 签名(撒盐)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                //整合
                .compact();
        logger.info("jwt为:{}",jwt);

        return jwt;
    }

    @Override
    public int deleteById(Long id) {
        AdminStandardVO admin = adminMapper.selectById(id);
        if (admin == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该用户不存在");
        }
        int rows = adminMapper.deleteById(id);
        if (rows != 1) {
            String message = "删除管理员失败,请稍后再试";
            logger.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
        rows = adminRoleMapper.deleteByAdminId(id);
        if (rows < 1) {
            String message = "删除管理员权限失败,请稍后再试";
            logger.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
        return rows;

    }

    @Override
    public int insert(Admin record) {
        return adminMapper.insert(record);
    }

    @Override
    public int insertSelective(Admin record) {
        return adminMapper.insertSelective(record);
    }

    @Override
    public AdminStandardVO selectById(Long id) {
        return adminMapper.selectById(id);
    }

    @Override
    public int updateByIdSelective(Admin record) {
        return adminMapper.updateByIdSelective(record);
    }

//    @Override
//    public int updateById(Admin record) {
//        return adminMapper.updateById(record);
//    }

    @Override
    public int updateBatch(List<Admin> list) {
        return adminMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<Admin> list) {
        return adminMapper.batchInsert(list);
    }

    @Override
    public int countByUsername(String username) {
        return adminMapper.countByUsername(username);
    }

    @Override
    public int countByPhone(String phone) {
        return adminMapper.countByPhone(phone);
    }

    @Override
    public int countByEmail(String email) {
        return adminMapper.countByEmail(email);
    }

    @Override
    @Transactional
    public void adminAddNewDTO(AdminAddNewDTO adminAddNewDTO) {

        Long[] roleIds = adminAddNewDTO.getRoleIds();
        List list = Arrays.asList(roleIds);
        if (list.contains(1)) {
            throw new ServiceException(ServiceCode.ERR_INSERT, "非法访问,滚!");
        }
        for (int i = 0; i < roleIds.length; i++) {
            if (roleIds[i] == 1) {
                throw new ServiceException(ServiceCode.ERR_INSERT, "非法访问,滚!");
            }
        }


        if (adminMapper.countByUsername(adminAddNewDTO.getUsername()) != 0) {
            throw new ServiceException(ServiceCode.ERR_CONFLICT, "插入失败,该用户名已存在");
        }
        if (adminMapper.countByPhone(adminAddNewDTO.getPhone()) != 0) {
            throw new ServiceException(ServiceCode.ERR_CONFLICT, "插入失败,该手机号已存在");

        }
        if (adminMapper.countByEmail(adminAddNewDTO.getEmail()) != 0) {
            throw new ServiceException(ServiceCode.ERR_CONFLICT, "插入失败,该邮箱已存在");

        }

        logger.info("添加成功");
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminAddNewDTO, admin);
        String rawPassword = admin.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        admin.setPassword(encodedPassword);
        int rows = adminMapper.insert(admin);
        if (rows != 1) {
            String message = "添加管理员失败,请稍后再试";
            logger.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }


        // 调用adminRoleMapper的insertBatch()方法插入关联数据

        List<AdminRole> adminRoleList = new ArrayList<>();
        for (int i = 0; i < roleIds.length; i++) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(admin.getId());
            adminRole.setRoleId(roleIds[i]);
            adminRoleList.add(adminRole);
        }
        rows = adminRoleMapper.insertBatch(adminRoleList);
        if (rows != roleIds.length) {
            String message = "添加管理员失败,请稍后再试";
            logger.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);

        }

    }

    @Override
    public List<AdminListVO> adminList() {
        List<AdminListVO> list = adminMapper.list();
        Iterator<AdminListVO> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == 1) {
                iterator.remove();
            }
        }
        return list;
    }


    @Override
    public void updateEnable(Long id) {
        AdminStandardVO adminStandardVO = selectById(id);
        //如果查询不到,抛异常
        if (adminStandardVO == null) {
            throw new ServiceException(ServiceCode.ERR_SELECT, "该管理员不存在");
        }
        //如果查询到结果,且enable为零,说明该管理被禁用,我们只需要启用,反之就禁用
        if (adminStandardVO.getEnable() == 0) {
            int rows = adminMapper.setEnable(id);
            //如果修改结果与预期不符,抛异常
            if (rows != 1) {
                throw new ServiceException(ServiceCode.ERR_UPDATE, "出现未知错误,请稍后再试");
            }
        } else {
            int rows = adminMapper.setDisable(id);
            //如果修改结果与预期不符,抛异常
            if (rows != 1) {
                throw new ServiceException(ServiceCode.ERR_UPDATE, "出现未知错误,请稍后再试");
            }
        }
    }

    @Override
    public AdminLogInVO getLoginInfoByUsername(String username) {
        return adminMapper.getLoginInfoByUsername(username);
    }


}
