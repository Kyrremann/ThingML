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

import org.fife.rsta.ac.thingml.tree.ThingMLTreeNode;

public class State extends States {

	String name;
	Transition transitions;
	States next;
	int offset;
	int line;

	public State(String name, Transition transitions, States next, int offset, int line) {
		this.name = name;
		this.transitions = transitions;
		this.next = next;
		this.offset = offset;
		this.line = line;
	}

	public String printAst() {
		String ast = "(State " + name + "\n\t";
		if (transitions != null) {
			ast += "\t";
			ast += transitions.printAst();
		}
		ast += ")\n";
		if (next != null) {
			ast += "\t";
			ast += next.printAst();
		}
		return ast;
	}

	public ThingMLTreeNode getTreeNode(ThingMLTreeNode root) {
		ThingMLTreeNode child = new ThingMLTreeNode(name);
		child.setOffset(this.offset);
		child.setLine(this.line);
		if(transitions != null)
			child.add(transitions.getTreeNode(child));
		if(next != null)
			root.add(next.getTreeNode(root));
		return child;
	}
}