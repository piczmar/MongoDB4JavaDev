Final: Question 7

You have been tasked to cleanup a photosharing database. The database consists of two collections, albums, and images. Every image is supposed to be in an album, but there are orphan images that appear in no album. Here are some example documents (not from the collections you will be downloading). 

> db.albums.findOne()
{
	"_id" : 67
	"images" : [
		4745,
		7651,
		15247,
		17517,
		17853,
		20529,
		22640,
		27299,
		27997,
		32930,
		35591,
		48969,
		52901,
		57320,
		96342,
		99705
	]
}

> db.images.findOne()
{ "_id" : 99705, "height" : 480, "width" : 640 }
> 
From the above, you can conclude that the image with _id = 99705 is in album 67. It is not an orphan. 

Your task is to write a program to remove every image from the images collection that appears in no album. Or put another way, if an image does not appear in at least one album, it's an orphan and should be removed from the images collection.

Start by using mongoimport to import your albums.json and images.json collections. (Did you notice the links in the previous sentence?) 
When you are done removing the orphan images from the collection, there should be 90,017 documents in the images collection. To prove you did it correctly, what are the total number of images with the tag 'sunrises" after the removal of orphans? As as a sanity check, there are 50,054 images that are tagged 'sunrises' before you remove the images. 
Hint: you might consider creating an index or two or your program will take a long time to run. 


43,434

34,204

49,123

45,044

50,054