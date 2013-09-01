var MongoClient = require('mongodb').MongoClient,
		request = require('request');

MongoClient.connect('mongodb://localhost:27017/course', function(err, db) {
    if(err) throw err;
		
		// replacement update
		

		var query = {name : 'comments'};
		var sort = [];
		var operator = {$inc: {counter : 1}}
		var options = {new : true}
	
		db.collection('counters').findAndModify(query, sort, operator, options,
			function(err, doc){
				if(err) throw err;
			
				if(!doc){
					console.log('No counter found for comments.');
				}else{
					console.log('Number of comments: ' + doc.counter);
				}
				return db.close();
			});
		
});
