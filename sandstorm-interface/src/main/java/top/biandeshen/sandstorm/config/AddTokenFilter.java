/**
 * FileName: AddTokenFilter
 * Author:   fanjiangpan
 * Date:     2018/3/15 19:06
 * Description: 为每次访问添加签名Sign
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/15           版本号
 */
package top.sandstorm.org.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.biandeshen.sandstorm.manager.Impl.RedisTokenManager;
import top.sandstorm.common.commons.MD5Util;
import top.sandstorm.org.shiro.Constants;
import top.sandstorm.org.shiro.IAccountService;
import top.sandstorm.org.shiro.UserIDToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈为每次访问添加签名Sign〉
 *
 * @author fanjiangpan
 * @since 1.0.0
 */
public class AddTokenFilter extends AuthenticatingFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private IAccountService accountService;

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        return null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //用户名
        String account = request.getParameter("account");
        //密码
        String password = MD5Util.md5(request.getParameter("password"));
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        RedisTokenManager redisTokenManager = new RedisTokenManager();
        //当前缓存中token
        UserIDToken userIDToken = redisTokenManager.getToken(Constants.CURRENT_USER_ID);
        String sign = MD5Util.md5(account + password + userIDToken.getToken(), SysConst.SALT);
        if (request.getAttribute(Constants.AUTHORIZATION) == null) {
            request.setAttribute(Constants.AUTHORIZATION, sign);
            return true;
        } else {
            return false;
        }
    }


}
