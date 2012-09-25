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

import javax.swing.ImageIcon;

import org.fife.rsta.ac.thingml.tree.ThingMLTreeNode;

public class InitState extends States {

	String name;
	States next;
	int offset;
	int line;

	public InitState(String name, States next, int offset, int line) {
		this.name = name;
		this.next = next;
		this.offset = offset;
		this.line = line;
	}

	public String printAst() {
		String ast = "(Init " + name + ")\n\t";
		if (next != null)
			ast += next.printAst();
		return ast;
	}
	
	public ThingMLTreeNode getTreeNode(ThingMLTreeNode root) {
		ThingMLTreeNode child = new ThingMLTreeNode(name);
		child.setOffset(this.offset);
		child.setLine(line);
		child.setIcon(new ImageIcon(getClass().getResource("startstate.png")));
		if(next != null)
			root.add(next.getTreeNode(root));
		return child;
	}
}