package org.weirdcanada.bakka.actors

// bakka
import org.weirdcanada.bakka.messages._
import org.weirdcanada.bakka.util.Line

// akka
import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging

class LineFuncActor extends Actor {

  val log = Logging(context.system, this)

  def receive = {
    // Receive a message and apply a function to it
    // line is of type Line
    case LineFuncMessage(lineToBeProcessed, func, state) => 
      sender ! WorkCompletedMessage(lineToBeProcessed.copy(line = func(lineToBeProcessed.line)), state + 1)
  }

}


