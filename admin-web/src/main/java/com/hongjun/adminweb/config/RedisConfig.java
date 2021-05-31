package com.hongjun.adminweb.config;

import com.hongjun.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author hongjun500
 * @date 2021/3/19 17:38
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {
}
