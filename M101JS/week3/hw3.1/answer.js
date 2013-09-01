var MongoDB = require('mongodb');
var MongoClient = MongoDB.MongoClient;


var updateDocs = function(docs, index, db){
	if(index>=docs.length){
		db.close();
		return;
	}
	var doc = docs[index];

	console.log('Saving doc ' + JSON.stringify(doc));
		// TODO: why for any reason the following does not work?
		// db.collection('students').save(doc, function(err, saved){		
			db.collection('students').update({_id: doc._id},doc,function(err, saved){
				console.log('Result: ' + saved);
				if(err) throw err;
				updateDocs(docs, index+1, db);
			});

};

MongoClient.connect('mongodb://localhost:27017/school', function(err, db) {
	if(err) throw err;
	var collection = db.collection('students')
	var cursor = collection.find({})

	cursor.toArray(function(err, docs){
		if(err) throw err;

		for(var d=0 ; d<docs.length; d++){
			var doc= docs[d];
			var scoresArr = doc.scores;
			var min = new MongoDB.Double(Number.MAX_VALUE);
			var minIndx = -1;
			for(var i = 0; i<scoresArr.length; i++){
				var score = scoresArr[i];

				if(score.type === 'homework'){
					if(score.score < min){
						min = score.score;
						minIndx = i;		
					}
				}
			}		
			scoresArr.splice(minIndx,1);
		}
		updateDocs(docs, 0, db);
	});
});

