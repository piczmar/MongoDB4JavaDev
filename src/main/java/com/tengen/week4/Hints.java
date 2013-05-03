package com.tengen.week4;

import com.mongodb.*;
import com.mongodb.util.JSON;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Shows how to import object to db.
 */
public class Hints {
    public static void main(String[] args) throws IOException, ParseException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("course");
        DBCollection fooCollection = db.getCollection("hints");
        //createTestData(fooCollection);

        DBObject query = new BasicDBObject("a",40000).append("b",40000).append("c",40000);

        //DBObject explain = fooCollection.find(query).hint("a_1_b_-1_c_1").explain();
        DBObject explain = fooCollection.find(query).hint(new BasicDBObject("a",1).append("b",-1).append("c",1)).explain();
        System.out.println(explain);

        explain = fooCollection.find(query).hint("a_1_b_1_c_1").explain();
        System.out.println(explain);
        //System.out.println("Print collection..");
        //printCollection(fooCollection, null, null);
    }

    private static void createTestData(DBCollection fooCollection) {
        fooCollection.drop();
        fooCollection.ensureIndex(new BasicDBObject("a",1).append("b",1).append("c",1));
        fooCollection.ensureIndex(new BasicDBObject("a",1).append("b",-1).append("c",1));
        for (int i = 0; i < 500000; i++) {
            fooCollection.insert(new BasicDBObject("a", i)
                    .append("b", i)
                    .append("c", i));
        }
    }


    private static void printCollection(DBCollection collection, DBObject criteria, DBObject skipFields) {
        System.out.println("------");
        System.out.println("size = " + collection.find().size());
        DBCursor cur = null;
        cur = collection.find(criteria, skipFields).limit(20);
        try {
            while (cur.hasNext()) {
                System.out.println(cur.next());
            }
        } finally {
            cur.close();
        }
    }
}
