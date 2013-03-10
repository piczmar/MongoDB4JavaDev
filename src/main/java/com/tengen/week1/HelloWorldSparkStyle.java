package com.tengen.week1;


import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Run this and go to URL: http://localhost:4567/ in your browser
 */
public class HelloWorldSparkStyle {
    public static void main(String[] args) {
        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello World from Spark!";
            }
        });
    }
}
