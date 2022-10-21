package cn.tedu.csmallpassport.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;

/**
 * Spring Validation的配置类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Configuration
public class ValidationConfiguration {

    public ValidationConfiguration() {
        log.debug("创建配置类对象：ValidationConfiguration");
    }

    @Bean
    public javax.validation.Validator validator() {
        return Validation.byProvider(HibernateValidator.class)
                .configure() // 开始配置Validator
                .failFast(true) // 快速失败，即检查请求参数发现错误时直接视为失败，并不向后继续检查
                .buildValidatorFactory()
                .getValidator();
    }

}
