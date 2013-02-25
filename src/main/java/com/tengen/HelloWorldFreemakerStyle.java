package com.tengen;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


public class HelloWorldFreemakerStyle {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreemakerStyle.class, "/");

        try {
            Template template = configuration.getTemplate("hello.ftl");
            Writer writer = new StringWriter();

            Map<String, Object> templateMap = new HashMap<String, Object>();
            templateMap.put("name", "Freemarker");

            template.process(templateMap, writer);

            System.out.println(writer);

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
