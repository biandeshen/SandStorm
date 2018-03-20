/**
 * FileName: ShiroConfig
 * Author:   fanjiangpan
 * Date:     2018/3/9 10:21
 * Description: Shiro框架的java配置文件
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/9           版本号
 */
package top.biandeshen.sandstorm.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.session.NoSessionCreationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import top.biandeshen.sandstorm.entity.Menu;
import top.biandeshen.sandstorm.manager.TokenManager;
import top.biandeshen.sandstorm.service.AccountService;
import top.biandeshen.sandstorm.shiro.Constants;
import top.biandeshen.sandstorm.shiro.IAccountService;
import top.biandeshen.sandstorm.shiro.ShiroRestRealm;
import top.biandeshen.sandstorm.shiro.StatelessAuthcFilter;
import top.sandstorm.common.commons.Exceptions;

import javax.servlet.Filter;
import java.io.IOException;
import java.util.*;

/**
 * 〈Shiro框架的java配置文件〉
 *
 * @author fanjiangpan
 * @since 1.0.0
 * <p>
 * 参考链接：http://blog.csdn.net/u013615903/article/details/78781166
 */
@Configuration
public class ShiroConfig {

    /**
     * <bean id="shiroFilter" class="ShiroFilterFactoryBean" />
     * 总过滤器
     *
     * @param securityManager 依赖的安全管理器
     * @param authcFilter     自定义权限过滤器
     */
    @Bean
    @Autowired
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, StatelessAuthcFilter authcFilter/*, AddTokenFilter addTokenFilter*/) throws IOException {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        //bean.setFilters(filters);

        /* 注册实际的拦截器 */
        Map<String, Filter> filters = bean.getFilters();
        //自定义的无状态权限验证过滤器
        filters.put("stateLessAuthcFilter", authcFilter);
        // shiro提供的不做任何处理的过滤器
        filters.put("anon", new AnonymousFilter());
//        filters.put("addToken",/* new AddTokenFilter() */addTokenFilter);
        // shiro提供的过滤器
        filters.put("noSessionCreation", new NoSessionCreationFilter());

        /* 登录跳转链接 */
        bean.setLoginUrl("/rbac/account/login");
        bean.setUnauthorizedUrl("/rbac/account/unauthorized");
        /* 不同的url用不同的过滤器拦截 */
        //拦截器.
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //不拦截的写在前面
        //通过此路径获取验证
        filterChainDefinitionMap.put("/rbac/account/login", "anon");
        filterChainDefinitionMap.put("/rbac/account/unauthorized", "anon");
        filterChainDefinitionMap.put("/rbac/account/logout", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        //todo
        //生产环境开启权限和身份验证
        String propertiesPath = "/application.properties";
        Resource resource = new ClassPathResource(propertiesPath);
        Properties props = new Properties();
        props.load(resource.getInputStream());
        if (props.getProperty("spring.profiles.active") == null ||
                props.getProperty("spring.profiles.active").equals("production")) {
            // 其余所有路径都会被拦截
            bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
            //todo 要么设置拦截器，拦截用户的method，要么在此处用rest，赋予用户的权限
            //根据用户ID查询权限（permission），放入到Authorization里。
    /*List<SysPermission> permissionList = sysPermissionService.selectByMap(map);
    Set<String> permissionSet = new HashSet<String>();
    for(SysPermission Permission : permissionList){
        permissionSet.add(Permission.getName());
    }*/
/*            Set<String> permissons = 查询用户角色权限列表(findpromissionbyid);
            for (String str : permissons) {
                filterChainDefinitionMap.put(str.get访问路径，rest[str.get访问方法])
            }*/
            filterChainDefinitionMap.put("/**", "noSessionCreation,stateLessAuthcFilter");
            // 添加路径拦截
        } else {
            bean.getFilterChainDefinitionMap().put("/**", "anon");
        }
        return bean;
    }
//
//    @Bean
//    public AddTokenFilter addTokenFilter(TokenManager redisTokenManager) {
//        AddTokenFilter addTokenFilter = new AddTokenFilter();
////        addTokenFilter.setRedisTokenManager(redisTokenManager);
//        return addTokenFilter;
//    }

    @Bean
    public StatelessAuthcFilter authFilter(AccountService accountService) {
        StatelessAuthcFilter authcFilter = new StatelessAuthcFilter();
        authcFilter.setAccountService(accountService);
        return authcFilter;
    }

    @Bean
    @Autowired
    public DefaultWebSecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);

        final DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdCookieEnabled(false);
        sessionManager.setSessionValidationSchedulerEnabled(false);
        securityManager.setSessionManager(sessionManager);
        // 禁止session被创建
        securityManager.setSubjectFactory(new DefaultWebSubjectFactory() {
            @Override
            public Subject createSubject(SubjectContext context) {
                context.setSessionCreationEnabled(false);
                return super.createSubject(context);
            }
        });
        // 禁用使用Sessions 作为存储策略的实现
        final DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(new DefaultSessionStorageEvaluator() {
            @Override
            public boolean isSessionStorageEnabled() {
                return false;
            }
        });
        securityManager.setSubjectDAO(subjectDAO);
        SecurityUtils.setSecurityManager(securityManager);

        return securityManager;
    }


    @Bean
    @Autowired
    public Realm realm(@Qualifier("accountService") IAccountService accountService) {
        return new ShiroRestRealm(accountService);
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    @Autowired
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

}
