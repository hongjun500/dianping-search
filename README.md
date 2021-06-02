### 基于ElasticSearch的搜索点评工程
### 组织结构
``` lua
dianping
├──common -- 工具类及通用代码
├──data-model-mbg -- MyBatisGenerator生成的数据库操作代码及其DO类
├──security -- SpringSecurity封装公用模块(无需改动)
├──admin-web -- 后台管理系统接口,基于layui的layuimini前端UI框架操作界面
├──app-server -- app/H5接口
```
### 技术选型

| 技术                 | 说明                | 官网                                           |
| -------------------- | ------------------- | ---------------------------------------------- |
| SpringBoot           | 容器+MVC框架        | https://spring.io/projects/spring-boot         |
| SpringSecurity       | 认证和授权框架      | https://spring.io/projects/spring-security     |
| MyBatis              | ORM框架             | http://www.mybatis.org/mybatis-3/zh/index.html |
| MyBatisGenerator     | 数据层代码生成      | http://www.mybatis.org/generator/index.html    |
| Redis                | 分布式缓存          | https://redis.io/                              |
| Nginx                | 静态资源服务器      | https://www.nginx.com/                         |
| Docker               | 应用容器引擎        | https://www.docker.com                         |
| Druid                | 数据库连接池        | https://github.com/alibaba/druid               |
| OSS                  | 对象存储            | https://github.com/aliyun/aliyun-oss-java-sdk  |
| JWT                  | JWT登录支持         | https://github.com/jwtk/jjwt                   |
| Lombok               | 简化对象封装工具    | https://github.com/rzwitserloot/lombok         |
| Hutool               | Java工具类库        | https://github.com/looly/hutool                |
| PageHelper           | MyBatis物理分页插件 | http://git.oschina.net/free/Mybatis_PageHelper |
| knife4j           | 基于Swagger-UI的文档生成工具        | https://doc.xiaominfo.com      |
| Hibernator-Validator | 验证框架            | http://hibernate.org/validator                 |

注：admin-web没有前后端分离，页面跳转里无法携带token(JWT登录授权过滤器JwtAuthenticationTokenFilter会拦截掉。。。),因此添加了Cookie...
app-server无需此操作
app-server工程中有的接口需要放行(无需登录)，在配置文件的
secure:
  ignored:
     urls
中以接口路由名称放行即可！！！

部署、更新方式及其注意事项：

- 更新时不要搞错了工程目录，admin-web后台UI操作系统、app-server是APP的接口系统！！！


- 确保本地开发环境启动无误，三种环境中除生产环境里白名单限制较大外其它配置应相同！！！

-三种环境配置文件改动时需要注意同时更新以免生产环境上出现问题！！！

-打包方式改用maven插件排除了springboot的打包插件，将原先的一个jar分为了工程jar、resources、lib

- 两个项目工程都有分环境配置的文件：dev开发，test测试，prod生产环境

- 更改了java文件只需将打包出来的jar替换即可

- 静态资源文件、配置文件之类的更新则是替换对应工程下resources相应的文件即可

- 第三方依赖或者是security、data-model-mbg、common三个模块下的内容则将lib下对应的jar替换，一般来说这security、common两个模块不用改动，第三方依赖也无需引入，因此这部分可忽略不计
          
