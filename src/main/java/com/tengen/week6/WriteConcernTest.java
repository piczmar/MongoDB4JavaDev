package com.tengen.week6;


import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * <p>WriteConcern control the acknowledgment of write operations with various options.
 * <p>
 * <b>w</b>
 * <ul>
 * <li>-1 = Don't even report network errors </li>
 * <li> 0 = Don't wait for acknowledgement from the server </li>
 * <li> 1 = Wait for acknowledgement, but don't wait for secondaries to replicate</li>
 * <li> 2+= Wait for one or more secondaries to also acknowledge </li>
 * </ul>
 * <b>wtimeout</b> how long to wait for slaves before failing
 * <ul>
 * <li>0: indefinite </li>
 * <li>greater than 0: ms to wait </li>
 * </ul>
 * </p>
 * <p/>
 * Other options:
 * <ul>
 * <li><b>j</b>: wait for group commit to journal</li>
 * <li><b>fsync</b>: force fsync to disk</li>
 * </ul>
 */
public class WriteConcernTest {
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        /**
         * Pass a list of servers, because we never know which will be PRIMARY on failover
         */
        MongoClient client = new MongoClient(Arrays.asList(
                new ServerAddress("localhost", 27017),
                new ServerAddress("localhost", 27018),
                new ServerAddress("localhost", 27019)
        ));
        DBCollection collection = client.getDB("course").getCollection("write.test");
        collection.drop();
        //collection.insert(new BasicDBObject("_id", 1));   // default write concern - WriteConcern.NORMAL, w=0

        collection.insert(new BasicDBObject("_id", 1));

        try {
            //collection.insert(new BasicDBObject("_id", 1));
            collection.insert(new BasicDBObject("_id", 1)  , WriteConcern.UNACKNOWLEDGED); // write without acknowledge, fast write but no errors reported
        } catch (MongoException.DuplicateKey exc) {
            System.out.println(exc.getMessage());
        }
    }
}
