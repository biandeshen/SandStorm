/**
 * FileName: ForbidException
 * Author:   fanjiangpan
 * Date:     2018/3/20 10:43
 * Description: 禁止当前用户登录，如用户可用状态为0
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/20           版本号
 */
package top.sandstorm.common.rest;

/**
 * 〈禁止当前用户登录，如用户可用状态为0〉
 *
 * @author fanjiangpan
 * @since 1.0.0
 */
public class ForbidException extends RuntimeException {
    public ForbidException() {
    }

    public ForbidException(String message) {
        super(message);
    }

    public ForbidException(String message, Throwable cause) {
        super(message, cause);
    }
}
