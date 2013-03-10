package com.tengen.week2;

import com.mongodb.BasicDBObject;

import java.util.Arrays;
import java.util.Date;

/**
 *  Shows how to create Mongo DB document
 */
public class InsertTest {
    public static void main(String[] args) {
        BasicDBObject doc = new BasicDBObject();
        doc.put("username", "jyemin");
        doc.put("birthDate", new Date());
        doc.put("programmer", true);
        doc.put("age", 20);
        doc.put("languages", Arrays.asList("Java", "C++"));
        doc.put("address", new BasicDBObject("street", "20 Main")
                .append("town", "Westfield")
                .append("zip", "21321"));
    }
}
