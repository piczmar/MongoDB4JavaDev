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
            for (int retries = 0; retries <= 5; retries++) {
                try {
                    collection.insert(new BasicDBObject("_id", i));
                    System.out.println("Inserted doc " + i);
                    break; // do not retry if inserted successfully
                } catch(MongoException.DuplicateKey exc){
                    System.out.println("Document already exists: " + i);
                }catch (MongoException exc) {
                    System.out.println(exc.getMessage());
                    System.out.println("Retrying...");
                    Thread.sleep(5000);
                }
            }
            Thread.sleep(500);
        }

    }
}
