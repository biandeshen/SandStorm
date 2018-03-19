/**
 * FileName: TokenManager
 * Author:   fanjiangpan
 * Date:     2018/3/15 9:10
 * Description: 对token进行操作的接口
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/15           版本号
 */
package top.biandeshen.sandstorm.manager; /**
 * FileName: TokenManager
 * Author:   fanjiangpan
 * Date:     2018/3/15 9:10
 * Description: 对token进行操作的接口
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/15           版本号 
 */

import top.biandeshen.sandstorm.shiro.UserIDToken;


/**
 * 〈对token进行操作的接口〉
 *
 * @author fanjiangpan
 * @since 1.0.0
 */

public interface TokenManager {

    /**
     * 创建一个token关联上指定用户
     * @param userId 指定用户的id
     * @param token 指定用户的token
     * @return 生成的token
     */
    UserIDToken createToken(String userId, String token);

    /**
     * 检查token是否有效
     * @param model token
     * @return 是否有效
     */
    boolean checkToken(UserIDToken model);

    /**
     * 从字符串中解析token
     * @param userid userid
     * @return
     */
    UserIDToken getToken(String userid);

    /**
     * 清除token
     * @param userId 登录用户的id
     */
    void deleteToken(String userId);

}
