I thought I could adapt the JSON reader that is used in the Goblin Traders Mod to work for Villager Trades, 
but for the moment, I do not understand Java well enough to make that happen. The reason it isn't working, I believe, is
that this JSON reader is set up to function with entity types that do not have a hard coded built-in pre-existing trades list.
For this reason, it doesn't need to worry about the format the items list is in and I don't know yet how to convert that
list this reader uses into the type of list the Villager Trades listing uses. I believe at this point, the best thing to
do would be to start from scratch and write a JSON reader specifically for the Villager Trades. This is assuming that I 
can come to understand the Java programming language well enough to accomplish this. I'm leaving all this here as an archive
for my own research purposes with the clear note that IT DOES NOT WORK.

The Goblin Traders Mod is under the GNU LESSER GENERAL PUBLIC LICENSE, to the best of my understanding, which you can view here:
https://github.com/MrCrayfish/GoblinTraders/blob/1.19.X/LICENSE.txt

Again, since it doesn't work for what I need to be doing with this project, it only remains here for learning purposes 
and I will continue to give MrCrayfish credit for his code for helping me learn and understand how things work since he
has been kind enough to share his source code. If nothing else, it's because of all this I was able to understand datagen
and make that work elsewhere for other projects and for this, I will be forever grateful.