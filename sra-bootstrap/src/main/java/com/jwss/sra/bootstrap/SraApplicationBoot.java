package com.jwss.sra.bootstrap;

import cn.hutool.system.SystemUtil;
import com.jwss.sra.config.properties.DefaultProperties;
import com.jwss.sra.config.properties.DevEnableProperties;
import com.jwss.sra.framework.constant.GlobalValue;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author jwss
 * @date 2022-1-12 14:12:24
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.jwss.sra"})
public class SraApplicationBoot {
    private static final Logger logger = LoggerFactory.getLogger(SraApplicationBoot.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SraApplicationBoot.class, args);
        Environment environment = context.getEnvironment();
        String contextPath = environment.getProperty("server.servlet.context-path");
        contextPath = StringUtil.isNullOrEmpty(contextPath) ? contextPath : "";

        DefaultProperties defaultProperties = (DefaultProperties)context.getBean("defaultProperties");
        GlobalValue.PORT = Integer.valueOf(Objects.requireNonNull(environment.getProperty("server.port")));
        GlobalValue.AGREEMENT = defaultProperties.getAgreement();
        GlobalValue.SERVER_IP = defaultProperties.getDomain();

        String[] urls = {
                String.format("http://localhost:%s%s", GlobalValue.PORT, contextPath),
                GlobalValue.getServerUrl()
        };

        logger.info("接口访问地址：{} || {}", urls[0], urls[1]);
        logger.info("接口文档地址：{}/doc.html || {}/doc.html", urls[0], urls[1]);
        logger.info("测试接口访问：{}/test/index || {}/test/index", urls[0], urls[1]);
        DevEnableProperties devEnableProperties = (DevEnableProperties)context.getBean("devEnableProperties");
        logger.warn("强密码：{}, 权限缓存状态：{}", devEnableProperties.getStrongPassword(), devEnableProperties.getPermissionCache());
        GlobalValue.START_TIME = System.currentTimeMillis();
    }
}
