Stop-Process -Name mongod
Stop-Process -Name mongos
 
rm -r /data/config
rm -r /data/shard*

mkdir /data/shard0 
mkdir /data/shard1
mkdir /data/shard0/rs0 
mkdir /data/shard0/rs1 
mkdir /data/shard0/rs2
mkdir /data/shard1/rs0 
mkdir /data/shard1/rs1 
mkdir /data/shard1/rs2
mkdir /data/shard2/rs0 
mkdir /data/shard2/rs1 
mkdir /data/shard2/rs2
mkdir /data/config/config-a
mkdir /data/config/config-b
mkdir /data/config/config-c


# start a replica set and tell it that it will be a shard0
start-process mongod -ArgumentList '--replSet s0', '--logpath s0-r0.log', '--dbpath /data/shard0/rs0', '--port 37017', '--shardsvr', '--smallfiles' -Verb runAs 
start-process mongod -ArgumentList '--replSet s0', '--logpath s0-r1.log', '--dbpath /data/shard0/rs1', '--port 37018', '--shardsvr', '--smallfiles' -Verb runAs
start-process mongod -ArgumentList '--replSet s0', '--logpath s0-r2.log', '--dbpath /data/shard0/rs2', '--port 37019', '--shardsvr', '--smallfiles' -Verb runAs

sleep 10
# connect to one server and initiate the set
Start-Process .\init-replica0.bat

# start a replicate set and tell it that it will be a shard1
start-process mongod -ArgumentList '--replSet s1', '--logpath s1-r0.log', '--dbpath /data/shard1/rs0', '--port 47017', '--shardsvr', '--smallfiles' -Verb runAs
start-process mongod -ArgumentList '--replSet s1', '--logpath s1-r1.log', '--dbpath /data/shard1/rs1', '--port 47018', '--shardsvr', '--smallfiles' -Verb runAs
start-process mongod -ArgumentList '--replSet s1', '--logpath s1-r2.log', '--dbpath /data/shard1/rs2', '--port 47019', '--shardsvr', '--smallfiles' -Verb runAs

sleep 10
# connect to one server and initiate the set
Start-Process .\init-replica1.bat

# start a replicate set and tell it that it will be a shard2
start-process mongod -ArgumentList '--replSet s2', '--logpath s2-r0.log', '--dbpath /data/shard1/rs0', '--port 57017', '--shardsvr', '--smallfiles' -Verb runAs 
start-process mongod -ArgumentList '--replSet s2', '--logpath s2-r1.log', '--dbpath /data/shard1/rs1', '--port 57018', '--shardsvr', '--smallfiles' -Verb runAs
start-process mongod -ArgumentList '--replSet s2', '--logpath s2-r2.log', '--dbpath /data/shard1/rs2', '--port 57019', '--shardsvr', '--smallfiles' -Verb runAs

sleep 10
# connect to one server and initiate the set
Start-Process .\init-replica2.bat


# now start 3 config servers
start-process mongod -ArgumentList '--logpath cfg-a.log', '--dbpath /data/config/config-a', '--port 57040', '--configsvr', '--smallfiles' -Verb runAs
start-process mongod -ArgumentList '--logpath cfg-b.log', '--dbpath /data/config/config-b', '--port 57041', '--configsvr', '--smallfiles' -Verb runAs
start-process mongod -ArgumentList '--logpath cfg-c.log', '--dbpath /data/config/config-c', '--port 57042', '--configsvr', '--smallfiles' -Verb runAs

sleep 10

# now start the mongos on a standard port
start-process mongos -ArgumentList '--logpath mongos-1.log', '--configdb localhost:57040,localhost:57041,localhost:57042' -Verb runAs

echo "Waiting 60 seconds for the replica sets to fully come online"
sleep 60
echo "Connnecting to mongos and enabling sharding"

# add shards and enable sharding on the test db
start-process .\init-admin.bat

