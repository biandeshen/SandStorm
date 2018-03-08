package top.sandstorm.common.commons;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.sandstorm.common.rest.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Responses {
  private static final Logger logger = LoggerFactory.getLogger( Responses.class );

  /**
   * result转json后用response写出
   * @param response
   * @param result
   */
  public static void sendResult(HttpServletResponse response, Result result) {
    response.setCharacterEncoding( "UTF-8" );
    response.setHeader( "Content-type", "application/json;charset=UTF-8" );
    response.setStatus( 200 );
    try {
      response.getWriter().write( JSON.toJSONString( result ) );
    } catch (IOException ex) {
      logger.error( ex.getMessage() );
    }
  }
}
