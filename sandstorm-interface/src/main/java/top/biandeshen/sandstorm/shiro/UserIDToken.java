/**
 * FileName: UserIDToken
 * Author:   fanjiangpan
 * Date:     2018/3/8 16:57
 * Description:
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/8           版本号
 */
package top.biandeshen.sandstorm.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 〈Token的Model类，可以增加字段提高安全性，例如时间戳、url签名>
 *
 * @author fanjiangpan
 * @since 1.0.0
 */
public class UserIDToken implements AuthenticationToken{

    /**用户id
     *
     */
    private String userId;

    /**随机生成的uuid
     *
     */
    private String token;

    public UserIDToken(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public UserIDToken(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return userId;
    }
}
