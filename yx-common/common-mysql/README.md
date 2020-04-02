#### mysql连接说明

##### 1.连接池使用druid
用于帮助你在Spring Boot项目中轻松集成Druid数据库连接池和监控。
<dependency>
   <groupId>com.alibaba</groupId>
   <artifactId>druid-spring-boot-starter</artifactId>
   <version>1.1.21</version>
</dependency>

#### 2.监控页面
http://localhost:9088/api/druid/sql.html
需要开启监控配置。默认是关闭的

#### 3.mybatis设置
###### 别名设置
mybatis.type-aliases-package=com.jesse.**.model
###### 设置自动驼峰命名转换 
mybatis.configuration.map-underscore-to-camel-case=true
###### sql执行超时时间
mybatis.configuration.default-statement-timeout=10
mybatis.mapper-locations=classpath*:mapper/*.xml

tkMybatis+mybatis驼峰转换，几乎满足所有关于sql的幻想

#### 参考：
https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter