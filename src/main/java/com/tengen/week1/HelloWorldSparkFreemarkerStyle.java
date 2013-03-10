package com.tengen.week1;


import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class HelloWorldSparkFreemarkerStyle {
    public static void main(String[] args) {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreemakerStyle.class, "/");

        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                Writer writer = new StringWriter();
                try {
                    Template template = configuration.getTemplate("hello.ftl");

                    Map<String, Object> templateMap = new HashMap<String, Object>();
                    templateMap.put("name", "Freemarker");

                    template.process(templateMap, writer);

                    System.out.println(writer);

                } catch (Exception e) {
                    halt(500); // return error code 500
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                return writer;

            }
        });
    }
}
