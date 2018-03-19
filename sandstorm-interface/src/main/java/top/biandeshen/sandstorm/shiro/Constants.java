/**
 * FileName: Constants
 * Author:   fanjiangpan
 * Date:     2018/3/15 9:41
 * Description: 常量
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/15           版本号
 */
package top.biandeshen.sandstorm.shiro;

/**
 * 〈常量〉
 *
 * @author fanjiangpan
 * @since 1.0.0
 */
public class Constants {

    /**
     * 功能描述: <br>
     * 〈存放登录时用户ID〉
     *

     * @return:
     * @since: 1.0.0
     * @Author:fanjiangpan
     * @Date: 2018/3/15 16:37
     */
     public static Integer USER_ID_TMP = null;

    /**
     * 存储当前登录用户id的字段名
     */
    public static  String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 2;

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";

    public static String SERVER_TOKEN = null;
}