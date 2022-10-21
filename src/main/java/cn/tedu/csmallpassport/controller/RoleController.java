package cn.tedu.csmallpassport.controller;

import cn.tedu.csmallpassport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmallpassport.service.impl.IAdminServiceImpl;
import cn.tedu.csmallpassport.service.impl.IRoleServiceImpl;
import cn.tedu.csmallpassport.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ams_admin)表控制层
 *
 * @author xxxxx
 */
@Api(tags = "01 角色管理模块")
@RestController
@RequestMapping("roles")
public class RoleController {
    Logger logger = LoggerFactory.getLogger(RoleController.class);
    /**
     * 服务对象
     */
    @Resource
    private IRoleServiceImpl service;

//    @ApiOperationSupport(order = 100)
//    @ApiOperation(value = "添加管理员")
//    @PostMapping("/add-new")
//    public JsonResult addNew(AdminAddNewDTO adminAddNewDTO) {
//        logger.info("添加成功");
//        service.adminAddNewDTO(adminAddNewDTO);
//        return JsonResult.ok();
//    }

    @ApiOperationSupport(order = 400)
    @ApiOperation(value = "角色列表")
    @GetMapping("/list")
    public JsonResult list() {
        return JsonResult.ok(service.list());
    }

//    @ApiOperationSupport(order = 200)
//    @ApiOperation(value = "根据id删除管理员")
//    @PostMapping("/delete/{id:[0-9]+}")
//    public JsonResult delete(@PathVariable Long id) {
//        adminServiceImpl.deleteById(id);
//        return JsonResult.ok();
//    }

}
