package org.weirdcanada.bakka.actors

// messages
import org.weirdcanada.bakka.messages._
import org.weirdcanada.bakka.bash._

// akka
import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging

/* The KernelActor is responsible for dispatching Bash
 * messages to the right bash actor. It maintains an ordered
 * list of instructions (List[BashAction]), processing each 
 * line as it comes.
 */
class KernelActor(instructions: List[BashAction]) extends Actor {

  val log = Logging(context.system, this)
  val totalInstructions = instructions.length

  val lineFuncActor = context.actorOf(Props[LineFuncActor], name = "funcActor")
  val grepActor = context.actorOf(Props[GrepActor], name = "grepActor")

  def receive = {
    case WorkCompletedMessage(line, state) if state < totalInstructions => 
      instructions(state) match { 
        case LineFuncAction(func) => lineFuncActor ! LineFuncMessage(line, func, state)
        case GrepFilterAction(pattern) => grepActor ! GrepFilterMessage(line, pattern, state)
        case GrepFilterExcludeAction(pattern) => grepActor ! GrepFilterExcludeMessage(line, pattern, state)
      }
    case StartWorkMessage(line) => 
      self ! WorkCompletedMessage(line, 0)
    case _ => log.info("received unknown message")
  }
}
