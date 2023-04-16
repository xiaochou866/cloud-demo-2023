package cn.itcast.feign.config;
import feign.Logger;
import org.springframework.context.annotation.Bean;

public class DefaultFeighConfiguration {
    @Bean
    public Logger.Level logLevel(){
        return Logger.Level.BASIC;
    }
}
