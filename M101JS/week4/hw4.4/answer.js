// run this from shell with cmd: mongo < answer.js


use m101;

db.profile.find({"op": "query", ns: "school2.students"},{millis: 1, _id: 0}).sort({millis: -1}).limit(1).pretty();

