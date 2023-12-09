import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.HashMap;

/**
 * 项目多模块代码生成器
 * <p>
 * <a href="https://baomidou.com/pages/981406/#%E6%95%B0%E6%8D%AE%E5%BA%93%E9%85%8D%E7%BD%AE-datasourceconfig">...</a>
 *
 * @author wang xiang
 */
public class MultiModuleGenerator {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/xxx?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String projectRootPath = System.getProperty("user.dir");
    private static final String parentPackageName = "cn.haidnor.project";

    // 配置项: 业务包名
    private static final String servicePackage = "test";
    // 配置项: 数据库表,多个表名使用逗号分割
    private static final String tables = "test_table";

    public static void main(String[] args) {
        multiModuleGenerator();
        System.out.println("自动覆盖数据库实体类文件不会保留已经手动添加的注解, 请充分检查后再进行自动生成覆盖 !");
    }

    /**
     * 多模块代码生成器
     *
     * @author wang xiang
     */
    protected static void multiModuleGenerator() {
        String entityPath = projectRootPath + "/model/src/main/java/com/zhongwei/wish/entity/" + servicePackage;                // 数据库实体类
        String mapperPath = projectRootPath + "/core/src/main/java/com/zhongwei/wish/mapper/" + servicePackage;                 // mapper
        String mapperXmlPath = projectRootPath + "/core/src/main/resources/mapper/" + servicePackage;                           // mapper.xml
        String servicePath = projectRootPath + "/core/src/main/java/com/zhongwei/wish/service/" + servicePackage;               // service interface
        String serviceImplPath = projectRootPath + "/core/src/main/java/com/zhongwei/wish/service/" + servicePackage + "/impl"; // service implement

        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> builder
                        .author("wang xiang")   // 作者名称
                        .dateType(DateType.ONLY_DATE)
                        .disableOpenDir()                       // 禁止打开输出目录
                )
                .packageConfig((scanner, builder) -> builder
                        .parent(parentPackageName)
                        .entity("entity." + servicePackage)
                        .mapper("mapper." + servicePackage)
                        .service("service." + servicePackage)
                        .serviceImpl("service." + servicePackage + ".impl")
                        .pathInfo(
                                new HashMap<OutputFile, String>() {{
                                    put(OutputFile.entity, entityPath);
                                    put(OutputFile.mapper, mapperPath);
                                    put(OutputFile.xml, mapperXmlPath);
                                    put(OutputFile.service, servicePath);
                                    put(OutputFile.serviceImpl, serviceImplPath);
                                }}
                        )
                )
                .strategyConfig(builder -> builder.addInclude(tables)
                                .entityBuilder()
                                .fileOverride()                                                                // 覆盖实体类文件
                                .enableLombok()                                                                // 开启 lombok 模型 会在实体类前添加 [@Getter] 和 [@Setter] 注解。（这是Lombok的注解，需要添加Lombok依赖）
//                        .addTableFills(new Column("delete_flag", FieldFill.INSERT))        // 在实体类的该字段上追加注解[@TableField(value = "create_time", fill = FieldFill.INSERT)]
//                        .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))  //在实体类的该字段上追加注解[@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)]
                                // Mapper策略配置
                                .mapperBuilder()
                                .enableMapperAnnotation() // 会在mapper接口上添加注解[@Mapper]
                                .build()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .templateConfig(builder -> builder.controller("")) // 不生成 controller
                .execute();
    }


}

