package org.mybatis.generator.ext;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by zhengx on 15/12/16 下午10:43
 */
public class MybatisXmlMapperPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(
            IntrospectedTable introspectedTable) {

        // 用模板生成实际文件
        Properties props = new Properties();
        props.put("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        props.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        props.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        props.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        Velocity.init(props);
//        VelocityEngine ve = new VelocityEngine(props);
        // 配置引擎上下文对象
        VelocityContext ctx = new VelocityContext();
        ctx.put("tableName", introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        String baseRecordType = introspectedTable.getBaseRecordType();
        String dao = introspectedTable.getDAOInterfaceType();
        String daoInterfaceType = dao.replace("DODAO", "DAO");
        ctx.put("namespace", daoInterfaceType);

        int i = baseRecordType.lastIndexOf(".");
        String resultMap = baseRecordType.substring(i + 1, baseRecordType.length() - 2) + "ResultMap";
        ctx.put("resultMapId", resultMap);

        List<String> columnsName = new ArrayList<>();
        List<String> insertValues = new ArrayList<>();
        List<Tuple> whereClauseTests = new ArrayList<>();
        List<Tuple> allColumns = new ArrayList<>();
        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();

        List<String> ignoreProperties = new ArrayList<>();
        ignoreProperties.add("gmtCreate");
        ignoreProperties.add("gmtModified");
        ignoreProperties.add("rowStatus");
        ignoreProperties.add("rowVersion");

        for (IntrospectedColumn column : columns) {
            String columnName = MyBatis3FormattingUtilities.getEscapedColumnName(column);
            columnsName.add(columnName);

            String javaProperty = column.getJavaProperty();

            allColumns.add(new Tuple(columnName, javaProperty));

            String parameterClause = MybatisFormattingUtil
                    .getParameterClause(column);
            if (javaProperty.equals("gmtCreate") || javaProperty.equals("gmtModified")) {
                insertValues.add("now()");
            } else if (javaProperty.equals("rowStatus") || javaProperty.equals("rowVersion")) {
                insertValues.add("0");
            } else {

                insertValues.add(parameterClause);
            }
            if (!ignoreProperties.contains(javaProperty)) {
                String test = javaProperty + " != null";
                if (column.isStringColumn()) {
                    test += " and " + javaProperty + " != '' ";
                }
                String columnTest = columnName + " = " + parameterClause;
                whereClauseTests.add(new Tuple(test, columnTest));
            }
        }

        ctx.put("columnsName", columnsName);
        ctx.put("insertValues", insertValues);
        ctx.put("whereClauses", whereClauseTests);
        ctx.put("columns", allColumns);
        ctx.put("shortTypeName", introspectedTable.getTableConfiguration().getDomainObjectName());

        // 加载模板文件
        Template t = Velocity.getTemplate("mybatis-mapper-template.vm");
        String sqlTarget = introspectedTable.getContext()
                .getSqlMapGeneratorConfiguration().getTargetProject();
        String sqlPackage = introspectedTable.getContext()
                .getSqlMapGeneratorConfiguration().getTargetPackage().replaceAll("\\.", "/");

        String mapperDir = sqlTarget + File.separator
                + sqlPackage + File.separator;
        try {

//            File dummyMapper = new File(mapperDir + introspectedTable.getMyBatis3XmlMapperFileName());
//            Files.deleteIfExists(dummyMapper.toPath());
            File mapper = new File(mapperDir
                    + introspectedTable.getFullyQualifiedTableNameAtRuntime() + ".xml");
//            Files.deleteIfExists(mapper.toPath());

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(mapper), "UTF-8"
            ));
            t.merge(ctx, out);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static class Tuple {
        private String left;
        private String right;

        Tuple(String l, String r) {
            this.left = l;
            this.right = r;
        }

        public String getLeft() {
            return left;
        }

        public String getRight() {
            return right;
        }
    }


}
