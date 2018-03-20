package top.biandeshen.sandstorm.api;

import top.sandstorm.common.rest.Result;
import top.sandstorm.common.rest.ResultGenerator;
import top.biandeshen.sandstorm.entity.UserProfile;
import top.biandeshen.sandstorm.service.UserProfileService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by fanjiangpan on 2018/03/19.
*/
@RestController
@RequestMapping("/rbac/user/profile")
public class UserProfileAPI {
    @Resource
    private UserProfileService userProfileService;

    @PostMapping("/add")
    public Result add(UserProfile userProfile) {
userProfileService.save(userProfile);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
userProfileService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/update")
    public Result update(UserProfile userProfile) {
userProfileService.update(userProfile);
        return ResultGenerator.genSuccessResult();
    }
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
UserProfile userProfile = userProfileService.findById(id);
        return ResultGenerator.genSuccessResult(userProfile);
    }

    @GetMapping
    public Result list(Integer pageNumber, Integer pageSize) {
        PageInfo pageInfo = userProfileService.findAll(pageNumber,pageSize);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
