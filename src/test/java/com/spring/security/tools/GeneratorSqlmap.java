package com.spring.security.tools;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description Mybatis逆向工程（使用mybatis-generator-core-1.3.2.jar）
 * @Author xy
 * @Date 2020/7/3 11:37
 * @Version 1.0
 * @Since JDK 1.8
 */
public class GeneratorSqlmap {
    public void generator() throws Exception{

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指定 逆向工程配置文件
//        String path = new File("").getCanonicalPath(); // D:/gzhc/workspace/
//        String path = this.getClass().getResource("/").getPath(); // D:/gzhc/workspace/Instance/target/test-classes/
        String path = System.getProperty("user.dir"); // D:\gzhc\workspace
        System.out.println(path);
        File configFile = new File(path + "/Instance/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);

    }
    public static void main(String[] args) throws Exception {
        try {
            GeneratorSqlmap generatorSqlmap = new GeneratorSqlmap();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}
