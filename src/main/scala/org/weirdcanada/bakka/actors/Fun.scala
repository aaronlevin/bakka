package org.weirdcanada.bakka.actors

// Scala
import scala.io.Source

// Akka
import akka.actor.{Actor,ActorSystem,Props}

// Bakka
import org.weirdcanada.bakka.bash._
import org.weirdcanada.bakka.messages._
import org.weirdcanada.bakka.util.Line

object Helpers {

  def time[A](msg: String)(f: => A) = {
    val s = System.nanoTime
    val ret = f
    println("time for " + msg + ": " + (System.nanoTime - s)/1e6 + "ms")
    ret
  }

}
object Fun extends App {

  override def main(args: Array[String]) = {

    import Helpers.time

    val system = ActorSystem("MySystem")
    val pattern = """^2""".r.pattern
    def sillyFunc(str: String): String = {
      if( str(0) % 2 == 0)
        str.substring(0,10)
      else
        str.substring(0,11)
    }

    println("create instructions")
    val instructions = List[BashAction](
      LineFuncAction(sillyFunc),
      LineFuncAction((str: String) => "2"+str),
      GrepFilterAction(pattern)
    )

    println("create kernel")
    val kernel = system.actorOf(Props(new KernelActor(instructions)), name = "kernel")

    println("warm up jvm")
    var i = 0
    Source.fromFile(args(0)).getLines.zipWithIndex.foreach { case (line, num) => 
      i = i+1
    }

    val fileIter = Source.fromFile(args(0)).getLines
    println("map over files")
    // Loop over files
    val s1 = System.nanoTime
    fileIter.zipWithIndex.foreach { case (line,num) =>
      kernel ! StartWorkMessage(Line(line,num))
    }
    val s2 = System.nanoTime
    println("Time: " + (s2-s1)/1e6 + " ms")

    val s3 = System.nanoTime
    Source.fromFile(args(0)).getLines.zipWithIndex.foreach { case (line, num) => 
      val line2 = sillyFunc(line)
      val line3 = "2"+line2
      if(pattern.matcher(line3).matches)
        line3
    }
    val s4 = System.nanoTime
    println("Time: " + (s4-s3)/1e6 + " ms")
  }
}
