package cn.tedu.csmallpassport.controller;

import cn.tedu.csmallpassport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmallpassport.pojo.dto.AdminLoginInDTO;
import cn.tedu.csmallpassport.security.LoginPrincipal;
import cn.tedu.csmallpassport.service.impl.IAdminServiceImpl;
import cn.tedu.csmallpassport.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * (ams_admin)表控制层
 *
 * @author xxxxx
 */
@Api(tags = "01 管理员管理模块")
@RestController
@RequestMapping("/admins")
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);
    /**
     * 服务对象
     */
    @Resource
    private IAdminServiceImpl adminServiceImpl;

    @ApiOperationSupport(order = 100)
    @ApiOperation(value = "添加管理员")
    @PostMapping("/add-new")
    @PreAuthorize("hasAuthority('/ams/admin/read')")
    public JsonResult addNew(AdminAddNewDTO adminAddNewDTO) {
        logger.info("添加成功");
        adminServiceImpl.adminAddNewDTO(adminAddNewDTO);
        return JsonResult.ok();
    }


    @ApiOperationSupport(order = 200)
    @ApiOperation(value = "根据id删除管理员")
    @ApiImplicitParam(name = "id", value = "管理员id", required = true, dataType = "long")
    @PostMapping("/delete/{id:[0-9]+}")
    public JsonResult delete(@PathVariable Long id,
                       @ApiIgnore @AuthenticationPrincipal LoginPrincipal loginPrincipal) {
        logger.info("当前登录的当事人:{}",loginPrincipal);
        adminServiceImpl.deleteById(id);
        return JsonResult.ok();
    }


    @ApiOperationSupport(order = 300)
    @ApiOperation(value = "设置是否启用管理员")
    @PostMapping("/setEnable/{id:[0-9]+}")
    public JsonResult updateEnable(@PathVariable Long id,
                           @ApiIgnore @AuthenticationPrincipal LoginPrincipal loginPrincipal) {
        logger.info("当前登录的当事人:{}",loginPrincipal);
        adminServiceImpl.updateEnable(id);
        return JsonResult.ok();
    }



    @ApiOperationSupport(order = 400)
    @ApiOperation(value = "管理员列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('/ams/admin/read')")
    public JsonResult list() {
        logger.info("开始处理管理员列表请求");
        return JsonResult.ok(adminServiceImpl.adminList());
    }


    @ApiOperationSupport(order = 410)
    @ApiOperation(value = "根据用户名获取账号信息")
    @GetMapping("/getLoginInfoByUsername")
    public JsonResult getLoginInfoByUsername(String username) {
        return JsonResult.ok(adminServiceImpl.getLoginInfoByUsername(username));
    }


    @ApiOperationSupport(order = 420)
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public JsonResult login(AdminLoginInDTO adminLoginInDTO) {
        logger.info("登录的用户为{}",adminLoginInDTO);
        String jwt=adminServiceImpl.login(adminLoginInDTO);
        return JsonResult.ok(jwt);
//        return JsonResult.ok(adminServiceImpl.getLoginInfoByUsername(username));
    }


}
