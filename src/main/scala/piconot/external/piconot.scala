package piconot.external

import java.io.FileNotFoundException
import picolib.maze.Maze
import picolib.semantics._
import piconot.external._
import piconot.external.semantics._
import scalafx.application.JFXApp

object Piconot extends JFXApp {

  val args = parameters.raw

  // Error handling: did the user pass two arguments?
  if (args.length != 2) {
    println(usage)
    sys.exit(1)
  }

  // parse the maze file
  val mazeFileName = args(0)
  val maze = Maze(getFileLines(mazeFileName))

  // parse the program file
  val programFilename = args(1)
  val program = PiconotParser(getFileContents(programFilename))

  // process the results of parsing
  program match {
    // Error handling: syntax errors
    case e: PiconotParser.NoSuccess ⇒ println(e)

    // if parsing succeeded...
    case PiconotParser.Success(t, _) ⇒ {
      val bot = new Picobot(maze, program.get) with TextDisplay with GUIDisplay
      checkErrors(bot)
      bot.run
    }
  }

  /** A string that describes how to use the program **/
  def usage = "usage: piconot.external.Piconot <maze-file> <rules-file>"

  /**
   * Given a filename, get a list of the lines in the file
   */
  def getFileLines(filename: String): List[String] =
    try {
      io.Source.fromFile(filename).getLines().toList
    } catch { // Error handling: non-existent file
      case e: FileNotFoundException ⇒ { println(e.getMessage()); sys.exit(1) }
    }

  /**
   * Given a filename, get the contents of the file
   */
  def getFileContents(filename: String): String =
    try {
      io.Source.fromFile(filename).mkString
    } catch { // Error handling: non-existent file
      case e: FileNotFoundException ⇒ { println(e.getMessage()); sys.exit(1) }
    }

  /**
   * Check for errors. If there are any print them and exit
   */
  def checkErrors(bot: Picobot): Unit = {
//    object checker extends DefaultChecker[Picobot]
//      with allBlocked
//    val errors = checker.check(bot)
//    if (!errors.isEmpty) {
//      errors foreach println
//      sys.exit(1)
//    }
  }
}