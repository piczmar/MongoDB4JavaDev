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
final DB blogDatabase = mongoClient.getDB("school")
DBCollection collection = blogDatabase.getCollection("students")
DBCursor cur = collection.find();


try {
    String sId = null
    while (cur.hasNext()) {
        DBObject doc = cur.next()
        println doc
        doc = removeLowestScore(doc)
        collection.update(new BasicDBObject("_id", doc.get("_id")), doc, false, false)
    }
} finally {
    cur.close();
}

DBObject removeLowestScore(DBObject student) {
    def scores = student.get("scores")
    if (!scores) {
        return student
    }
    def mc = [compare: {a, b -> a.toBigDecimal() == b.toBigDecimal() ? 0 : a.toBigDecimal() < b.toBigDecimal() ? -1 : 1}] as Comparator
    BigDecimal lowestScore = scores.findAll({it.get("type") == "homework"}).collect({it.get("score")}).min(mc)
    List newScores = scores.findAll({it.get("score") != lowestScore})
    student.put("scores", newScores)
    return student
}

println "After delete remained ${collection.find().count()} items"
