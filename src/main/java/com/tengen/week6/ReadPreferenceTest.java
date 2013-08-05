package com.tengen.week6;


import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;

public class ReadPreferenceTest {
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        /**
         * Pass a list of servers, because we never know which will be PRIMARY on failover
         */
        MongoClient client = new MongoClient(Arrays.asList(
                new ServerAddress("localhost", 27017),
                new ServerAddress("localhost", 27018),
                new ServerAddress("localhost", 27019)
        ));
        // you can set read preference on different levels
        client.setReadPreference(ReadPreference.nearest());

        DBCollection collection = client.getDB("course").getCollection("write.test");
        collection.setReadPreference(ReadPreference.primaryPreferred());

        DBCursor curr = collection.find().setReadPreference(ReadPreference.primary());

        try {
            while (curr.hasNext()) {
                System.out.println(curr.next());
            }
        } finally {
            curr.close();
        }
    }
}
