package org.xlet.upgrader;

import org.xlet.upgrader.util.dozer.SimpleDateConverter;
import org.xlet.upgrader.util.dozer.UriComponentConverter;
import org.xlet.upgrader.web.interceptor.ReqInfoInterceptor;
import com.google.common.collect.Maps;
import org.dozer.CustomConverter;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.util.Map;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-2 下午3:21
 * Summary:
 */
@ComponentScan("cn.w.im")
@EnableAutoConfiguration
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ReqInfoInterceptor()).addPathPatterns("/**/**/**").excludePathPatterns("/static/**").excludePathPatterns("/favicon.ico");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/dashboard/login").setViewName("dashboard/login");
        registry.addViewController("/dashboard/index").setViewName("dashboard/index");
        registry.addViewController("/dashboard").setViewName("dashboard/index");
        registry.addViewController("/").setViewName("index");
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(
                        new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"),
                        new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html")
                );
            }
        };
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("500MB");
        factory.setMaxRequestSize("500MB");
        return factory.createMultipartConfig();
    }

    @Bean
    public DozerBeanMapperFactoryBean dozer(@Value("${app.web.context}") String context) {
        DozerBeanMapperFactoryBean factory = new DozerBeanMapperFactoryBean();
        factory.setMappingFiles(new Resource[]{new ClassPathResource("/dozer/version-mapping.xml"), new ClassPathResource("/dozer/changelog-mapping.xml")});
        Map<String, CustomConverter> converterMap = Maps.newHashMap();

        converterMap.put("dateConverter", new SimpleDateConverter("yyyy-MM-dd HH:mm:ss"));
        converterMap.put("anyUriConverter", new UriComponentConverter(context, "/download/any?f="));
        converterMap.put("uriConverter",new UriComponentConverter(context, ""));

        factory.setCustomConvertersWithId(converterMap);
        return factory;
    }


}
