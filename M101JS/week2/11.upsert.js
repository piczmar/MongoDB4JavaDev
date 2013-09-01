var MongoClient = require('mongodb').MongoClient,
		request = require('request');

MongoClient.connect('mongodb://localhost:27017/course', function(err, db) {
    if(err) throw err;
		
// if there is no ducument matching update query mongo will insert new document
		var query = {student: 'Frank', assignment : 'hw1'};
		//var operator = {student: 'Frank', assignment : 'hw1', grade: 100};
		var operator = {$set:{date_returned: new Date(), grade: 100}};		
		var options = {upsert: true}
		db.collection('grades').update(query,operator,options,function(err, updated){
			if(err) throw err;
			
			console.dir("successfully upserted " + updated);

			return db.close();
		});
});
