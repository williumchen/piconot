# Building and running Piconot
To build and run our Piconot bot, in the top-level piconot directory, run the following command:

 `sbt "run-main piconot.external.Piconot MAP_TXT BOT_PROGRAM"`
 
where MAP_TEXT is the name of the map layout text file for our Piconot to solve and BOT_PROGRAM is the name of the Piconot bot program written in our external DSL
 
 Running:
 
 `sbt "run-main piconot.external.Piconot resources/empty.txt src/main/scala/piconot/external/Empty.bot"`
 
 will run the rules specified in Empty.bot to solve the empty maze.
