package com.tengen.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * Shows how to find docs.
 */
public class SortSkipLimitTest {
    public static void main(String[] args) throws UnknownHostException {

        MongoClient client = new MongoClient();
        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("SortSkipLimitTest");

        collection.drop();//clean collection

        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            collection.insert(new BasicDBObject("_id", i)
                    .append("start", new BasicDBObject()
                            .append("x", rand.nextInt(90) * 10)
                            .append("y", rand.nextInt(90) * 10))
                    .append("end", new BasicDBObject()
                            .append("x", rand.nextInt(90) * 10)
                            .append("y", rand.nextInt(90) * 10)));
        }

        System.out.println("Count:");
        System.out.println(collection.count());

        System.out.println("Find all:");
        DBCursor cur = collection.find()
                .sort(new BasicDBObject("start.y", -1)// sort descending
                                .append("start.x", 1)) // sort ascending
                .skip(2) // skip first 2
                .limit(3); // limit to first 3
        try {
            while (cur.hasNext()) {
                System.out.println(cur.next());
            }
        } finally {
            cur.close();
        }

    }
}
