package top.biandeshen.sandstorm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.biandeshen.sandstorm.base.AbstractService;
import top.biandeshen.sandstorm.entity.User;


@Service
@Transactional(readOnly = true)
public class UserService extends AbstractService<User> {
}
