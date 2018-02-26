###使用说明
1. ##### 打开src/main/resources下的application.properties,设置你需要的数据库
2. ##### 修改generator.properties中的信息，与你的项目保持一致
3. ##### 修改src/test/resources下的generatorConfig.xml，与2匹配
4. ##### 添加Maven插件，设置命令为mybatis-generator:generate -e，即可生成generator插件，运行可自动根据所给数据库生成entity，repository，以及在resource中生成对应的mapper（含SQL语句） 相关链接：http://blog.csdn.net/pucao_cug/article/details/64499355
5. ##### 自行在service中添加对应服务的类，若继承base包中的AbstractService，即可使用对应的方法
6. ##### 由于通过插件自动生成的repository中含有抽象方法，也可自行实现对应repository中的抽象方法，在service包中设置自动注入并使用
7. ##### 由于生成的repository类中会有CLOB等造成错误，可以自行删除，没有影响
8. ##### 参考example实体类设置主键和类的注解
9. ##### 在config包中，修改其中的包名，转换成自己API包（也就是Controller包）的路径
10. ##### 启动与包同级的Application类中的main方法，即可成功启动服务（Tomcat）
11. ##### 在浏览器中打开http://localhost:8080/swagger-ui.html(端口号可改，位于Application中)，即可测试对应接口

#### ps.已提供SQL，在Test中获取User表的SQL语句