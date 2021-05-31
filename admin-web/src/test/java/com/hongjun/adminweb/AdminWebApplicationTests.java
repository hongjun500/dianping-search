package com.hongjun.adminweb;

import com.hongjun.adminweb.service.SysMenuService;
import com.hongjun.adminweb.service.SysResourceService;
import com.hongjun.adminweb.vo.SysMenuVO;
import com.hongjun.common.util.RedisUtil;
import com.hongjun.dataobject.SysResourceDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.*;

@SpringBootTest
@Slf4j
class AdminWebApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysMenuService sysMenuService;

    @Test
    void contextLoads() {
        Set<Object> set = new HashSet<>();
        HashSet set2 = new HashSet<>();
        set2.add("");
        set.add("fdsfsdf");
        redisUtil.set("data", "data");
        List<SysMenuVO> sysMenuVOS = sysMenuService.treeListAdminId(9L);
        log.info("数据{}",redisUtil.get("data").toString());
    }

    @Test
    void setSysResourceService() {
        List<SysResourceDO> sysResourceDOS = sysResourceService.listAll();
        System.out.println(sysResourceDOS.isEmpty());
    }

    @Autowired
    ApplicationContext applicationContext;
int i;
    @Test
    void testContext(){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("1",1);
        map.put("2","");
        map.put("null",null);
        for (Object objects:map.values()){
            System.out.println(objects);
        }
        System.out.println(map.toString());
    }


}
