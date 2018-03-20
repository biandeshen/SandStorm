package top.biandeshen.sandstorm.service;

import top.biandeshen.sandstorm.repository.ColumnInfoMapper;
import top.biandeshen.sandstorm.entity.ColumnInfo;
import top.biandeshen.sandstorm.base.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by fanjiangpan on 2018/03/19.
 */
@Service
@Transactional(readOnly=true)
public class ColumnInfoService extends AbstractService<ColumnInfo> {
    // 因为泛型注入，所以这里不必声明mapper的依赖
}
