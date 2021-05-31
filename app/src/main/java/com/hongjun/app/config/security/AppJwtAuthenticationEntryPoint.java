package com.hongjun.app.config.security;

import cn.hutool.json.JSONUtil;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.response.CommonReturnType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hongjun500
 * @date 2021/3/18 14:43
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description: 无登录凭证时返回信息
 */
@Component
public class AppJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Map<Object, Object> map = new HashMap<>(16);
        map.put("errCode", EnumBusinessError.USER_NOT_LOGIN.getErrCode());
        map.put("errMsg", EnumBusinessError.USER_NOT_LOGIN.getErrMsg());
        response.getWriter().println(JSONUtil.parse(CommonReturnType.create(map,"fail")));
        response.getWriter().flush();
    }
}
