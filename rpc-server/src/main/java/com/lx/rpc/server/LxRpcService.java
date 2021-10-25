package com.lx.rpc.server;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: Rpc 服务注解 （标注在服务实现类上）
 * @Author: LinXin_
 * @CreateTime: 2021/10/24 14:44
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface LxRpcService {

    /**
     * 服务接口类
     */
    Class<?> value();

    /**
     *服务版本号
     */
    String version() default "";
}
