package cn.tedu.csmallpassport.filter;

import cn.tedu.csmallpassport.security.LoginPrincipal;
import cn.tedu.csmallpassport.web.JsonResult;
import cn.tedu.csmallpassport.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * JWT认证过滤器
 *
 * <p>Spring Security框架会自动从SecurityContext读取认证信息，如果存在有效信息，则视为已登录，否则，视为未登录</p>
 * <p>当前过滤器应该尝试解析客户端可能携带的JWT，如果解析成功，则创建对应的认证信息，并存储到SecurityContext中</p>
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    public static final int JWT_MIN_LENGTH = 100;

    @Value("${csmall.jwt.secret-Key}")
    String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //每次发请求时都清空原有数据,防止之前登录的缓存
        SecurityContextHolder.clearContext();

        String jwt = request.getHeader("Authorization");
        log.debug("接收到JWT数据：{}", jwt);
        if (!StringUtils.hasText(jwt) || jwt.length() < JWT_MIN_LENGTH) {
            log.debug("未获取到有效数据,将直接放行");
            // 放行
            filterChain.doFilter(request, response);
            return;
        }
        //设置响应的文档类型
        response.setContentType("application/json;charset=utf-8");

        log.debug("将尝试解析jwt");
        Claims claims = null;
        try {
            claims=Jwts.parser().setSigningKey(secretKey).
                    parseClaimsJws(jwt).getBody();
        } catch (SignatureException e) {
            String message = "非法访问,滚!";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_SIGNATURE, message);
            String jsonString = JSON.toJSONString(jsonResult);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonString);
            printWriter.close();
            return;
        } catch (MalformedJwtException e) {
            String message = "非法访问,滚!";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_MALFORMED, message);
            String jsonString = JSON.toJSONString(jsonResult);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonString);
            printWriter.close();
            return;
        } catch (ExpiredJwtException e) {
            String message = "登录已过期,请重新登陆!";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_EXPIRED, message);
            String jsonString = JSON.toJSONString(jsonResult);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonString);
            printWriter.close();
            return;
        } catch (Throwable e) {
            String message = "服务器忙,请稍后再试";
            e.printStackTrace();
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_UNKNOWN, message);
            String jsonString = JSON.toJSONString(jsonResult);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonString);
            printWriter.close();
            return;
        }

        //将根据从JWT中解析得到的数据来创建认证信息
        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);
        //解析创建认证信息的权限数据
        String authoritiesJsonString = claims.get("authorities",String.class);
        log.debug("从JWT中解析得到数据：id={}", id);
        log.debug("从JWT中解析得到数据：username={}", username);
        log.debug("从JWT中解析得到数据：authorities1={}", authoritiesJsonString);
        log.debug("从JWT中解析得到数据：authorities1的数据类型={}", authoritiesJsonString.getClass().getName());
//        log.debug("从JWT中解析得到数据：authorities1的元素数据类型={}", ((ArrayList)authorities1).get(0).getClass().getName());

        List authorities=JSON.parseArray(authoritiesJsonString,
                SimpleGrantedAuthority.class);

        log.debug("从jwt中解析到的id为:id={}", id);
        log.debug("从jwt中解析到的username为:{}", username);


        //准备创建认证信息的权限数据
//        List<GrantedAuthority> list = new ArrayList();
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("这是一个山寨权限");
//        list.add(grantedAuthority);

        //准备用于创建认证信息的当事人数据
        LoginPrincipal loginPrincipal = new LoginPrincipal();
        loginPrincipal.setId(id);
        loginPrincipal.setUsername(username);

        //创建认证信息
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginPrincipal, null, authorities
        );
        log.debug("即将向SecurityContext存入认证信息#{}",authentication);
        //将认证信息存储在securityContext中
        SecurityContextHolder.getContext().setAuthentication(authentication);


        //放行
        filterChain.doFilter(request, response);

    }

}
