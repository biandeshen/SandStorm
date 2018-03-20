package top.biandeshen.sandstorm.service;

import tk.mybatis.mapper.entity.Condition;
import top.biandeshen.sandstorm.entity.Menu;
import top.biandeshen.sandstorm.repository.AccountMapper;
import top.biandeshen.sandstorm.entity.Account;
import top.biandeshen.sandstorm.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.biandeshen.sandstorm.shiro.IAccountService;

import java.util.Set;


/**
 * Created by fanjiangpan on 2018/02/27.
 */
@Service("accountService")
@Transactional(readOnly=true)
public class AccountService extends AbstractService<Account> implements IAccountService {
    // 因为泛型注入，所以这里不必声明mapper的依赖
    @Override
    public String findTokenByUserId(String userid) {
        return mapper.selectByPrimaryKey(Integer.parseInt(userid.trim())).getToken();
    }

    @Override
    public Set<Menu> findPermissionsById(String userId) {
        return ((AccountMapper)mapper).selectPermissionsById(Integer.parseInt(userId.trim()));
    }

    @Override
    public String findPasswd(String account) {
        Condition condition = new Condition(Account.class);
        condition.createCriteria().andEqualTo("account", account);
        return mapper.selectByCondition(condition).get(0).getPassword();
    }

    @Transactional(readOnly = false)
    public Integer saveToken(Account account, String serverToken) {
        Condition condition = new Condition(Account.class);
        condition.createCriteria().andEqualTo("account", account.getAccount());
        Account account1 = mapper.selectByCondition(condition).get(0);
        account1.setToken(serverToken);
        mapper.updateByPrimaryKey(account1);
        return account1.getId();
    }
}
