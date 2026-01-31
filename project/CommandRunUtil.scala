import sbt.*

import java.io.IOException
import scala.sys.process.Process

object CommandRunUtil {

  lazy val isWindows: Boolean = System.getProperty("os.name").startsWith("Windows")

  /**
   * Utility method to run a command
   *
   * @param command          The command to execute (eg. "npm")
   * @param args             The command line arguments (eg. "--test --build")
   * @param detached         Whether it should run concurrently (true) or block the next tasks
   * @param workingDirectory The working directory to execute the command
   * @param logger           The logger to use for output
   * @return The process running the given command
   */
  def runCommand(command: String,
                 args: List[String] = List.empty,
                 detached: Boolean = false,
                 workingDirectory: File,
                 logger: Logger): Process = {

    // The full command which will be executed (adapted to run on Unix and Windows)
    val fullCommand: scala.collection.Seq[String] = if (isWindows) {
      "cmd" :: "/c" :: command :: args
    } else {
      command :: args
    }

    logger.info(s"Running command (detached: $detached) '${fullCommand.mkString(" ")}'")

    // Instantiate the process
    val process = Process(fullCommand, workingDirectory)
    // Run the process with the given logger to use as stdout
    val startedProcess = process.run(logger)
    // If the process should NOT run detached and exits with an error, throw an exception
    if (!detached && startedProcess.exitValue() != 0) {
      throw new IOException(s"command '${fullCommand.mkString(" ")}' failed with exit code ${startedProcess.exitValue()}")
    }

    // Return the process
    startedProcess
  }
}