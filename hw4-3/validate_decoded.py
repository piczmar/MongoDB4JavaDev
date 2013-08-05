import pymongo
import random
import re
import string
import sys
import getopt
import pprint

# Copyright 2012
# 10gen, Inc.
# Author: Andrew Erlichson   aje@10gen.com
#
# If you are a student and reading this code, turn back now, before
# the MongoDB gods smite you.

connection = None
db = None
webhost = "localhost:8082"
mongostr = "mongodb://localhost:27017"
db_name = "blog"

# this script will check that homework 4.3

# command line arg parsing to make folks happy who want to run at mongolabs or mongohq
# this functions uses global vars to communicate. forgive me.
def arg_parsing(argv):

    global webhost
    global mongostr
    global db_name

    try:
        opts, args = getopt.getopt(argv, "-p:-m:-d:")
    except getopt.GetoptError:
        print "usage validate.py -p webhost -m mongoConnectString -d databaseName"
        print "\tmongoConnectionString default to {0}".format(mongostr)
        print "\tdatabaseName defaults to {0}".format(db_name)
        sys.exit(2)
    for opt, arg in opts:
        if (opt == '-h'):
            print "usage validate.py -m mongoConnectString -d databaseName"
            sys.exit(2)
        elif opt in ("-m"):
            mongostr = arg
            print "Overriding MongoDB connection string to be ", mongostr
        elif opt in ("-d"):
            db_name = arg
            print "Overriding MongoDB database to be ", db_name

# check to see if they loaded the data set
def check_for_data_integrity():

    posts = db.posts
    try:
        count = posts.count()
    except:
        print "can't query MongoDB..is it running?"
        raise
        return False

    if (count != 1000):
        print "There are supposed to be 1000 documents. you have ", count
        return False

    # find the most popular tags
    try:

        result = db.posts.aggregate([{'$project':{'tags':1}}, 
                                     {'$unwind':'$tags'}, 
                                     {'$group':{'_id': '$tags',
                                                'count':{'$sum':1}}}, 
                                     {'$sort':{'count':-1}}, 
                                     {'$limit':10}])
    except:
        print "can't query MongoDB..is it running?"
        raise
        return False


    if (result['result'][0]['count'] != 13 or
        result['result'][0]['_id'] != "sphynx"):
        print "The dataset is not properly loaded. The distribution of post tags is wrong."
        return False

    print "Data looks like it is properly loaded into the posts collection"

    return True
    

def check_for_fast_blog_home_page():

    posts = db.posts

    try:
        explain = posts.find().sort('date', direction=-1).limit(10).explain()
    except:
        print "can't query MongoDB..is it running?"
        raise
        return False

    if (explain['nscannedObjects'] > 10):
        print "Sorry, executing the query to display the home page is too slow. "
        print "We should be scanning no more than 10 documents. You scanned", explain['nscannedObjects']
        print "here is the output from explain"

        pp = pprint.PrettyPrinter(depth=6)
        pp.pprint(explain)
        return False
    
    print "Home page is super fast. Nice job.\n"
    return True

def get_the_middle_permalink():
    posts = db.posts
    try:
        c = posts.find().skip(500).limit(1)
        for doc in c:
            permalink = doc['permalink']
            return permalink
    except:
        print "can't query MongoDB..is it running?"
        raise
    return ""

def check_for_fast_blog_entry_page():
    
    posts = db.posts

    permalink = get_the_middle_permalink()
    try:
        explain = posts.find({'permalink':permalink}).explain()
    except:
        print "can't query MongoDB..is it running?"
        raise
        return False

    if (explain['nscannedObjects'] > 1):
        print "Sorry, executing the query to retrieve a post by permalink is too slow "
        print "We should be scanning no more than 1 documents. You scanned", explain['nscannedObjects']
        print "here is the output from explain"

        pp = pprint.PrettyPrinter(depth=6)
        pp.pprint(explain)
        return False
    
    print "Blog retrieval by permalink is super fast. Nice job.\n"
    return True


def check_for_fast_posts_by_tag_page():
    posts = db.posts

    tag = "sphynx"
    try:
        explain = posts.find({'tags':tag}).sort('date', direction=-1).limit(10).explain()
    except:
        print "can't query MongoDB..is it running?"
        raise
        return False

    if (explain['nscannedObjects'] > 10):
        print "Sorry, executing the query to retrieve posts by tag for is too slow."
        print "We should be scanning no more than 10 documents. You scanned", explain['nscannedObjects']
        print "here is the output from explain"

        pp = pprint.PrettyPrinter(depth=6)
        pp.pprint(explain)
        return False
    
    print "Blog retrieval by tag is super fast. Nice job.\n"
    return True


# main section of the code
def main(argv):
            
    arg_parsing(argv)
    global connection
    global db

    print "Welcome to the HW 4.3 Checker. My job is to make sure you added the indexes"
    print "that make the blog fast in the following three situations"
    print "\tWhen showing the home page"
    print "\tWhen fetching a particular post"
    print "\tWhen showing all posts for a particular tag"

    # connect to the db (mongostr was set in arg_parsing)
    try:
        connection = pymongo.Connection(mongostr, safe=True)
        db = connection[db_name]
    except:
        print "can't connect to MongoDB using", mongostr, ". Is it running?"
        sys.exit(1)
        
    if (not check_for_data_integrity()):
        print "Sorry, the data set is not loaded correctly in the posts collection"
        sys.exit(1)

    if (not check_for_fast_blog_home_page()):
        print "Sorry, the query to display the blog home page is too slow."
        sys.exit(1)

    if (not check_for_fast_blog_entry_page()):
        print "Sorry, the query to retrieve a blog post by permalink is too slow."
        sys.exit(1)

    if (not check_for_fast_posts_by_tag_page()):
        print "Sorry, the query to retrieve all posts with a certain tag is too slow"
        sys.exit(1)
    
    # if you are reading this in cleartext, you are violating the honor code.
    # You can still redeem yourself. Get it working and don't submit the validation code until you do.
    # All a man has at the end of the day is his word.
    print "Tests Passed for HW 4.3. Your HW 4.3 validation code is nffhe89hfkdjsbapajsfda89jks"



if __name__ == "__main__":
    main(sys.argv[1:])







