package org.weirdcanada.bakka.messages

import org.weirdcanada.bakka.util.Line

case class WorkCompletedMessage(line: Line, state: Int)
