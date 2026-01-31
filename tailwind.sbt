import com.typesafe.sbt.web.Import.WebKeys.webTarget
import sbt.io.Path.relativeTo

val tailwindSourceTask = taskKey[Seq[File]]("Tailwind CSS source file task")
tailwindSourceTask := {
  val logger = streams.value.log
  logger.info("running tailwind source task")
  val start = System.nanoTime()
  val sourceDir = (Assets / sourceDirectory).value / "tailwind"
  val workingDir = baseDirectory.value
  println("\n\nHERE:!!!" + (Assets / sourceDirectory).value + "\n\n")

  val targetDir = webTarget.value / "tailwind-plugin"
  val sources = sourceDir ** "*.css"
  val mappings = sources pair relativeTo(sourceDir)
  val compiled = mappings map { case (file, path) => file -> (Assets / resourceManaged).value / path }

  // note: add support for other OS and non x64 here,
  // the 'cmd' variable should simply resolve to the name
  // of the binary to use
  val cmd = CommandRunUtil.isWindows match {
    case true => "tailwindcss-windows-x64.exe"
    case false => "tailwindcss-macos-x64"
  }
  compiled.foreach { case (f, p) =>
    val args = List("-i", f.absolutePath, "-o", p.absolutePath, "--minify")
    CommandRunUtil.runCommand(s"./$cmd", args, detached = false, workingDir, logger)
  }

  val result = compiled map (_._2)
  val took = (System.nanoTime() - start) / 1000 / 1000
  logger.info(s"finished tailwind source task (${took}ms)")
  logger.debug(s"result: $result")
  result
}
Assets / sourceGenerators += tailwindSourceTask.taskValue
