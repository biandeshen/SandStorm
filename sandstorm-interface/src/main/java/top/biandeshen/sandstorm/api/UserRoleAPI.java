package top.biandeshen.sandstorm.api;

import top.sandstorm.common.rest.Result;
import top.sandstorm.common.rest.ResultGenerator;
import top.biandeshen.sandstorm.entity.UserRole;
import top.biandeshen.sandstorm.service.UserRoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by fanjiangpan on 2018/03/19.
*/
@RestController
@RequestMapping("/rbac/user/role")
public class UserRoleAPI {
    @Resource
    private UserRoleService userRoleService;

    @PostMapping("/add")
    public Result add(UserRole userRole) {
userRoleService.save(userRole);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
userRoleService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/update")
    public Result update(UserRole userRole) {
userRoleService.update(userRole);
        return ResultGenerator.genSuccessResult();
    }
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
UserRole userRole = userRoleService.findById(id);
        return ResultGenerator.genSuccessResult(userRole);
    }

    @GetMapping
    public Result list(Integer pageNumber, Integer pageSize) {
        PageInfo pageInfo = userRoleService.findAll(pageNumber,pageSize);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
