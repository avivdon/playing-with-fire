# playing-with-fire #
## Final High School Java Project ##

Playing With Fire is based on the known "Playing With Fire" game you can find in the internet in multiple websites (such as https://www.crazygames.com/game/playing-with-fire-2).

### Before describing the game itself, I want to make it clear:
This game is not well-programmed. The algorithms written in are not efficient, there are tons of comments, it's nothing close to any coding standard conventions and it's extremely unreadable.
I wrote this code back in high school so there were so many things I was not aware of (e.g: I didn't bother checking how to implement Animation of objects, so i just made it up on my own using Timer library). 
I wasn't aware of the existance of path-finding recursive algorithms, but I managed to write something close to BFS (and very unefficient) on my own that made the job. 
I didn't know anything about AI, so I just tested the game hundreds of times with different parameters until I couldn't beat the computer anymore.

Therefore, even though the code is terrible, I decided to upload it as is without changing anything (also, I have no clue what is written inside, it's so messed up)

## Game Description
The game itself is a single-player game with 1 up to 3 opponents by the player's choice.
The goal is to be the last survivor in the entire map, or alternatively have the most points until the time runs out (120 seconds)

![initial](https://user-images.githubusercontent.com/74790003/99846453-0171ac80-2b7f-11eb-88d0-77c59263091a.png)

![winner](https://user-images.githubusercontent.com/74790003/99846465-06cef700-2b7f-11eb-81f6-d979e6288df5.png)

Each character has a bomb that can be placed on the ground.
Place a bomb to burn down your opponents or to explode crates to expose the bonuses hidden inside.
Make sure to take as many bonuses as possible, otherwise you won't make it long enough (also, bonuses give points)

Beware! Unlike the original game, the opponents here are EXTREMELY intelligent. They know exactly when to charge and attempt to attack, when to steal bonuses and when to flee away from you. They WILL push you into a dead-end.

### How to win ###
I challenge you to win the game. However, I haven't found anyone who won the opponent yet. I doubt it's possible

![trapped](https://user-images.githubusercontent.com/74790003/99846461-059dca00-2b7f-11eb-958f-23915a2c505c.png)

![computer-is-ambushed](https://user-images.githubusercontent.com/74790003/99846447-fdde2580-2b7e-11eb-9762-7e876ede324b.png)

![complicated](https://user-images.githubusercontent.com/74790003/99847453-c1abc480-2b80-11eb-895c-a052208e6572.png)

![Screenshot 2020-11-20 221850](https://user-images.githubusercontent.com/74790003/99846458-046c9d00-2b7f-11eb-9358-a48fbf5daf18.png)

![instructions](https://user-images.githubusercontent.com/74790003/99846457-02a2d980-2b7f-11eb-8c8c-d5c608540195.png)

