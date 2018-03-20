package top.biandeshen.sandstorm.api;

import top.sandstorm.common.rest.Result;
import top.sandstorm.common.rest.ResultGenerator;
import top.biandeshen.sandstorm.entity.ColumnInfo;
import top.biandeshen.sandstorm.service.ColumnInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by fanjiangpan on 2018/03/19.
*/
@RestController
@RequestMapping("/cms/column/info")
public class ColumnInfoAPI {
    @Resource
    private ColumnInfoService columnInfoService;

    @PostMapping("/add")
    public Result add(ColumnInfo columnInfo) {
columnInfoService.save(columnInfo);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
columnInfoService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/update")
    public Result update(ColumnInfo columnInfo) {
columnInfoService.update(columnInfo);
        return ResultGenerator.genSuccessResult();
    }
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
ColumnInfo columnInfo = columnInfoService.findById(id);
        return ResultGenerator.genSuccessResult(columnInfo);
    }

    @GetMapping
    public Result list(Integer pageNumber, Integer pageSize) {
        PageInfo pageInfo = columnInfoService.findAll(pageNumber,pageSize);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
