/**
 * FileName: AddTokenFilter
 * Author:   fanjiangpan
 * Date:     2018/3/15 19:06
 * Description: 为每次访问添加签名Sign
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/15           版本号
 */
package top.biandeshen.sandstorm.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import top.biandeshen.sandstorm.config.SysConst;
import top.biandeshen.sandstorm.manager.TokenManager;
import top.biandeshen.sandstorm.shiro.Constants;
import top.biandeshen.sandstorm.shiro.UserIDToken;
import top.sandstorm.common.commons.MD5Util;
import top.sandstorm.common.rest.ServiceException;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 〈为每次访问添加签名Sign〉
 *
 * @author fanjiangpan
 * @since 1.0.0
 */
public class AddTokenFilter extends AuthenticatingFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TokenManager tokenManager;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        return null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getAttribute(Constants.AUTHORIZATION) == null) {
            //用户名
            String account = request.getParameter("account");
            //密码
            String password = request.getParameter("password");
            if (account == null || password == null) {
                return false;
            }
            UserIDToken userIDToken;
            //当前缓存中token
            try {
                userIDToken = tokenManager.getToken(String.valueOf(Constants.USER_ID_TMP));
                System.out.println("UserIdToken="+userIDToken.getToken());
                String sign = MD5Util.md5(account + MD5Util.md5(password) + userIDToken.getToken(), SysConst.SALT);
                request.setAttribute(Constants.AUTHORIZATION, sign);
                request.setAttribute(Constants.CURRENT_USER_ID,userIDToken.getUserId());
                return true;
            }catch (NullPointerException e){
                throw new ServiceException(e.getMessage(),e);
            }finally {
                saveRequestAndRedirectToLogin(request,response);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        super.redirectToLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        return super.onLoginFailure(token, e, request, response);
    }
}
