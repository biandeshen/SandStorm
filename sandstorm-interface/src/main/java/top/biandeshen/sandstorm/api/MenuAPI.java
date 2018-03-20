package top.biandeshen.sandstorm.api;

import top.sandstorm.common.rest.Result;
import top.sandstorm.common.rest.ResultGenerator;
import top.biandeshen.sandstorm.entity.Menu;
import top.biandeshen.sandstorm.service.MenuService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by fanjiangpan on 2018/03/19.
*/
@RestController
@RequestMapping("/rbac/menu")
public class MenuAPI {
    @Resource
    private MenuService menuService;

    @PostMapping("/add")
    public Result add(Menu menu) {
menuService.save(menu);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
menuService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/update")
    public Result update(Menu menu) {
menuService.update(menu);
        return ResultGenerator.genSuccessResult();
    }
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
Menu menu = menuService.findById(id);
        return ResultGenerator.genSuccessResult(menu);
    }

    @GetMapping
    public Result list(Integer pageNumber, Integer pageSize) {
        PageInfo pageInfo = menuService.findAll(pageNumber,pageSize);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
