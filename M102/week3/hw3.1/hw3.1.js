

// before runing this with mongo < hw3.1.js import this:

// mongoimport -d hw31 -c zips zips.js


use hw31;

//db.zips.count();

db.zips.aggregate([
	{ $group : {
        _id : { state: "$state"},
        sum : { $sum : 1 }
    }
	}, 
	{ $sort: {sum : 1}
	}
]);

