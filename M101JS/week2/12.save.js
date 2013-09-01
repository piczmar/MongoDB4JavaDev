var MongoClient = require('mongodb').MongoClient,
		request = require('request');

MongoClient.connect('mongodb://localhost:27017/course', function(err, db) {
    if(err) throw err;
		
		// replacement update
		

		var query = {assignment : 'hw2'};

	
		db.collection('grades').findOne(err,function(err, doc){
			if(err) throw err;
			
			doc['date_returned'] = new Date();
			// if no document found a new document will be inserted, otherwise updated
			db.collection('grades').save(doc,function(err,saved){
				if(err) throw err;
					
				console.dir("successfully saved " + saved);

				return db.close();
			});

		});
		
});
