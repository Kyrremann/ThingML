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
package org.fife.rsta.ac.thingml.syntaxtree;

import javax.swing.tree.MutableTreeNode;

import org.fife.rsta.ac.thingml.tree.ThingMLTreeNode;

public class StateMachine {

	String name;
	States states;
	int offset;
	int line;

	public StateMachine(String name, States states, int offset, int line) {
		this.name = name;
		this.states = states;
		this.offset = offset;
		this.line = line;
	}

	public String printAst() {
		String ast = "(StateMachine " + name + "\n\t";
		if (states != null)
			ast += states.printAst();
		ast += ")";
		return ast;
	}

	public MutableTreeNode getTreeNode(ThingMLTreeNode root) {
		ThingMLTreeNode child = new ThingMLTreeNode(name);
		child.setOffset(offset);
		child.setLine(line);
        if(states != null)
                child.add(states.getTreeNode(child));
        return child;
	}
	
	public String toString() {
		return name + " - offset: " + offset;
	}
}