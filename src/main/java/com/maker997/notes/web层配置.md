### web.xml 文件的配置
* ssm 框架的继承顺序: mybatis -> spring -> springMVC.配置前端控制器的时候要spring所有的配置都加载进去
```
       <servlet>
           <servlet-name>seckill-dispather</servlet-name>
           <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
           <init-param>
               <param-name>contextConfigLocation</param-name>
               <param-value>classpath:spring/spring-*.xml</param-value>
           </init-param>
       </servlet>
       <servlet-mapping>
           <servlet-name>seckill-dispather</servlet-name>
           <url-pattern>/</url-pattern>
       </servlet-mapping>
```

### 配置 springMVC

1. 创建 spring-web.xml 文件
2. 开启 springMVC 注解模式
3. 静态资源默认 servlet 配置
4. 配置 jsp 显示 ViewResolver
5. 自动扫描web 相关的bean
```
    <!--1. 开启注解模式
        1) 自动注册DeaultAnnotationHandlerMapping ,AnnotationMethodHandlerAdapter
        2) 提供一系列的数据绑定 数字和日期的format @NumberFormat,@DateTimeFormat,xml,json 的支持
    -->
    <mvc:annotation-driven/>

    <!--2. 静态资源默认 servlet 配置
        1) 加入静态资源的处理: js,gif,png
        2) 允许使用"/"做整体映射
    -->
    <mvc:default-servlet-handler/>

    <!--3. 配置 jsp 显示 ViewSolver-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="prefix" value="/WEB-INF/jsp"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--4. 扫描 web 相关的 bean-->
    <context:component-scan base-package="com.maker997.web"/>

```
