//var mongoClient = require('mongodb').MongoClient;
var http = require('http');

var server = http.createServer(function (request, response){
	response.writeHead(200, {"content-Type" : "text/plain"});
	response.end("Hello World!");
})

server.listen(8000);
console.log("Server running at http://localhost:8000");
