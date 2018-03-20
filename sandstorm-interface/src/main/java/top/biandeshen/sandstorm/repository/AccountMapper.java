package top.biandeshen.sandstorm.repository;

import top.biandeshen.sandstorm.base.Mapper;
import top.biandeshen.sandstorm.entity.Account;
import top.biandeshen.sandstorm.entity.Menu;

import java.util.Set;

public interface AccountMapper extends Mapper<Account> {
    Set<Menu> selectPermissionsById(Integer userId);
}