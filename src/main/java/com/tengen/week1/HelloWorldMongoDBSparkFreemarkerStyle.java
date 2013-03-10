package com.tengen.week1;


import com.mongodb.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.io.Writer;
import java.net.UnknownHostException;

public class HelloWorldMongoDBSparkFreemarkerStyle {
    public static void main(String[] args) throws UnknownHostException {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreemakerStyle.class, "/");

        // create logical connection to Mongo cluster
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
        // create database
        DB db = client.getDB("course");
        // get db collection (like a table in RDBMS)
        final DBCollection collection = db.getCollection("hello");


        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                Writer writer = new StringWriter();
                try {
                    Template template = configuration.getTemplate("hello.ftl");

                    DBObject document = collection.findOne();

                    template.process(document, writer);

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
