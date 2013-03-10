package com.tengen.week1;


import com.mongodb.*;

import java.net.UnknownHostException;

public class HelloWorldMongoDBStyle {
    public static void main(String[] args) throws UnknownHostException {
        // create logical connection to Mongo cluster
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

        // create database
        DB db = client.getDB("course");

        // get db collection (like a table in RDBMS)
        DBCollection collection = db.getCollection("hello");

        DBObject document = collection.findOne();

        System.out.println(document);
    }
}
