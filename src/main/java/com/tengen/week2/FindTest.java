package com.tengen.week2;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Shows how to find docs.
 */
public class FindTest {
    public static void main(String[] args) throws UnknownHostException {

        MongoClient client = new MongoClient();
        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("insertTest");

        collection.drop();//clean collection

        for (int i = 0; i < 10; i++) {
            collection.insert(new BasicDBObject("x", i));
        }

        System.out.println("Find one:");

        DBObject one = collection.findOne();
        System.out.println(one);

        System.out.println("Find all:");
        DBCursor cur = collection.find();
        try {
            while (cur.hasNext()) {
                System.out.println(cur.next());
            }
        } finally {
            cur.close();
        }
        System.out.println("Count:");
        System.out.println(collection.count());
    }
}
