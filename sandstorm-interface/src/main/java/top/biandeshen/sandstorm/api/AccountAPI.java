package top.biandeshen.sandstorm.api;

import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import top.biandeshen.sandstorm.config.SysConst;
import top.biandeshen.sandstorm.shiro.Constants;
import top.sandstorm.common.commons.MD5Util;
import top.sandstorm.common.rest.Result;
import top.sandstorm.common.rest.ResultGenerator;
import top.biandeshen.sandstorm.entity.Account;
import top.biandeshen.sandstorm.service.AccountService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import top.biandeshen.sandstorm.manager.TokenManager;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Created by fanjiangpan on 2018/02/27.
 */
@RestController
@RequestMapping("/rbac/account")
public class AccountAPI {
    @Resource
    private AccountService accountService;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping("/add")
    public Result add(Account account) {
        accountService.save(account);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        accountService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/update")
    public Result update(Account account) {
        accountService.update(account);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Account account = accountService.findById(id);
        return ResultGenerator.genSuccessResult(account);
    }

    @GetMapping
    @ApiOperation("分页展示账户信息")
    public Result list(Integer pageNumber, Integer pageSize) {
        PageInfo pageInfo = accountService.findAll(pageNumber, pageSize);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 功能描述: <br>
     * 〈参考RedisTokenManager.java类〉
     *
     * @return:
     * @since: 1.0.0
     * @Author:fanjiangpan
     * @Date: 2018/3/15 9:54
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "登录验证", /*httpMethod = "GET",*/ /*response = Result.class,*/ notes = "登录验证")
    public Result login(Account account) {
        if (null == account.getAccount() || null == account.getPassword()) {
            return ResultGenerator.genFailResult("请登录");
        } else {
            try {
                Map<String, Object> resultMap = new HashMap<>();
                //token
                String serverToken = check(account);
                //userid
                Integer userid = accountService.saveToken(account, serverToken);
                //在Redis中创建UserIDToken
                tokenManager.createToken(String.valueOf(userid), serverToken);
                resultMap.put("userid", userid);
                resultMap.put("token", serverToken);
                Constants.USER_ID_TMP = userid;
                return ResultGenerator.genSuccessResult(resultMap);
            } catch (AuthenticationException e) { // 失败
                return ResultGenerator.genFailResult("登录失败");
            }
        }
    }

    private String check(Account account) {
        // 明文密码
        final String password = account.getPassword();
        /*数据库中存储的是md5+盐加密后的密码，因此这里要把加密后的密码传入*/
        final String md5Password = MD5Util.md5(password, SysConst.SALT);
        AuthenticationToken token = new UsernamePasswordToken(account.getAccount(), md5Password);
        Subject currentSubject = SecurityUtils.getSubject();
        // 这句话触发框架去访问Realm的doGetAuthenticationInfo
        currentSubject.login(token);
        String serverToken = UUID.randomUUID().toString().replaceAll("-", "");
        return serverToken;
    }

    @RequestMapping("/unauthorized")
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Result unauthorized() {
        return ResultGenerator.genFailResult("权限不足");
    }

    /**
     * 功能描述: <br>
     * 〈退出登录，注销！〉
     *
     * @return:
     * @since: 1.0.0
     * @Author:fanjiangpan
     * @Date: 2018/3/15 11:23
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public Result logout(Account account) {
        //TODO 注销有问题
        Account account1 = accountService.findBy("account",account.getAccount());
        tokenManager.deleteToken(String.valueOf(account1.getId()));
        return ResultGenerator.genSuccessResult("注销成功！");
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public Result logout() {
        if (Constants.USER_ID_TMP == null) {
            return ResultGenerator.genFailResult("注销失败！");
        } else {
            tokenManager.deleteToken(String.valueOf(Constants.USER_ID_TMP));
            return ResultGenerator.genSuccessResult("注销成功！");
        }
    }

}
