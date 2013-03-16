package com.tengen.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * Shows how to find docs.
 */
public class DotNotationTest {
    public static void main(String[] args) throws UnknownHostException {

        MongoClient client = new MongoClient();
        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("DotNotationTest");

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

        //query using dot notation

        QueryBuilder builder = QueryBuilder.start("start.x").greaterThan(50);
        DBObject query = builder.get();

        System.out.println("Count:");
        System.out.println(collection.count());

        System.out.println("Find all:");
        DBCursor cur = collection.find(query, new BasicDBObject("start.y",true));
        try {
            while (cur.hasNext()) {
                System.out.println(cur.next());
            }
        } finally {
            cur.close();
        }

    }
}
