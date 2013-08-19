var express = require('express');
	app = express(),
	cons = require('consolidate');

app.engine('html',cons.swig); // using consolidate to register Swig with Express
app.set('view engine','html');
app.set('views',__dirname + '/views');

app.get('/',function(req,res){
	res.render('hello',{'name' : 'Swig'});
});


// anything else
app.get('*',function(req,res){
	res.send("Page not found",404);
});

app.listen("8000");
console.log("Server is started on port 8000");
