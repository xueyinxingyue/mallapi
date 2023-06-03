package com.xueyin.mallapi.config;

import com.xueyin.mallapi.interceptor.CheckTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private CheckTokenInterceptor checkTokenInterceptor;
    @Value("${upload.path}")
    private String uploadPath;

    //解决跨域问题
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //设置访问源地址
        config.addAllowedOriginPattern("*");
        //设置访问源请求头
        config.addAllowedHeader("*");
        //设置访问源请求方法
        config.addAllowedMethod("*");
        //有效期1800秒
        config.setMaxAge(1800L);
        //添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        //返回新的CorsFilter
        return new CorsFilter(source);
    }

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkTokenInterceptor)
                .addPathPatterns("/**")                  //配置要拦截的请求
                .excludePathPatterns("/captcha","/login","/upload/**");                 //配置不拦截的请求
    }

    //配置资源的真实路径     虚拟路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + uploadPath);
    }
}
