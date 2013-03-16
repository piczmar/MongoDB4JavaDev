package com.tengen.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Random;

/**
 * Shows how to update docs.
 */
public class UpdateTest {
    public static void main(String[] args) throws UnknownHostException {

        DBCollection collection = createCollection();
        for (String name : Arrays.asList("alice", "bobby", "cathy", "david", "ethan")) {
            collection.insert(new BasicDBObject("_id", name));
        }

        System.out.println("Count:");
        System.out.println(collection.count());

        collection.update(new BasicDBObject("_id", "alice"),
                new BasicDBObject("age", 20));

        printCollection(collection);

        collection.update(new BasicDBObject("_id", "alice"),
                new BasicDBObject("gender", "F"));            // update but remove old data

        printCollection(collection);

        collection.update(new BasicDBObject("_id", "alice"),
                new BasicDBObject("$set", new BasicDBObject("surname", "kovalsky")));    // incremental update

        printCollection(collection);

        // try updating object which does not exist in collection
        collection.update(new BasicDBObject("_id", "frank"),
                new BasicDBObject("$set", new BasicDBObject("gender", "M")));    // frank will not be in results

        printCollection(collection);

        // try updating object which does not exist in collection but use upsert
        collection.update(new BasicDBObject("_id", "frank"),
                new BasicDBObject("$set", new BasicDBObject("gender", "M")), true, false);    // now frank will be in results

        printCollection(collection);

        // update every document in collection - upsert=false, multi=true
        collection.update(new BasicDBObject(),
                new BasicDBObject("$set", new BasicDBObject("title", "Dr.")), false, true);    // now frank will be in results

        printCollection(collection);

        // remove by criteria
        collection.remove(new BasicDBObject("_id", "alice"));

        printCollection(collection);

    }

    private static void printCollection(DBCollection collection) {
        System.out.println("------");
        DBCursor cur = collection.find();
        try {
            while (cur.hasNext()) {
                System.out.println(cur.next());
            }
        } finally {
            cur.close();
        }
    }

    private static DBCollection createCollection() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("UpdateTest");

        collection.drop();//clean collection
        return collection;
    }
}
