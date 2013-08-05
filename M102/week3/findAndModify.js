use test;
db.my_queue.drop();

t = db.my_queue;
t.find();

t.insert({ts: new Date(), job: "c", notes: "just a test of findandmodify"});
t.insert({ts: new Date(), job: "e", notes: "just a test of findandmodify 2"});
t.insert({ts: new Date(), job: "a", notes: "just a test of findandmodify"});
t.insert({ts: new Date(), job: "b", notes: "just a test of findandmodify 2"});

t.find();

print("--------------");

t.findAndModify({
	sort: {ts:1},
	remove: true
});

print("--------------");
t.find();
print("--------------");

t.findAndModify({
	query: {status:null},
	update: {$set: {status: "inprogress"}},
	sort: {ts: 1}
});



print("--------------");
t.find();
print("--------------");



