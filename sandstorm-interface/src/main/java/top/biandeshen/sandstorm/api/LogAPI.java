package top.biandeshen.sandstorm.api;

import top.sandstorm.common.rest.Result;
import top.sandstorm.common.rest.ResultGenerator;
import top.biandeshen.sandstorm.entity.Log;
import top.biandeshen.sandstorm.service.LogService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by fanjiangpan on 2018/03/19.
*/
@RestController
@RequestMapping("/rbac/log")
public class LogAPI {
    @Resource
    private LogService logService;

    @PostMapping("/add")
    public Result add(Log log) {
logService.save(log);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
logService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/update")
    public Result update(Log log) {
logService.update(log);
        return ResultGenerator.genSuccessResult();
    }
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
Log log = logService.findById(id);
        return ResultGenerator.genSuccessResult(log);
    }

    @GetMapping
    public Result list(Integer pageNumber, Integer pageSize) {
        PageInfo pageInfo = logService.findAll(pageNumber,pageSize);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
