package cn.tedu.csmallpassport.service.impl;

import cn.tedu.csmallpassport.mapper.RoleMapper;
import cn.tedu.csmallpassport.pojo.vo.RoleListItemVO;
import cn.tedu.csmallpassport.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Service
public class IRoleServiceImpl implements IRoleService {
Logger logger= LoggerFactory.getLogger(IRoleServiceImpl.class);
    @Resource
    private RoleMapper mapper;


    @Override
    public List<RoleListItemVO> list() {
        List<RoleListItemVO> list=mapper.list();
        Iterator <RoleListItemVO> iterator=list.iterator();
        while (iterator.hasNext()){
            if (iterator.next().getId()==1){
                iterator.remove();
            }
        }
        return list;
    }
}
