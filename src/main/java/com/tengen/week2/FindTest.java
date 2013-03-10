package com.tengen.week2;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Shows how to insert Mongo DB documents
 * Check this out in mongo console:
 * mongo course
 *
 * db.insertTest.find()
 */
public class FindTest {
    public static void main(String[] args) throws UnknownHostException {

        MongoClient client = new MongoClient();
        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("insertTest");

        collection.drop();//clean collection

        DBObject doc = new BasicDBObject("x", 1);
        System.out.println(doc);
        collection.insert(doc);
        System.out.println(doc);
        //insert another doc, this time explicitly set id
        doc = new BasicDBObject("_id", new ObjectId()).append("x", 2);
        System.out.println(doc);
        collection.insert(doc);
        System.out.println(doc);

        // you can also insert multiple at once
        DBObject doc2 = new BasicDBObject("x", 3);
        DBObject doc3 = new BasicDBObject("x", 4);
        collection.insert(Arrays.asList(doc2, doc3));

        //expect exception when inserting the same doc twice
        try{
            collection.insert(doc3);
        }catch(Exception exc){
            exc.printStackTrace();
        }

        //but if you clean the ID the insert succeeds
        doc3.removeField("_id");  // remove the "_id" field
        collection.insert(doc3);      // second insert
    }
}
