package top.biandeshen.sandstorm.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import top.biandeshen.sandstorm.config.SysConst;
import top.biandeshen.sandstorm.entity.Account;
import top.biandeshen.sandstorm.manager.TokenManager;
import top.biandeshen.sandstorm.service.AccountService;
import top.sandstorm.common.commons.Exceptions;
import top.sandstorm.common.commons.MD5Util;
import top.sandstorm.common.rest.ForbidException;
import top.sandstorm.common.rest.ServiceException;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author web2017 2017-08-30
 */
public class StatelessAuthcFilter extends PermissionsAuthorizationFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private IAccountService accountService;

//    public void setAccountService(IAccountService accountService) {
//        this.accountService = accountService;
//    }

    @Autowired
    private AccountService aService;

    @Autowired
    private TokenManager tokenManager;

    /**
     * 访问是否受到许可
     * 1、没有userid或者sign，说明没登录
     * 2、有二者，但是加密摘要和服务端加密摘要不一致，说明为伪造
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws IOException
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //获取客户端传来的token和id，与缓存中token进行比较，如果成功，则延长时间
        //token和id通过加密生成sign进行传输
        //或许最早的web2017就是对的。。。。
        String sign = req.getHeader("Authorization");
        //通过sign获取到userid 和 token
        //todo
        String token;
        String userid;

        Boolean isAuthenticated = tokenManager.checkToken(token);
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            resp.setStatus(HttpStatus.OK.value());
        }
        String username;
        String password;
        //获取用户
        if (req.getParameter("account") != null || req.getParameter("password") != null) {
            username = req.getParameter("account");
            password = req.getParameter("password");
        } else {
            return false;
        }
        //获取用户id
        Account account = aService.findBy("account", username);
        System.out.println(account);
        UserIDToken userIDToken;

        //todo tokenManager.checkToken() 续两分钟;
        userIDToken = tokenManager.getToken(String.valueOf(account.getId()));
        String userid = userIDToken.getUserId();
        Boolean hasId = userid != null;
        String clientToken = userIDToken.getToken();
        if (userid == null || clientToken == null) {
            return false;
        }
        String clientSign = MD5Util.md5(MD5Util.md5(userid) + MD5Util.md5(clientToken, SysConst.SALT), SysConst.SALT);
        //获取签名
        Boolean hadSign = clientSign != null;


        //验证用户id,姓名，密码，如果成功，加密后获取token生成一个签名放入header，命名为Constant.Autor
        //如果存在签名，则校验签名是否正确，用checkSign(autor)进行验证，如果不正确，返回flase
        //如果不存在签名，
        Boolean isSignOK = checkSign(request, userid, clientSign);
        // 此处其实已经验证了用户的身份，下面一句不过是在形式上登录一下
        if (hasId && hadSign && isSignOK) {
            // login之后request中才有principle，才能进行下一步鉴权
            SecurityUtils.getSubject().login(new UserIDToken(userid));
        } else {
            return false;
        }

        //获得用户这一次要访问的资源路径
        final String servletPath = ((HttpServletRequest) request).getServletPath();
        // 鉴权：判断url是否在用户的权限之中,注意第三个参数是这次请求的权限字符串，应该和realm中存储的一致
        return super.isAccessAllowed(request, response, new String[]{servletPath});
    }

    private Boolean checkSign(ServletRequest request, String userid, String clientSign) {
        // 有userID和sign，说明登录过，我们还要验证token的合法性，非rest没有这么麻烦
        // 服务端用token加密参数为serverSign，比对客户端sign
        String token = accountService.findTokenByUserId(userid);
        String serverSign = MD5Util.md5(MD5Util.md5(userid) + MD5Util.md5(token, SysConst.SALT), SysConst.SALT);
        if (logger.isDebugEnabled()) {
            logger.debug("服务端计算出来的签名是：" + serverSign);
        }
        /* 如果用户状态为禁用，则抛出异常 */
        //todo
/*        if (enable == 0){
            throw new ForbidException();
        }*/
        return clientSign.equals(serverSign);
    }

    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        super.redirectToLogin(request, response);
    }
}

