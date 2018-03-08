/**
 * FileName: TimeUtils
 * Author:   fanjiangpan
 * Date:     2018/3/8 10:04
 * Description: 时间转换的工具类
 * History:
 * <author>          <time>          <version>
 * fanjiangpan           2018/3/8           版本号
 */
package top.sandstorm.common.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈时间转换的工具类〉
 *
 * @author fanjiangpan
 * @since 1.0.0
 */
public class TimeUtils {
    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static void main(String args[]){
        String time = stampToDate("1504096296000");
        System.out.println(time);
    }
}
