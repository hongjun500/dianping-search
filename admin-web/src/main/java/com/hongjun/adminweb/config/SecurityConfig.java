package com.hongjun.adminweb.config;

import com.hongjun.adminweb.service.SysAdminService;
import com.hongjun.adminweb.service.SysResourceService;
import com.hongjun.common.error.BusinessException;
import com.hongjun.config.BaseSecurityConfig;
import com.hongjun.config.DynamicSecurityService;
import com.hongjun.dataobject.SysResourceDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hongjun500
 * @date 2021/3/19 18:02
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig  extends BaseSecurityConfig{

    @Autowired
    private SysResourceService sysResourceService;
    @Autowired
    private SysAdminService sysAdminService;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> {
            try {
                return sysAdminService.loadUserByUsername(username);
            } catch (BusinessException e) {
                e.printStackTrace();
                log.warn("获取登录用户信息失败{}",e.getErrMsg());
                return null;
            }
        };
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<SysResourceDO> resourceList = sysResourceService.listAll();
                for (SysResourceDO resource : resourceList) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
                }
                return map;
            }
        };
    }

}
