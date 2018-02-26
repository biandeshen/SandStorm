package top.biandeshen.sandstorm.api;

import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import top.biandeshen.sandstorm.entity.User;
import top.biandeshen.sandstorm.service.UserService;
import top.sandstorm.common.rest.Result;
import top.sandstorm.common.rest.ResultGenerator;

import javax.annotation.Resource;

//@EnableAutoConfiguration
@RestController
@RequestMapping("/demo/user")
public class UserAPI{
    @Resource
    private UserService userService;

    @PostMapping
    public Result add(User user) {
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(User user) {
        userService.update(user);
        return ResultGenerator.genSuccessResult();
    }
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @GetMapping
    public Result list(Integer pageNumber, Integer pageSize) {
//        PageHelper.startPage(pageNumber,pageSize);
        PageInfo pageInfo = userService.findAll(pageNumber,pageSize);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
