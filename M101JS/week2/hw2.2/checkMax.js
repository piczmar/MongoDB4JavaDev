var MongoClient = require('mongodb').MongoClient,
		request = require('request');

MongoClient.connect('mongodb://localhost:27017/weather', function(err, db) {
    if(err) throw err;

		var options = {
			sort : [['State' , -1],['Temperature' , -1]]
		};

		var currState;
		var cursor = db.collection('data').find({},{},options);
		cursor.each(function(err,doc){
				if(err) throw err;

				if(doc == null){
					return db.close();		
				}
				if(doc['State'] !== currState){
					console.log(doc['State'] + ' ' + doc['Temperature'] + ' ' + doc['_id']);
					currState = doc['State'];
					doc["month_high"] = true;
					db.collection('data').save(doc,function(err,saved){
							if(err) throw err;
							console.dir('saved doc ' + saved);
					});
				}
		});
});
