package com.tengen.week6;


import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;

public class ReplicaSetTest {
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        /**
         * Pass a list of servers, because we never know which will be PRIMARY on failover
         */
        MongoClient client = new MongoClient(Arrays.asList(
                new ServerAddress("localhost", 27017),
                new ServerAddress("localhost", 27018),
                new ServerAddress("localhost", 27019)
        ));
        DBCollection collection = client.getDB("course").getCollection("replica.test");

        collection.drop();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
                collection.insert(new BasicDBObject("_id", i));
                System.out.println("Inserted doc " + i);
            } catch (MongoException exc) {
                System.out.println(exc.getMessage());
            }
            Thread.sleep(500);
        }

    }
}
