package com.jwss.sra.config.web;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import com.jwss.sra.config.properties.FileUploadProperties;
import com.jwss.sra.config.properties.SatokenProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jwss
 * @date 2022-1-17 16:12:05
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Resource
    private FileUploadProperties fileUploadProperties;
    @Resource
    private SatokenProperties satokenProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String locations = "file:" + fileUploadProperties.getLocalUrl();
        String pathPatterns = fileUploadProperties.getBrowserUrl() + "**";
        logger.info("本地上传路径：{}", locations);
        logger.info("文件映射路径：{}", pathPatterns);
        registry.addResourceHandler(pathPatterns).addResourceLocations(locations);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**").excludePathPatterns(getExcludeList());
    }

    /**
     * 注册 [Sa-Token全局过滤器]
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter().setBeforeAuth(r -> {
            // ---------- 前置函数：在每次认证函数之前执行，设置一些安全响应头 ----------
            SaHolder.getResponse()
                    // 服务器名称
                    .setServer("sra-server")
                    // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
                    .setHeader("X-Frame-Options", "SAMEORIGIN")
                    // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
                    .setHeader("X-XSS-Protection", "1; mode=block")
                    // 禁用浏览器内容嗅探
                    .setHeader("X-Content-Type-Options", "nosniff");
        });
    }

    /**
     * 获取放行路由列表
     *
     * @return 路由集合
     */
    public List<String> getExcludeList() {
        List<String> excludes = satokenProperties.getExcludes();
        logger.info("############# 放行 ##############");
        if (excludes != null) {
            excludes.forEach(logger::info);
        }
        logger.info("############# 路由 ##############");
        return excludes;
    }
}
