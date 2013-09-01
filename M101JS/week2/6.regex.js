var MongoClient = require('mongodb').MongoClient,
		request = require('request');

MongoClient.connect('mongodb://localhost:27017/course', function(err, db) {
    if(err) throw err;
	
		var query = {title: {$regex : 'NSA'}};


		db.collection('reddit').find(query,{title:1, _id:0}).each(function(err,doc){
				if(err) throw err;

				if(doc == null){
					db.close();		
				}

				console.log(doc);
		});
});
