var MongoClient = require('mongodb').MongoClient;

MongoClient.connect('mongodb://localhost:27017/course', function(err, db) {
    if(err) throw err;

//    var query = { 'grade' : 100 };
		var query = { 'student' : 'Joe', grade:{$gt: 80, $lt: 95}};

		var projection = {_id: 0, student: 1, grade: 1};

    var cursor = db.collection('grades').find(query,projection);
		console.log('cursor returned: ' + cursor);

		cursor.toArray(function(err, docs) {
        if(err) throw err;

				// javascript array .forEach() looping
				docs.forEach(function(doc){
		        console.dir(doc);
				});

				db.close();

    });
});
