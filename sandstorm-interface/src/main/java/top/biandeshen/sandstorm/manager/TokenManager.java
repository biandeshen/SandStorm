/**
 * FileName: TokenManager
 * Author:   fanjiangpan
 * Date:     2018/3/15 9:10
 * Description: 对token进行操作的接口
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/15           版本号
 */
package top.sandstorm.org.shiro; /**
 * FileName: TokenManager
 * Author:   fanjiangpan
 * Date:     2018/3/15 9:10
 * Description: 对token进行操作的接口
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/15           版本号 
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
     * @param authentication 加密后的字符串
     * @return
     */
    UserIDToken getToken(String authentication);

    /**
     * 清除token
     * @param userId 登录用户的id
     */
    void deleteToken(String userId);

}
