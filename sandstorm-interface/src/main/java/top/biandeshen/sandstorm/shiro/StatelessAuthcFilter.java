package top.biandeshen.sandstorm.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import top.biandeshen.sandstorm.config.SysConst;
import top.biandeshen.sandstorm.manager.TokenManager;
import top.sandstorm.common.commons.MD5Util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author web2017 2017-08-30
 */
public class StatelessAuthcFilter extends PermissionsAuthorizationFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private IAccountService accountService;

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

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
        //获取用户id
        UserIDToken userIDToken;
        if (Constants.USER_ID_TMP == null){
            return false;
        }
        userIDToken = tokenManager.getToken(String.valueOf(Constants.USER_ID_TMP));
        String  userid = userIDToken.getUserId();
        Boolean hasId = userid != null;
        String clientToken = userIDToken.getToken();
        if (userid == null || clientToken == null){
            return false;
        }
        String clientSign = MD5Util.md5(MD5Util.md5(userid)+MD5Util.md5(clientToken,SysConst.SALT) , SysConst.SALT);
        //获取签名
        Boolean hadSign = clientSign != null;


        //验证用户id,姓名，密码，如果成功，加密后获取token生成一个签名放入header，命名为Constant.Autor
        //如果存在签名，则校验签名是否正确，用checkSign(autor)进行验证，如果不正确，返回flase
        //如果不存在签名，
        Boolean isSignOK = checkSign(request, userid,clientSign);
        // 此处其实已经验证了用户的身份，下面一句不过是在形式上登录一下
        if (hasId&&hadSign&&isSignOK) {
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

    private Boolean checkSign(ServletRequest request, String userid,String clientSign) {
        // 有userID和sign，说明登录过，我们还要验证token的合法性，非rest没有这么麻烦
        // 服务端用token加密参数为serverSign，比对客户端sign
        String token = accountService.findTokenByUserId(userid);
        String serverSign = MD5Util.md5(MD5Util.md5(userid)+MD5Util.md5(token,SysConst.SALT) , SysConst.SALT);
        if (logger.isDebugEnabled()) {
            logger.debug("服务端计算出来的签名是：" + serverSign);
        }
        return clientSign.equals(serverSign);
    }

    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        super.redirectToLogin(request, response);
    }
}

