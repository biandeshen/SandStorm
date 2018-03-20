package top.biandeshen.sandstorm.service;

import top.biandeshen.sandstorm.repository.MenuMapper;
import top.biandeshen.sandstorm.entity.Menu;
import top.biandeshen.sandstorm.base.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by fanjiangpan on 2018/02/27.
 */
@Service
@Transactional(readOnly = true)
public class MenuService extends AbstractService<Menu> {
    // 因为泛型注入，所以这里不必声明mapper的依赖

    /**
     * 得出指定ID的子节点（一层）
     *
     * @return
     */
    public List<Menu> findByPid(Integer pid) {
        return mapper.selectByPid(pid);
    }
}
