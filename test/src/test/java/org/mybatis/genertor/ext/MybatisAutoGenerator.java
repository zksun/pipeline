package org.mybatis.genertor.ext;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhikunsun on 2017/11/7.
 */
public class MybatisAutoGenerator {

    @Test
    public void testGen() throws IOException, XMLParserException,
            InvalidConfigurationException, SQLException,
            InterruptedException {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        ConfigurationParser configurationParser = new ConfigurationParser(warnings);
        Configuration configuration = configurationParser.parseConfiguration(
                this.getClass().getClassLoader()
                        .getResourceAsStream(""));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
