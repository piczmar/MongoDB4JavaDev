var MongoClient = require('mongodb').MongoClient;

MongoClient.connect('mongodb://localhost:27001/test',function(err, db){
	if(err) throw err;

	//Findone document in our collection
	db.collection('coll').findOne({},function(err,doc){
		if(err) throw err;

		// Print the result
		console.dir(doc);

		// Close the DB
		db.close();
	});

	// Declare success
	console.dir('Called findOne!');
})
