package com.generator.codegenerator;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.Controller;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.builder.Service;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyBatisPlusGenerator {

    /**
     * 项目根路径 需要指向后端项目根文件夹Service目录
     * 如果同时导入前后端代码，即Service的上一级目录，值为 System.getProperty("user.dir") + File.separator + "Service"
     * 如果是单独打开的后端项目使用 System.getProperty("user.dir")
     */
    private final static String projectPath = System.getProperty("user.dir");
    /**
     * 作者 开发人员姓名
     */
    private final static String author = "";
    /**
     * 模块名，需要是模块的文件夹名称<br>
     * ps:如果包路径和模块名不一致,比如系统管模块名为sym,但是包路径为com.bdsoft.xtgl
     * 此时修改 packageConfig 下面的 .moduleName("xtgl") 的值为xtgl即可
     */
    private final static boolean useConvert = false;
    private final static String modelName = "recruit";
    /**
     * 本次代码生成的表名
     */
    private final static String[] tableName = {"T_DATA_LIST"};

    //true时候，生成代码以modelName开头
    private final static boolean startWithModelName = true;
    // service是否以i开头
    private final static boolean startWithI = false;
    // 表前缀
    private final static String prefix = "t_";
    // 表后缀
    private final static String suffix = "";

    private final static String javaPath = "\\src\\test\\java";
    private final static String resourcePath = "\\src\\test\\resources\\mapper\\";

    private final static String jdbcUrl = "jdbc:sqlserver://CNDCAWCHIDBDV01:1433;databasename=HGRInfo_PRE";
    private final static String jdbcUser = "hgr_info";
    private final static String jdbcPwd = "hgrinfo@321";

    private final static String packageName = "cn.az";
    private final static String mapperName = "mapper";
    private final static String muduleName = "summary";


    public static void main(String[] args) {

        List<IFill> tableFillList = new ArrayList<>();
//        tableFillList.add(new Column("createTime", FieldFill.INSERT));
//        tableFillList.add(new Column("createUser", FieldFill.INSERT));
//        tableFillList.add(new Column("updateTime", FieldFill.INSERT_UPDATE));
//        tableFillList.add(new Column("updateUser", FieldFill.INSERT_UPDATE));

        FastAutoGenerator.create(jdbcUrl,
                        jdbcUser,
                        jdbcPwd)
                .globalConfig(builder -> {
                    // 设置作者
                    builder.author(author)
                            // 开启 swagger 模式
                            .enableSwagger()
                            .disableOpenDir()
                            // 指定输出目录
                            .outputDir(projectPath + File.separator + javaPath);
                })
                .packageConfig(builder -> {
                    // 设置父包名
                    builder.parent(packageName)
                            .mapper(mapperName)
                            // 设置父包模块名
                            .moduleName(muduleName)
                            // 设置mapperXml生成路径
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + File.separator + resourcePath + File.separator + "%s"));
                })
                .strategyConfig(builder -> {
                    StrategyConfig.Builder configBuild = builder.addInclude(tableName).addTablePrefix(prefix).addTableSuffix(suffix);
                    // 实体属性设置
                    Entity.Builder entityBuild = configBuild.entityBuilder().fileOverride().enableTableFieldAnnotation().enableRemoveIsPrefix().addTableFills(tableFillList);
                    Mapper.Builder mapperBuilder = configBuild.mapperBuilder().fileOverride();
                    Service.Builder serviceBuilder = configBuild.serviceBuilder();
                    Controller.Builder controllerBuilder = configBuild.controllerBuilder();
                    if (startWithModelName) { // 如果以模块为前缀，设置模块名
                        boolean upperCase = Character.isUpperCase(modelName.charAt(0));
                        String convertModelName = StrUtil.isNotBlank(modelName) ? modelName.substring(0, 1).toUpperCase() + modelName.substring(1) : "";
                        if(!useConvert){
                            convertModelName = "";
                        }
                        if (upperCase) {
                            System.out.println(convertModelName);
                        }
                        entityBuild.formatFileName(convertModelName + "%s").enableLombok();
                        mapperBuilder.formatMapperFileName(convertModelName + "%s" + ConstVal.MAPPER)
                                .formatXmlFileName(convertModelName + "%s" + ConstVal.MAPPER).enableBaseColumnList().enableBaseResultMap();
                        if (startWithI) { // "I" +
                            serviceBuilder.formatServiceFileName("I" + convertModelName + "%s" + ConstVal.SERVICE);
                        } else {
                            serviceBuilder.formatServiceFileName(convertModelName + "%s" + ConstVal.SERVICE);
                        }
                        serviceBuilder.formatServiceFileName(convertModelName + "%s" + ConstVal.SERVICE)
                                .formatServiceImplFileName(convertModelName + "%s" + ConstVal.SERVICE_IMPL);
                        controllerBuilder.formatFileName(convertModelName + "%s" + ConstVal.CONTROLLER).enableRestStyle();
                    } else {
                        if (!startWithI) { // 不适用i开头
                            serviceBuilder.formatServiceFileName("%s" + ConstVal.SERVICE);
                        }
                    }
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}