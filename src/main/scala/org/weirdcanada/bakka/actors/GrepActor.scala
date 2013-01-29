package org.weirdcanada.bakka.actors

// bakka
import org.weirdcanada.bakka.messages._

// akka
import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging

class GrepActor extends Actor {

  val log = Logging(context.system, this)

  def receive = {
    case GrepFilterMessage(inLine, pattern, state) => 
      if( pattern.matcher(inLine.line).matches )
        sender ! WorkCompletedMessage(inLine, state + 1)
    case GrepFilterExcludeMessage(inLine, pattern, state) =>
      if( ! pattern.matcher(inLine.line).matches )
        sender ! WorkCompletedMessage(inLine, state + 1)
  }

}


