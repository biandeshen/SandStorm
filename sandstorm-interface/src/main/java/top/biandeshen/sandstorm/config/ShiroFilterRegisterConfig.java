/**
 * FileName: ShiroFilterRegisterConfig
 * Author:   fanjiangpan
 * Date:     2018/3/9 16:58
 * Description: 取消SpringBoot自动注册我们的Filter
 *              参考链接https://www.cnblogs.com/yuananyun/p/8033822.html
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/9           版本号
 */
package top.biandeshen.sandstorm.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.sandstorm.org.shiro.StatelessAuthcFilter;

/**
 * 〈参考链接https://www.cnblogs.com/yuananyun/p/8033822.html〉
 *
 * @author fanjiangpan
 * @since 1.0.0
 */
@Configuration
public class ShiroFilterRegisterConfig {

    @Bean
    public FilterRegistrationBean shiroStateLessAuthcFilterRegistration(StatelessAuthcFilter authcFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(authcFilter);
        registration.setEnabled(false);
        return registration;
    }
}