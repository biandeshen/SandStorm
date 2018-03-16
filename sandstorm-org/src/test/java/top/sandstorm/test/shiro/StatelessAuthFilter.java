/**
 * FileName: StatelessAuthFilter
 * Author:   fanjiangpan
 * Date:     2018/3/9 9:09
 * Description: 无状态的身份验证过滤器
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/9           版本号
 */
package top.sandstorm.org.shiro;

import org.apache.shiro.SecurityUtils;
import top.sandstorm.common.commons.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈无状态的身份验证过滤器〉
 *
 * @author fanjiangpan
 * @since 1.0.0
 */
public class StatelessAuthFilter extends PermissionsAuthorizationFilter {
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
        String userid = request.getParameter("userid");
        Boolean hasId = userid != null;

        String clientSign = request.getParameter("sign");
        Boolean hadSign = clientSign != null;

        Boolean isSignOK = checkSign(request, userid, clientSign);
        //此处实际上已经验证了用户的身份，下面不过是形式上的登录
        if (hasId && hadSign && isSignOK) {
            //login之后request中才有principle，才能进行下一步鉴权
            SecurityUtils.getSubject().login(new UserIDToken(userid));
        } else {
            return false;
        }

        //获得用户这一次要访问的资源路径
        final String servletPath = ((HttpServletRequest) request).getServletPath();
        //鉴权：判断url是否在用户的权限之中，注意第三个参数是这次请求的权限字符串，应该和realm中存储的一致
        return super.isAccessAllowed(request, response, new String[]{servletPath});
    }

    private Boolean checkSign(ServletRequest request, String userid, String clientSign) {
        //如果有userid 和 sign,说明登录过，所以还需验证token的合法性
        //服务端token用加密参数为ServerSign,对比客户端sign

        List<String> keys = new ArrayList<>(request.getParameterMap().keySet());

        //业务参数连接成字符串
        String linkString = "";

        for (String key : keys) {
            //除开userid和sign，其余字符串连接起来
            if (!"userid".equals(key) && !"sign".equals(key)) {
                linkString += key + "=" + request.getParameter(key) + "&";
            }
        }

        if (!StringUtils.isEmpty(linkString)) {
            linkString = linkString.substring(0, linkString.length() - 1);
        }
        String serverToken = accountService.findTokenByUserId(userid);
        String serverSign = MD5Util.md5(linkString + serverToken);
        if (logger.isDebugEnabled()) {
            logger.debug("服务端计算出的签名是：" + serverSign);
        }
        return clientSign.equals(serverSign);
    }


    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        super.redirectToLogin(request, response);
    }
}
