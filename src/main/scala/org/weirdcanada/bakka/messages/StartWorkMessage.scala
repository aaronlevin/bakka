package org.weirdcanada.bakka.messages

// bakka
import org.weirdcanada.bakka.util.Line

// A message for the Kernel actor to start work
case class StartWorkMessage(line: Line)
