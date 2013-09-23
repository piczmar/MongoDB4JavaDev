/* start this program, then connect to mongo MASTER replica node and shutdown server:
 > use admin
 > db.shutdownServer()
 */

var MongoDb = require('mongodb');
var MongoClient = MongoDb.MongoClient;

MongoClient.connect('mongodb://localhost:27017,localhost:27018,localhost:27019/course', function(err,db){
	if(err) throw err;

	var docNumber = 0;

	var insertDoc = function(){

		db.collection('repl').insert({documentNumber:docNumber++}, function(err,doc){
			if(err) throw err;
			
			console.log(doc);

		});
	
		console.log('Dispatched insert');
		setTimeout(insertDoc,1000);

	};

	insertDoc();

});