@Grapes(
@Grab(group = 'org.mongodb', module = 'mongo-java-driver', version = '2.10.1')
)

import com.mongodb.DB
import com.mongodb.DBCursor
import com.mongodb.DBCollection
import com.mongodb.DBObject
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.BasicDBObject

final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost"))
final DB blogDatabase = mongoClient.getDB("students")
DBCollection collection = blogDatabase.getCollection("grades")
DBCursor cur = collection.find(new BasicDBObject("type","homework"))
        .sort(new BasicDBObject(new BasicDBObject("student_id", 1).append("score", 1)))

Set<String> idsToRemove = []
try {
    String sId = null
    while (cur.hasNext()) {
        DBObject doc = cur.next()
        String newSId = doc.get("student_id")
        if (newSId != sId) {
            sId = newSId
            idsToRemove.add(doc.get("_id"))
            //println "score = ${doc.get("score")}"
        }
//        else{
//            println "higherScore ${doc.get("score")}"
//        }
    }
} finally {
    cur.close();
}

idsToRemove.each{
   collection.remove(new BasicDBObject("_id",it))
}

println "After delete remained ${collection.find().count()} items"
