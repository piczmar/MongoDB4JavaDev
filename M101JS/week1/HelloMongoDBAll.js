var express = require('express');
	app = express(),
	cons = require('consolidate'),
	MongoClient = require('mongodb').MongoClient,
	Server = require('mongodb').Server;

app.engine('html',cons.swig); // using consolidate to register Swig with Express
app.set('view engine','html');
app.set('views',__dirname + '/views');
app.use(express.bodyParser()); // register middleware with express to parse request body for post variables
app.use(app.router);

function errorHandler(err, req, res, next){
	console.error(err.message);
	console.error(err.stack);
	res.status(500);
	res.render('error_template',{error:err});
}

app.use(errorHandler);

var mongoclient = new MongoClient( new Server('localhost',27001, {'native_parser' : true}));

var db = mongoclient.db('course');


app.get('/',function(req,res){
	
	db.collection('hello_mongo_express').findOne({},function(err,doc){
		res.render('hello',doc);
	});


});


app.get('/fruits',function(req,res){
	res.render('fruits',{fruits: ['Banana', 'Apple', 'Orange']});
});

app.post('/fruits',function(req,res,next){
	var favourite = req.body.fruit;
	if(typeof favourite == 'undefined'){
		next(Error('Please choose a fruit!'));
	}else{
		res.send('Your favourite frout is ' + favourite);
	}
});

/*app.get('/:name',function(req, res, next){
	var name = req.params.name;
	var getvar1 = req.query.getvar1;
	var getvar2 = req.query.getvar2;
	res.render('hello1', {name: name, getvar1: getvar1, getvar2: getvar2})
});*/

// anything else
app.get('*',function(req,res){
	res.send("Page not found",404);
});

mongoclient.open(function(err,mongoclient){
	if(err) throw err;

	app.listen("8000");
	console.log("Server is started on port 8000");

})


