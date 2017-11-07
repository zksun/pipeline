package org.mybatis.generator.ext;


import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.AbstractXmlGenerator;

/**
 * Created by zhikunsun on 2017/11/7.
 */
public class DummyXmlGenerator extends AbstractXmlGenerator {
    @Override
    public Document getDocument() {
        XmlElement answer = new XmlElement("mapper");
        Document document = new Document();
        document.setRootElement(answer);
        return document;
    }
}
