/* start this program, then connect to mongo MASTER replica node and shutdown server:
 > use admin
 > db.shutdownServer()
 */

var MongoDb = require('mongodb');
var MongoClient = MongoDb.MongoClient;
var ReadPreference = MongoDb.ReadPreference;

MongoClient.connect('mongodb://localhost:27017,localhost:27018,localhost:27019/course?ReadPreference=secondary', function(err,db){
	if(err) throw err;

	db.collection('repl').insert({x:1}, function(err,doc){
			if(err) throw err;

			console.log(doc);
	});

	var findDoc = function(){

		db.collection('repl').findOne({x:1},
				function(err,doc){
			if(err) throw err;
			
			console.log(doc);
		});


		console.log('Dispatched insert');
		setTimeout(findDoc,1000);
	};


	findDoc();
});