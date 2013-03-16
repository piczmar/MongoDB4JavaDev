package com.tengen.week3;

import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Shows how to update docs.
 */
public class GridFSTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("course");

        FileInputStream inputStream = null;

        GridFS videos = new GridFS(db, "videos");

        try {
            inputStream = new FileInputStream("video.mp4");
        } catch (FileNotFoundException exc) {
            System.out.println("Can't find the file");
            System.exit(1);
        }

        GridFSInputFile video = videos.createFile(inputStream, "video.mp4");
        BasicDBObject meta = new BasicDBObject("description", "My new large file")
                .append("tags", Arrays.asList("opera", "singing"));
        video.setMetaData(meta);

        video.save();
        System.out.println("object id in files collection : " + video.get("_id"));

        System.out.println("Now the file is in DB, let's get it back...");
        GridFSDBFile gridFile = videos.findOne(new BasicDBObject("filename", "video.mp4"));

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream("video_copy.mp4");
            gridFile.writeTo(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Print collections..");
        DBCollection filesCollection = db.getCollection("videos.files");
        DBCollection chunksCollection = db.getCollection("videos.chunks");
        printCollection(filesCollection, null, null);
        printCollection(chunksCollection, null, null);


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
