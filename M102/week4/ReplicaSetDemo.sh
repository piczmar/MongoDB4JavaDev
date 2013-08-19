#!/bin/bash
# mongo help - explains parameters used
mkdir -p ~/data/1 ~/data/2 ~/data/3
cd ~/data
mongod --rest --replSet abc --dbpath ~/data/1 --port 27001 --smallfiles --oplogSize 50 --logpath log.1 --logappend --fork
mongod --rest --replSet abc --dbpath ~/data/2 --port 27002 --smallfiles --oplogSize 50 --logpath log.2 --logappend --fork
mongod --rest --replSet abc --dbpath ~/data/3 --port 27003 --smallfiles --oplogSize 50 --logpath log.3 --logappend --fork

:' now replica set is running but not initialized yet
  you can check that when you connect to mongo shell that there is no master, e.g.:
  mongo --port 27001
  > db.isMaster()
'

:' ... so, lets initialize it
  as there is no member that has config yet then choose any and initiate:
  mongo --port 27001
  > cfg = {
			_id : "abc", 
			members: [
				{_id: 0, host: "thinkpad:27001"},
				{_id: 1, host: "thinkpad:27002"},
				{_id: 2, host: "thinkpad:27003"}
			]
   }

	list replica set commands:
	> rs.help()
	then you find command to initiate:
	> rs.initiate(cfg)


	you can reconfig set when majority of servers is online like this:
	- connect to primary

	> cfg = rs.config()
  > cfg.members[2].slaveDelay = 8 * 3600
  > cfg.members[2].hidden = true
  > cfg.members[2].priority = 0
  > rs.reconfig(cfg)


  mkdir ~/data/a
	mongod --rest --replSet abc --dbpath a --port 27004 --oplogSize 50 --logpath log.a --logappend --fork

	> cfg = {
			_id : "abc", 
			members: [
				{_id: 0, host: "thinkpad:27001", priority: 1},
				{_id: 1, host: "thinkpad:27002"},
				{_id: 2, host: "thinkpad:27003", priority: 0.5},
				{_id: 3, host: "thinkpad:27004", arbiterOnly: true}
			]
   }
	> rs.reconfig(cfg)
	
  >  rs.isMaster()

result:

{
	"setName" : "abc",
	"ismaster" : false,
	"secondary" : false,
	"hosts" : [
		"localhost:27002",
		"localhost:27001"
	],
	"arbiters" : [
		"localhost:27004"
	],
	"primary" : "localhost:27002",
	"arbiterOnly" : true,
	"me" : "localhost:27004",
	"maxBsonObjectSize" : 16777216,
	"localTime" : ISODate("2013-08-10T13:50:03.514Z"),
	"ok" : 1
}

'


