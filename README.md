# Cricket-Game Flowgit
* First we’ll Initialize Teams and their players
* Then We’ll ask whether it’s a single match or series.
* Ask for no. of games will be played in the series.(if single match N =1)
* type of games will be played in series (T20/ODI). initialize total over accordingly
* Play N matches
    * Start Match -
        * Toss (Assign Bowling/ Batting)
        * Start first Inning(Batting, Bowling)
            * Call two batsmen from Batting team(Striker and Non-striker) and call for Bowler from Bowling Team.
            * start bowling(until all over ends/ all wickets fall down)
                * Generate result of ball from random func.
                * Update ball played by Striker/ bowled by bowler.
                * if run scored
                    * update score of team and individual players(Batsman and Bowler)
                    * if run is odd - Change strike.
                * if wicket
                    * update wicket of team/bowler
                    * Dismiss Striker
                    * call for next batsman (valid batsman) - Assign Striker
                * if last ball of over
                    * Change Strike
                    * call for next bowler (valid bowler)
        * Swap Teams ( Swap(Batting, Bowling))
        * Start second Inning - same first above
    * Increment the count of wins of winning Team
* After N matches which team has(Wins>n/2) will be the series winner.


valid Bowler - Haven’t bowled last over and have bowled less than N/5 overs.
valid Batsman - who haven’t played.
![](staticfiles/ClassDiagram.png)

This is the DB Diagram/Design , I have used in my project.

![](staticfiles/DbDiagram.png)

This swagger image shows the API Documentation of our application to perform multiple tasks.

![](staticfiles/api_documentation.png)

Gatling Results:

for baseURL/matches/{matchId}/player-id/{playerId}

![](staticfiles/get_player.png)

for baseURL/matches/{matchId}

![](staticfiles/get_match.png)

