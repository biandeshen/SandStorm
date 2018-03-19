/**
 * FileName: LoginControllerReturnAdvisor
 * Author:   fanjiangpan
 * Date:     2018/3/15 15:35
 * Description: 处理登录时的返回值
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/15           版本号
 */
package top.biandeshen.sandstorm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 〈处理登录时的返回值〉
 *
 * @author fanjiangpan
 * @since 1.0.0
 */
//@Component
//@Aspect
public class LoginControllerReturnAdvisor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* top.biandeshen.sandstorm.api.AccountAPI.login(..))")
    public void resultMapAspect(){}

    @AfterReturning("resultMapAspect()")
    public Object getResultMap(ProceedingJoinPoint joinPoint) throws Throwable {
         Object resultMap = joinPoint.proceed();
         return resultMap;
    }
}
