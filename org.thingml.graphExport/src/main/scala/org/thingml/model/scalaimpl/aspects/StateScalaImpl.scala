/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingml.model.scalaimpl.aspects

import org.sintef.thingml._
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import scala.collection.JavaConversions._
import org.sintef.thingml.constraints.ThingMLHelpers
import java.util.{Hashtable, ArrayList}

/**
 * Created by IntelliJ IDEA.
 * User: ffl
 * Date: 04.07.11
 * Time: 10:42
 * To change this template use File | Settings | File Templates.
 */

case class StateScalaImpl (self : State) {

  def allContainingStates: ArrayList[State] = {
    return ThingMLHelpers.allContainingStates(self)
  }

  def allProperties: ArrayList[Property] = {
    return ThingMLHelpers.allProperties(self)
  }

  def allValidTargetStates: ArrayList[State] = {
    return ThingMLHelpers.allValidTargetStates(self)
  }

  def allStates : ArrayList[State] = {
    self match {
      case cs : CompositeState => cs.allContainedStates
      case _ => {
        val result = new ArrayList[State]()
        result.add(self)
        result
      }
    }
  }

  def canHandle(p : Port, m : Message) = {
    val handlers = allMessageHandlers
    val result = if (!handlers.containsKey(p)) false
    else handlers.get(p).containsKey(m)
    println("     ** State " + self.getName + " canHandle(" + p.getName + ", " + m.getName + ") = " + result)
    result
  }

  def allHandlers(p : Port, m : Message) : ArrayList[Handler] = {
    val handlers = allMessageHandlers
    if (!handlers.containsKey(p) || !handlers.get(p).containsKey(m)) new ArrayList[Handler]()
    else handlers.get(p).get(m)
  }

  def allMessageHandlers() : Hashtable[Port, Hashtable[Message, ArrayList[Handler]]] = {
    var result :  Hashtable[Port, Hashtable[Message, ArrayList[Handler]]] = new  Hashtable[Port, Hashtable[Message, ArrayList[Handler]]]()
    allStates.foreach { s =>
      //println("Processisng state " + s.getName)
      s.getOutgoing.union(s.getInternal)foreach{ t =>
        //println("  Processisng handler " + t + " Event = " + t.getEvent)
        t.getEvent.foreach{e =>
          e match {
            case rm : ReceiveMessage if (rm.getMessage != null && rm.getPort != null) => {
              //println("    found handler for " + rm.getPort.getName + "?" + rm.getMessage.getName)
              var phdlrs = result.get(rm.getPort)
              if (phdlrs == null) {
                phdlrs = new Hashtable[Message, ArrayList[Handler]]()
                result.put(rm.getPort, phdlrs)
              }
              var hdlrs = phdlrs.get(rm.getMessage)
              if (hdlrs == null) {
                hdlrs = new ArrayList[Handler]
                phdlrs.put(rm.getMessage, hdlrs)
              }
              hdlrs.add(t)
            }
            case _ => { /* Not a message */ }
          }
        }
      }
    }
    result
  }
}