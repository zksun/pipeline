package org.mybatis.genertor.ext;

import org.mybatis.generator.api.IntrospectedColumn;

/**
 * Created by zhikunsun on 2017/11/7.
 */
public class MybatisFormattingUtil {

    public static String getParameterClause(IntrospectedColumn introspectedColumn) {
        return getParameterClause(introspectedColumn, null);
    }

    public static String getParameterClause(IntrospectedColumn introspectedColumn, String prefix) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#{");
        stringBuilder.append(introspectedColumn.getJavaProperty(prefix));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
