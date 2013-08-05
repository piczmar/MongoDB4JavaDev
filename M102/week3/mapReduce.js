use mapreduce;
db.things.drop();

db.things.insert({_id: 1, tags: ["dog", "cat"]});
db.things.insert({_id: 2, tags: ["cat"]});
db.things.insert({_id: 3, tags: ["mouse", "cat", "dog"]});
db.things.insert({_id: 4, tags: []});

db.things.find();

print("-----");

m = function(){
	this.tags.forEach(
		function(z){
			emit(z, {count: 1});		
		}
	);
};

// test map function using dummy emit function and single element of collection
function emit(k,v){
	print(k + " - " + tojson(v));
}
x = db.things.findOne();

m.apply(x);



r = function(key, values){
	var total = 0;
	for(var i = 0; i<values.length; i++){
		total += values[i].count;
	}
	return {count: total};
};

// test the reduce on dummy input

r("dog", [{count: 1}, {count: 1}, {count: 2}]);

// use seal map-reduce;

res = db.things.mapReduce(m,r, {out:{inline: 1}});


// equivalent way:

res = db.runCommand({
		mapreduce: "things", 
		map: m, 
		reduce: r, 
		out: {inline: 1}, 
		query: {_id: 3}, 
		sort: {_id: 1}, 
		finalize: function(k,v){ return "out -> "+ v.count}
	});

