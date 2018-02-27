package top.biandeshen.sandstorm.service;

import top.biandeshen.sandstorm.repository.UserMapper;
import top.biandeshen.sandstorm.entity.User;
import top.biandeshen.sandstorm.base.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by fanjiangpan on 2018/02/26.
 */
@Service
@Transactional(readOnly=true)
public class UserService extends AbstractService<User> {
    // 因为泛型注入，所以这里不必声明mapper的依赖
}
