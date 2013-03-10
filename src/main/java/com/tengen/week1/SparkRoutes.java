package com.tengen.week1;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Try it in web browser using following URLs:
 * http://localhost:4567/test/cat
 * http://localhost:4567/test
 * http://localhost:4567/
 */
public class SparkRoutes {
    public static void main(String[] args) {


        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreemakerStyle.class, "/");


        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                Writer writer = new StringWriter();
                try {
                    Template template = configuration.getTemplate("fruitPicker.ftl");

                    Map<String, Object> templateMap = new HashMap<String, Object>();
                    templateMap.put("fruits", Arrays.asList("orange", "apple", "banana", "peach"));

                    template.process(templateMap, writer);

                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                return writer;
            }
        });

        Spark.post(new Route("/favorite_fruit") {
            @Override
            public Object handle(Request request, Response response) {
                String fruit = request.queryParams("fruit");
                if (fruit != null) {
                    return "you choose " + fruit;
                }
                return "Why don't you choose any?";
            }
        });

        Spark.get(new Route("/test") {
            @Override
            public Object handle(Request request, Response response) {
                return "I'm in TEST now.";
            }
        });

        Spark.get(new Route("/test/:param") {
            @Override
            public Object handle(Request request, Response response) {
                return request.params(":param");
            }
        });
    }
}
