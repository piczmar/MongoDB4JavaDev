var MongoClient = require('mongodb').MongoClient,
		request = require('request');

var redditUrl = 'http://www.reddit.com/r/technology/.json'

MongoClient.connect('mongodb://localhost:27017/course', function(err, db) {
    if(err) throw err;
	
		request('http://www.reddit.com/r/technology/.json',function(error, response, body){
				console.log('got response, code ');

					var obj = JSON.parse(body);
					console.log(obj);
					var stories = obj.data.children.map(function(story){return story.data;});

					db.collection('reddit').insert(stories, function (err,data){
							if(err) throw err;
						
							console.dir(data);

							db.close();
					});

		});

});
