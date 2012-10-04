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
	private int line, column, charStart, charEnd;
	
	public ThingMLTreeNode(String name) {
		super(name);
		this.setName(name);
	}
	
	public ThingMLTreeNode(String name, int offset) {
		super(name);
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public Icon getIcon() {
		return icon;
	}

	public int getLine() {
		return line;
	}

	/**
	 * Where on the current line the word starts
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Where the word start based on the whole text
	 * @return the charStart
	 */
	public int getCharStart() {
		return charStart;
	}

	/**
	 * Where the word ends based on the whole text
	 * @see getCharStart() 
	 * @return the charEnd
	 */
	public int getCharEnd() {
		return charEnd;
	}
	
	/**
	 * @return the length of the name
	 */
	public int getLength() {
		return name.length();
	}

	/**
	 * @param charEnd the charEnd to set
	 */
	public void setCharEnd(int charEnd) {
		this.charEnd = charEnd;
	}

	/**
	 * @param charStart the charStart to set
	 */
	public void setCharStart(int charStart) {
		this.charStart = charStart;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public void setName(String name) {
		this.name = name;
	}
}
