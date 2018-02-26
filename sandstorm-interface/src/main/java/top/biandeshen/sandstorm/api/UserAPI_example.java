package top.biandeshen.sandstorm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import top.biandeshen.sandstorm.entity.User;
import top.sandstorm.common.rest.Result;
import top.sandstorm.common.rest.ResultGenerator;
import top.biandeshen.sandstorm.service.UserService;

@EnableAutoConfiguration
@RestController
@RequestMapping("/demo")
public class UserAPI_example {
    @Autowired
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

//    @GetMapping
//    public Result list(Integer pageNumber, Integer pageSize) {
//        PageInfo pageInfo = userService.findAll(pageNumber,pageSize);
//        return ResultGenerator.genSuccessResult(pageInfo);
//    }
}
