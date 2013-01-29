package org.weirdcanada.bakka.program

// bakka
import org.weirdcanada.bakka.bash._
import org.weirdcanada.bakka.actors._

// akka
import akka.actor.{Actor,ActorSystem,Props, ActorRef}

/** various helpers to start work and initialize programs
 */
object ImplicitTransforms extends BashTransformer {

  // Implicit transformation from Actions to Action Collectors
  implicit def bashCommandToActionCollector(bc: BashCommand): ActionCollector = {
    new ActionCollector(List(commandToAction(bc)))
  }

  // Implicit from ActionCollector to list of actions
  implicit def actionCollectorToActionList(ac: ActionCollector): List[BashAction] = ac.actions
}

trait ActorSystemEngine {

  def startSystem(name: String): ActorSystem = ActorSystem(name)

  def getKernelActor(system: ActorSystem, actions: List[BashAction], name: String): ActorRef =
    system.actorOf(Props(new KernelActor(actions)), name = name)

}

/* Cat a file through the system. Ideal usage:
 * Cat("cool_dude.txt") {
 *   ( TrD('x')
 *   |> Tr('d','x')
 *   |> Grep("^aaron'\slevin")
 *   )
 * }
 */
object Cat extends ActorSystemEngine {

  lazy val system = startSystem("cat")

  def apply(fileName: String)(actions: List[BashAction]) = {
    val kernel = (system, actions, "echo")
  }
}

object Echo extends ActorSystemEngine {
  
  lazy val system = startSystem("echo")

  def apply(str: String)(actions: List[BashAction]) = {
    val kernel = (system, actions, "echo")
  }

}

object Prog extends ActorSystemEngine {

  lazy val system = startSystem("prog")

  def apply(actions: List[BashAction]) = {
    val kernel = (system, actions, "program")
  }

}

