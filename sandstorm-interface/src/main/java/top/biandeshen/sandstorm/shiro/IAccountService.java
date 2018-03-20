package top.biandeshen.sandstorm.shiro;

import top.biandeshen.sandstorm.entity.Menu;

import java.util.Set;

/**
 * 定义验证和鉴权所依赖的服务
 * @author web2017
 */
public interface IAccountService {
  /**
   * 根据用户id获得token
   * @param userid
   * @return
   */
  String findTokenByUserId(String userid);

  Set<Menu> findPermissionsById(String userId);

  String findPasswd(String account);
}
