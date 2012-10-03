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
package org.fife.rsta.ac.thingml.tree;

import javax.swing.Icon;

import org.fife.rsta.ac.SourceTreeNode;

public class ThingMLTreeNode extends SourceTreeNode {

	private static final long serialVersionUID = -8551575054471449261L;
	private String name;
	private Icon icon;
	private int offset;
	private int line;
    /**
     * only to be used if you ever need to know where the weight is place...
     */
    public int weightOffset;
	
	public ThingMLTreeNode(String name) {
		super(name);
		this.name = name;
	}
	
	public ThingMLTreeNode(String name, int offset) {
		super(name);
		this.name = name;
		this.offset = offset;
	}
	
	public String toString() {
		return name;
	}
	
	public int getLength() {
		return name.length();
	}

	public Icon getIcon() {
		return icon;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public int getLine() {
		return line;
	}
	
	public void setLine(int line) {
		this.line = line;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}
}
