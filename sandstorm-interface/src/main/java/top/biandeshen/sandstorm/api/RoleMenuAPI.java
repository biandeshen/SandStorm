package top.biandeshen.sandstorm.api;

import top.sandstorm.common.rest.Result;
import top.sandstorm.common.rest.ResultGenerator;
import top.biandeshen.sandstorm.entity.RoleMenu;
import top.biandeshen.sandstorm.service.RoleMenuService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by fanjiangpan on 2018/03/19.
*/
@RestController
@RequestMapping("/rbac/role/menu")
public class RoleMenuAPI {
    @Resource
    private RoleMenuService roleMenuService;

    @PostMapping("/add")
    public Result add(RoleMenu roleMenu) {
roleMenuService.save(roleMenu);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
roleMenuService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/update")
    public Result update(RoleMenu roleMenu) {
roleMenuService.update(roleMenu);
        return ResultGenerator.genSuccessResult();
    }
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
RoleMenu roleMenu = roleMenuService.findById(id);
        return ResultGenerator.genSuccessResult(roleMenu);
    }

    @GetMapping
    public Result list(Integer pageNumber, Integer pageSize) {
        PageInfo pageInfo = roleMenuService.findAll(pageNumber,pageSize);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
