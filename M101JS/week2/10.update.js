var MongoClient = require('mongodb').MongoClient,
		request = require('request');

MongoClient.connect('mongodb://localhost:27017/course', function(err, db) {
    if(err) throw err;
		
		// replacement update
		

		var query = {assignment : 'hw1'};

		/*
		db.collection('grades').findOne(err,function(err, doc){
			if(err) throw err;
			
			if(!doc){
				console.log('No duuments for assignment ' + query.assignment  + ' found');
				return db.close();
			}
		
			query['_id'] = doc['_id'];
			doc['date_returned'] = new Date();

			db.collection('grades').update(query,doc,function(err,updated){
				if(err) throw err;
					
				console.dir("successfully updated " + updated);

				return db.close();
			});

		});
		*/

		// in place update
		/*var operator = {$set: {'date_returned' : new Date()}};
		db.collection('grades').update(query,operator,function(err, updated){
			if(err) throw err;
			
			console.dir("successfully updated " + updated);

			return db.close();
		});
		*/
		// multiple update
		var operator = {$unset: {'date_returned' : 1}};
		var options = {multi: true}
		db.collection('grades').update(query,operator,options,function(err, updated){
			if(err) throw err;
			
			console.dir("successfully updated " + updated);

			return db.close();
		});
});
