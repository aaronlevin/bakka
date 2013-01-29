package org.weirdcanada.bakka.bash

// bakka
import org.weirdcanada.bakka.messages._

// Java
import java.util.regex.Pattern

// Trait that will transform bash functions
// into the right akka messages
trait BashTransformer {

  private def trReplace(in: Char, replace: Char)(str: String): String = str.replace(in, replace) 
  private def trDelete(in: Char)(str: String): String = str.replace(in.toString, "")

  // emulate the unix pipe
  def commandToAction(bc: BashCommand): BashAction = {
    bc match {
      case Tr(char, replace) => LineFuncAction(trReplace(char, replace) _ )
      case TrD(char) => LineFuncAction(trDelete(char) _ )
      case Exec(func) => LineFuncAction(func)
      case Grep(pattern) => GrepFilterAction(pattern.r.pattern)
      case GrepEx(pattern) => GrepFilterExcludeAction(pattern.r.pattern)
    }
  }
}

// Helper class for syntactic sugar
class ActionCollector(val actions: List[BashAction]) extends BashTransformer {
  // emulate the unix pipe to collect actions
  def |>(bc: BashCommand): ActionCollector = {
    new ActionCollector(actions ::: List(commandToAction(bc)))
  }
}

// Tools to convert bash functions
sealed trait BashCommand 
case class Tr(char: Char, replace: Char) extends BashCommand
case class TrD(char: Char) extends BashCommand
case class Exec(func: String => String) extends BashCommand
case class Grep(pattern: String) extends BashCommand
case class GrepEx(pattern: String) extends BashCommand

// Actions
sealed trait BashAction 
case class GrepFilterAction(pattern: Pattern) extends BashAction
case class GrepFilterExcludeAction(pattern: Pattern) extends BashAction
case class LineFuncAction(func: String => String) extends BashAction
