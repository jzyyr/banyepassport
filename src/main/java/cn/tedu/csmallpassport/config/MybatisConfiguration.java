package cn.tedu.csmallpassport.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.tedu.csmallpassport.mapper")
public class MybatisConfiguration {

}
