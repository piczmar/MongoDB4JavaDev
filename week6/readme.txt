1. run in PowerShell
.{.\create_replica_set.ps1}
2. run in cmd
mongo -port 27018 < init_replica.js

mongo -port 27018 

> rs.status()
> db.people.insert({name: "marcin"})
> exit

mongo -port 27019
> db.people.find()

- you can't query secondary by default so you need to allow it:
> rs.slaveOk()
> db.people.find()
> exit

- synchronized data is first put to local db on node to which we write:
mongo -port 27018
> rs.isMaster()
> use local
> show collections
> db.oplog.rs.find().pretty()

- shutdown primary and see how long it takes for new to be elected

mongo -port 27019
> rs.status()