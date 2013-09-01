var MongoClient = require('mongodb').MongoClient,
		request = require('request');

MongoClient.connect('mongodb://localhost:27017/course', function(err, db) {
    if(err) throw err;
		
		// auto-generated id
		/*var doc = {student : 'Calvin', age: 6};
		db.collection('students').insert(doc,function(err, inserted){
			if(err) throw err;
			
			console.dir("successsfully inserted: "+ JSON.stringify(inserted) );

			return db.close();

		});*/

		// self-assigned id
		/*doc = {_id: 'calvin', age: 7}
		db.collection('students').insert(doc,function(err, inserted){
			if(err) throw err;
			
			console.dir("successsfully inserted: "+ JSON.stringify(inserted) );

			return db.close();

		});*/


		// inserting multiple

		var docs = [{_id: 'john', age: 7},{_id: 'jack', age: 7}];

		db.collection('students').insert(docs,function(err, inserted){
			if(err) throw err;
			
			console.dir("successsfully inserted: "+ JSON.stringify(inserted) );

			return db.close();

		});

});
