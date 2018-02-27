package top.biandeshen.sandstorm.generator;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import top.sandstorm.common.commons.Exceptions;
import top.sandstorm.common.generator.CodeGenerator;

import java.io.IOException;
import java.util.Properties;

/**
 * 调用代码生成器生成项目相关的源码
 */
public class CodeGeneratorClient {
  CodeGenerator codeGenerator = new CodeGenerator("/generator.properties", "generatorConfig.xml" );
  private String propertiesPath = "/generator.properties";
  Resource resource = new ClassPathResource( propertiesPath );
  Properties props = new Properties();

  public void generateMapper() {
    codeGenerator.generateMapper();
  }

  public void generateServiceAndController() {
    try {
      props.load( resource.getInputStream() );
      String TEMPLATE_FILE_PATH = props.getProperty( "templete.file.path" );
      System.out.println(TEMPLATE_FILE_PATH);
      codeGenerator.genServiceAndController( TEMPLATE_FILE_PATH );
    } catch (IOException e) {
      throw Exceptions.unchecked( e );
    }
  }

  public static void main(String[] args) {
    final CodeGeneratorClient client = new CodeGeneratorClient();
    client.generateMapper();
    client.generateServiceAndController();
  }

}
