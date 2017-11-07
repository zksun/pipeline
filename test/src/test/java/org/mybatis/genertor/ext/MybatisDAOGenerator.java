package org.mybatis.genertor.ext;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.messages.Messages.getString;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Created by zhikunsun on 2017/11/7.
 */
public class MybatisDAOGenerator extends AbstractJavaClientGenerator {


    public MybatisDAOGenerator() {
        super(true);
    }


    public MybatisDAOGenerator(boolean requiresXMLGenerator) {
        super(requiresXMLGenerator);
    }


    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return new DummyXmlGenerator();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        progressCallback.startTask(getString("Progress.17", introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        String baseRecordType = introspectedTable.getBaseRecordType();
        String doSimpleName = baseRecordType.substring(0, baseRecordType.length() - 2);
        String dao = introspectedTable.getDAOInterfaceType();
        String doInterfaceType = dao.replace("DODAO", "DAO");
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(doInterfaceType);
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);

        String rootInterface = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface)) {
            rootInterface = context.getJavaClientGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterface)) {
            rootInterface = rootInterface + "<" + doSimpleName + "DO>";
            FullyQualifiedJavaType fullyQualifiedJavaType = new FullyQualifiedJavaType(rootInterface);
            interfaze.addSuperInterface(fullyQualifiedJavaType);
            interfaze.addImportedType(fullyQualifiedJavaType);
        }

        List<CompilationUnit> answer = new ArrayList<>();
        if (context.getPlugins().clientGenerated(interfaze, null, introspectedTable)) {
            answer.add(interfaze);
        }

        return answer;
    }
}
