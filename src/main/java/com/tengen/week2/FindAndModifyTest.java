package com.tengen.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Shows how to update docs.
 */
public class FindAndModifyTest {
    public static void main(String[] args) throws UnknownHostException {

        DBCollection collection = createCollection();
        collection.drop();

        final String counterId = "abc";
        int first, numNeeded;

        numNeeded = 2;
        first = getRange(counterId, numNeeded, collection);
        System.out.println("Range: " + first + "-" + (first + numNeeded - 1));

        numNeeded = 3;
        first = getRange(counterId, numNeeded, collection);
        System.out.println("Range: " + first + "-" + (first + numNeeded - 1));

        numNeeded = 10;
        first = getRange(counterId, numNeeded, collection);
        System.out.println("Range: " + first + "-" + (first + numNeeded - 1));

        printCollection(collection);
    }

    private static int getRange(String id, int range, DBCollection collection) throws UnknownHostException {
        DBObject doc = collection.findAndModify(
                new BasicDBObject("_id", id), null, null, false,
                new BasicDBObject("$inc",new BasicDBObject("counter", range)), true, true
        );
        return (Integer) doc.get("counter") - range + 1;
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
        DBCollection collection = db.getCollection("FindAndModifyTest");

        collection.drop();//clean collection
        return collection;
    }
}
