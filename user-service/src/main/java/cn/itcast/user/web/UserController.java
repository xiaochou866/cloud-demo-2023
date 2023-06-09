package cn.itcast.user.web;

import cn.itcast.user.config.PatternProperties;
import cn.itcast.user.pojo.User;
import cn.itcast.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/user")
//@RefreshScope
@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT) // 实现读取nacos的配置的热更新
public class UserController {

    @Autowired
    private UserService userService;

    //@Value("${pattern.dateformat}")
    //private String dateformat;
    @Autowired
    PatternProperties patternProperties;

    @GetMapping("now")
    private String now(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(patternProperties.getDateformat()));
    }

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    //@GetMapping("/{id}")
    //public User queryById(@PathVariable("id") Long id, String truth) {
    //    return userService.queryById(id);
    //}

    //@GetMapping("/{id}")
    //public User queryById(@PathVariable("id") Long id,
    //                      @RequestHeader(value = "Truth", required = false) String truth) {
    //    System.out.println("truth: "+ truth);
    //    return userService.queryById(id);
    //}

    // 对指定参数的请求添加休眠实现 检测熔断
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id) throws InterruptedException {
        if(id==1){
            // 休眠，触发熔断
            Thread.sleep(60);
        }else if(id==2){
            throw new RuntimeException("故意抛出异常，触发异常比例熔断");
        }
        return userService.queryById(id);
    }
}
