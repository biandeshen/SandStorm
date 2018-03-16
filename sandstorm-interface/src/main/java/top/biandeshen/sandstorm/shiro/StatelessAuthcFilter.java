package top.sandstorm.org.shiro;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Account;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.sandstorm.common.commons.MD5Util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author web2017 2017-08-30
 */
public class StatelessAuthcFilter extends PermissionsAuthorizationFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private IAccountService accountService;

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

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

//        if (null != getSubject(request, response)
//                && getSubject(request, response).isAuthenticated()) {
//            return true;//已经认证过直接放行
//        }
//        return false;//转到拒绝访问处理逻辑
//

        HttpServletRequest req = (HttpServletRequest) request;
//        //用户名
//        String account = req.getParameter("account");
//        //密码
//        String password = MD5Util.md5(req.getParameter("password"));

        String clientSign = (String) req.getAttribute(Constants.AUTHORIZATION);
        System.out.println(req.getAttribute(Constants.AUTHORIZATION));
        //获取用户id
        String  userid = String.valueOf(Constants.USER_ID_TMP);
        Boolean hasId = userid != null;
        //获取签名
        Boolean hadSign = clientSign != null;

//        if (req.getHeader(Constants.AUTHORIZATION) != null){
//            //获取该签名，并验证
//
//        }else {
//            //生成并check
//        }
        //验证用户id,姓名，密码，如果成功，加密后获取token生成一个签名放入header，命名为Constant.Autor
        //如果存在签名，则校验签名是否正确，用checkSign(autor)进行验证，如果不正确，返回flase
        //如果不存在签名，
        Boolean isSignOK = checkSign(request, userid, clientSign);
        // 此处其实已经验证了用户的身份，下面一句不过是在形式上登录一下
        if (hasId && hadSign&&isSignOK) {
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
        //获取用户姓名
        String account = request.getParameter("account");
        //获取用户密码
        String password = request.getParameter("password");

//        List<String> keys = new ArrayList<String>(request.getParameterMap().keySet());
//
//        //业务参数连接成字符串
//        String linkString = "";
//
//        for (String key : keys) {
//            // 除开userid和sign，其余字符串连接起来
//            if (!"account".equals(key) && !"password".equals(key)) {
//                linkString += key + "=" + request.getParameter(key) + "&";
//            }
//        }
//        if (!StringUtils.isEmpty(linkString)) {
//            linkString = linkString.substring(0, linkString.length() - 1);
//        }

        String token = accountService.findTokenByUserId("1");
        String serverSign = MD5Util.md5(account+MD5Util.md5(password) + token);
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

