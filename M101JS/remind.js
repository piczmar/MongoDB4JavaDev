var express = require('express');
var app = express(),
		MongoClient = require('mongodb').MongoClient,
		Server = require('mongodb').Server;

var mongo = new MongoClient(new Server('localhost',27017));
var db = mongo.db('test');


app.get('/',function(req,res){
	db.collection('people').findOne(function(err,obj){
		console.log('found ' + obj.name);
		res.send('found ' + obj.name);
	});

});
mongo.open(function(err,mongoclient){
	if(err) throw err;

	app.listen("8000");
	console.log("Server is started on port 8000");
});
