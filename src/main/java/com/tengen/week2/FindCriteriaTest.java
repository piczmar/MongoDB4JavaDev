package com.tengen.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * Shows how to find docs with criteria.
 */
public class FindCriteriaTest {
    public static void main(String[] args) throws UnknownHostException {

        MongoClient client = new MongoClient();
        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("insertTest");

        collection.drop();//clean collection

        for (int i = 0; i < 10; i++) {
            collection.insert(new BasicDBObject("x", new Random().nextInt(2))
                    .append("y", new Random().nextInt(100)));
        }
        System.out.println("Count:");
        System.out.println(collection.count());

        System.out.println("Find all:");
        DBCursor cur = collection.find();
        try {
            while (cur.hasNext()) {
                System.out.println(cur.next());
            }
        } finally {
            cur.close();
        }

    }
}
