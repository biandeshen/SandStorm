/**
 * FileName: RedisTokenManager
 * Author:   fanjiangpan
 * Date:     2018/3/15 9:13
 * Description:
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/15           版本号
 */
package top.biandeshen.sandstorm.manager.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import top.biandeshen.sandstorm.shiro.Constants;
import top.biandeshen.sandstorm.shiro.UserIDToken;
import top.biandeshen.sandstorm.manager.TokenManager;


/**
 * 〈通过Redis存储和验证token的实现类〉
 *
 * @author fanjiangpan
 * @since 1.0.0
 */
@Component
public class RedisTokenManager implements TokenManager {

    private RedisTemplate<String, String> redis;

    @Autowired
    public void setRedis(StringRedisTemplate redis) {
        this.redis = redis;
        //泛型设置成Long后必须更改对应的序列化方案
        redis.setKeySerializer(new JdkSerializationRedisSerializer());
    }


    /**
     * 功能描述: <br>
     * 〈〉
     *
     * @return:
     * @since: 1.0.0
     * @Author:fanjiangpan
     * @Date: 2018/3/15 9:53
     */
    @Override
    public UserIDToken createToken(String userId, String token) {
        //使用uuid作为源token
        UserIDToken model = new UserIDToken(userId, token);
        //存储到redis并设置过期时间
        redis.boundValueOps(userId).set(token, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    @Override
    public UserIDToken getToken(String userId) {
//        if (authentication == null || authentication.length() == 0) {
//            return null;
//        }
//        String[] param = authentication.split("_");
//        if (param.length != 2) {
//            return null;
//        }
//        //使用userId和源token简单拼接成的token，可以增加加密措施
//        String userId = param[0];
//        String token = param[1];
        String token = redis.boundValueOps(userId).get();
        return new UserIDToken(userId, token);
    }


    @Override
    public boolean checkToken(UserIDToken model) {
        if (model == null) {
            return false;
        }
        String token = redis.boundValueOps(model.getUserId()).get();
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redis.boundValueOps(model.getUserId()).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public void deleteToken(String userId) {
        redis.delete(userId);
    }
}

