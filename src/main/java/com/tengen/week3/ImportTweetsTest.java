package com.tengen.week3;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.eclipse.jetty.util.UrlEncoded;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Shows how to import object to db.
 */
public class ImportTweetsTest {
    public static void main(String[] args) throws IOException, ParseException {
        String screenName = "MongoDB";
        Collection<DBObject> tweets = getLatestTweets(screenName);
        MongoClient client = new MongoClient();
        DB db = client.getDB("course");
        DBCollection tweetsCollection = db.getCollection("tweets");
        //tweetsCollection.drop();

        for (DBObject tweet : tweets) {
            tweet.put("screen_name", screenName);
            tweet = messageTweetId(tweet);
            tweet = messageTweet(tweet);
            //tweetsCollection.insert(tweet); // when doc with the same ID alrady exists this will fail
            tweetsCollection.update(new BasicDBObject("_id", tweet.get("_id")), tweet, true, false);
        }

        System.out.println("Print collection..");
        printCollection(tweetsCollection, null, null);
    }

    private static DBObject messageTweetId(DBObject tweet) {
        Object id = tweet.get("id");
        tweet.removeField("id");
        tweet.put("_id", id);
        return tweet;
    }

    private static DBObject messageTweet(DBObject tweet) throws ParseException {
        String createdTime = (String) tweet.get("created_at");
        SimpleDateFormat fmt = new SimpleDateFormat("EEE MMM d H:m:s Z y", Locale.ENGLISH);
        tweet.put("created_at", fmt.parse(createdTime));
        DBObject user = (DBObject) tweet.get("user");
        for (Iterator i = user.keySet().iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            if (!(key.equals("id") || key.equals("name") || key.equals("screen_name"))) {
                i.remove();
            }
        }
        tweet.put("user", user);
        return tweet;
    }

    private static Collection<DBObject> getLatestTweets(String screenName) throws IOException {
        URL url = new URL("http://api.twitter.com/1/statuses/user_timeline.json?screen_name=" + screenName + "&include_rts=1");
        InputStream inputStream = url.openStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int ret = 0;
        while ((ret = inputStream.read()) != -1) {
            outputStream.write(ret);
        }
        final String tweetString = outputStream.toString();
        List<DBObject> jsonObject = (List<DBObject>) JSON.parse(tweetString);
        return jsonObject;  //To change body of created methods use File | Settings | File Templates.
    }

    private static void printCollection(DBCollection collection, DBObject criteria, DBObject skipFields) {
        System.out.println("------");
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
