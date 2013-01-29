package org.weirdcanada.bakka.messages

// bakka
import org.weirdcanada.bakka.util.Line

// Java
import java.util.regex.Pattern

case class GrepFilterMessage(line: Line, pattern: Pattern, state: Int)
case class GrepFilterExcludeMessage(line: Line, pattern: Pattern, state: Int)

case class LineFuncMessage(line: Line, func: String => String, state: Int)
