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
        logger.info("?????????????????????????????????,{}", adminLoginInDTO);
        // ??????AuthenticationManager?????????authenticate()??????????????????
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                adminLoginInDTO.getUsername(), adminLoginInDTO.getPassword());
        Authentication  authentication1=  authenticationManager.authenticate(authentication);


        logger.debug("??????principal:{}",authentication1.getPrincipal());
        logger.debug("??????????????????");

        AdminDetails user= (AdminDetails) authentication1.getPrincipal();

        logger.debug("????????????JWT??????");
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");

        Map<String, Object> claims = new HashMap<>();


        claims.put("username", user.getUsername());// ???JWT?????????id
        claims.put("id", user.getId());// ???JWT?????????username
        claims.put("authorities", JSON.toJSONString(user.getAuthorities())); // ???JWT???????????????,??????????????????json?????????,?????????????????????(?????????????????????)


        Date expirationDate = new Date(System.currentTimeMillis() + duration*60*1000);

        System.out.println("???????????????" + expirationDate);



        String jwt = Jwts.builder()
                //Header
                .setHeaderParams(headers)
                // Payload ??????(??????)
                .setClaims(claims)
                //????????????
                .setExpiration(expirationDate)
                // Signature ??????(??????)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                //??????
                .compact();
        logger.info("jwt???:{}",jwt);

        return jwt;
    }

    @Override
    public int deleteById(Long id) {
        AdminStandardVO admin = adminMapper.selectById(id);
        if (admin == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "??????????????????");
        }
        int rows = adminMapper.deleteById(id);
        if (rows != 1) {
            String message = "?????????????????????,???????????????";
            logger.warn(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
        rows = adminRoleMapper.deleteByAdminId(id);
        if (rows < 1) {
            String message = "???????????????????????????,???????????????";
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
            throw new ServiceException(ServiceCode.ERR_INSERT, "????????????,???!");
        }
        for (int i = 0; i < roleIds.length; i++) {
            if (roleIds[i] == 1) {
                throw new ServiceException(ServiceCode.ERR_INSERT, "????????????,???!");
            }
        }


        if (adminMapper.countByUsername(adminAddNewDTO.getUsername()) != 0) {
            throw new ServiceException(ServiceCode.ERR_CONFLICT, "????????????,?????????????????????");
        }
        if (adminMapper.countByPhone(adminAddNewDTO.getPhone()) != 0) {
            throw new ServiceException(ServiceCode.ERR_CONFLICT, "????????????,?????????????????????");

        }
        if (adminMapper.countByEmail(adminAddNewDTO.getEmail()) != 0) {
            throw new ServiceException(ServiceCode.ERR_CONFLICT, "????????????,??????????????????");

        }

        logger.info("????????????");
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminAddNewDTO, admin);
        String rawPassword = admin.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        admin.setPassword(encodedPassword);
        int rows = adminMapper.insert(admin);
        if (rows != 1) {
            String message = "?????????????????????,???????????????";
            logger.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }


        // ??????adminRoleMapper???insertBatch()????????????????????????

        List<AdminRole> adminRoleList = new ArrayList<>();
        for (int i = 0; i < roleIds.length; i++) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(admin.getId());
            adminRole.setRoleId(roleIds[i]);
            adminRoleList.add(adminRole);
        }
        rows = adminRoleMapper.insertBatch(adminRoleList);
        if (rows != roleIds.length) {
            String message = "?????????????????????,???????????????";
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
        //??????????????????,?????????
        if (adminStandardVO == null) {
            throw new ServiceException(ServiceCode.ERR_SELECT, "?????????????????????");
        }
        //?????????????????????,???enable??????,????????????????????????,?????????????????????,???????????????
        if (adminStandardVO.getEnable() == 0) {
            int rows = adminMapper.setEnable(id);
            //?????????????????????????????????,?????????
            if (rows != 1) {
                throw new ServiceException(ServiceCode.ERR_UPDATE, "??????????????????,???????????????");
            }
        } else {
            int rows = adminMapper.setDisable(id);
            //?????????????????????????????????,?????????
            if (rows != 1) {
                throw new ServiceException(ServiceCode.ERR_UPDATE, "??????????????????,???????????????");
            }
        }
    }

    @Override
    public AdminLogInVO getLoginInfoByUsername(String username) {
        return adminMapper.getLoginInfoByUsername(username);
    }


}
