mkdir /data/rs1 
mkdir /data/rs2 
mkdir /data/rs3
start-process mongod -ArgumentList '--replSet m101', '--logpath 1.log', '--dbpath /data/rs1', '--port 27017', '--smallfiles' 
start-process mongod -ArgumentList '--replSet m101', '--logpath 2.log', '--dbpath /data/rs2', '--port 27018', '--smallfiles'
start-process mongod -ArgumentList '--replSet m101', '--logpath 3.log', '--dbpath /data/rs3', '--port 27019', '--smallfiles'