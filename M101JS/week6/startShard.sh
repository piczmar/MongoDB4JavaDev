mkdir -p shard0/rs1 shard0/rs2 shard0/rs3
mkdir -p shard1/rs1 shard1/rs2 shard1/rs3
mkdir -p cfg/rs1 cfg/rs2 cfg/rs3

mongod --replSet s0 --logpath "shard0/1.log" --dbpath shard0/rs1 --port 37017 --shardsvr --fork 
mongod --replSet s0 --logpath "shard0/2.log" --dbpath shard0/rs2 --port 37018 --shardsvr --fork
mongod --replSet s0 --logpath "shard0/3.log" --dbpath shard0/rs3 --port 37019 --shardsvr --fork

mongo --port 37017 << 'EOF'
config = { _id: "s0", members:[
         { _id : 0, host : "localhost:37017"},
         { _id : 1, host : "localhost:37018"},
         { _id : 2, host : "localhost:37019"} ]
        };
rs.initiate(config);
EOF

mongod --replSet s1 --logpath "shard1/1.log" --dbpath shard1/rs1 --port 47017 --shardsvr --fork 
mongod --replSet s1 --logpath "shard1/2.log" --dbpath shard1/rs2 --port 47018 --shardsvr --fork
mongod --replSet s1 --logpath "shard1/3.log" --dbpath shard1/rs3 --port 47019 --shardsvr --fork

mongo --port 47017 << 'EOF'
config = { _id: "s1", members:[
         { _id : 0, host : "localhost:47017"},
         { _id : 1, host : "localhost:47018"},
         { _id : 2, host : "localhost:47019"} ]
        };
rs.initiate(config);
EOF

mongod --logpath "cfg/1.log" --dbpath cfg/rs1 --port 57017 --fork --configsvr 
mongod --logpath "cfg/2.log" --dbpath cfg/rs2 --port 57018 --fork --configsvr
mongod --logpath "cfg/3.log" --dbpath cfg/rs3 --port 57019 --fork --configsvr


sleep 180

mongos --logpath mongos.log --configdb "localhost:57017,localhost:57018,localhost:57019" --fork

mongo << 'EOF'
db.adminCommand({addShard: "s0/localhost:37017"});
db.adminCommand({addShard: "s1/localhost:47017"});
db.adminCommand({enableSharding: "test"});
db.adminCommand({shardCollection: "test.grades", key: {student_id: 1}});
EOF