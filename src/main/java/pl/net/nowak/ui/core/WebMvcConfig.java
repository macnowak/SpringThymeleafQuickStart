package pl.net.nowak.ui.core;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Created by mno
 */
//@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Bean
    public MessageSource messageSource() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("strings/messages");
            messageSource.setDefaultEncoding("UTF-8");
            return messageSource;
    }
//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LayoutInterceptor());
//    }
}
