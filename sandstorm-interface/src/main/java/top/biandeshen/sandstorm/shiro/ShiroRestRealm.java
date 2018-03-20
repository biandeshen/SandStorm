/**
 * FileName: ShiroRestRealm
 * Author:   fanjiangpan
 * Date:     2018/3/8 16:44
 * Description: Shiro 的 realm，定义规则
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/8           版本号
 */
package top.biandeshen.sandstorm.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import top.biandeshen.sandstorm.entity.Menu;

import java.util.HashSet;
import java.util.Set;

/**
 * 〈Shiro 的 realm，定义规则〉
 *
 * @author fanjiangpan
 * @since 1.0.0
 */
public class ShiroRestRealm extends AuthorizingRealm {
    private final IAccountService accountService;

    public ShiroRestRealm(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean supports(AuthenticationToken token) { //判断此Realm是否支持此Token
        //仅支持UsernamePasswordToken类型和UserIDToken类型的Token
        return token instanceof UsernamePasswordToken || token instanceof UserIDToken;
    }

    /**
     * 鉴权，无需比对
     * 只需根据用户标识，返回其拥有的资源权限组合，比对的逻辑将由shiro框架完成。
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        //userid
        //获取principal对象，一般都是子类在执行授权操作赋予的
        // http://www.codes51.com/itwd/1210257.html
        String userId = String.valueOf(super.getAvailablePrincipal(principals));
        //userid 可访问的所有url的集合
        Set<Menu> permissions = accountService.findPermissionsById(userId);
        Set<String> permissionsplus = new HashSet<>(permissions.size());
        for (Menu menu:permissions) {
            permissionsplus.add(menu.getUrl());
        }
        //添加url集合至AuthorInfo
        simpleAuthorInfo.addStringPermissions(permissionsplus);
        return simpleAuthorInfo;
    }

    /**
     * 验证身份信息，但是这里不负责比对的逻辑，我们只需根据principle返回AuthenticationInfo（携带了用户名和密码）
     * 比对的逻辑将由shiro框架完成
     * <p>
     * 因为无状态，我们每次都要验证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SimpleAuthenticationInfo authInfo = null;
        if (token instanceof UsernamePasswordToken) {
            UsernamePasswordToken upToken = (UsernamePasswordToken) token;
            String account = upToken.getUsername();
            final String passwd = accountService.findPasswd(account);
            authInfo = new SimpleAuthenticationInfo(account, passwd, getName());
        } else if (token instanceof UserIDToken) {
            UserIDToken userIDToken = (UserIDToken) token;
            authInfo = new SimpleAuthenticationInfo(userIDToken.getUserId(), userIDToken.getUserId(), getName());
        }
        return authInfo;
    }
}