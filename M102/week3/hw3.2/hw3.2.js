use hw31;

x = db.zips.count()
db.zips.remove({city: {$regex: /^[^a-z].*/i } } );
y = db.zips.count();

print ("removed " + (x-y))
