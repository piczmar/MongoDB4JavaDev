var MongoClient = require('mongodb').MongoClient,
		request = require('request');

MongoClient.connect('mongodb://localhost:27017/course', function(err, db) {
    if(err) throw err;
		
		// replacement update
		

		var query = {assignment : 'hw3'};
	
		db.collection('grades').remove(query, function(err, removed){
				if(err) throw err;
			
				console.dir('successfully removed ' + removed + ' docs');

				return db.close();
			});
		
});
