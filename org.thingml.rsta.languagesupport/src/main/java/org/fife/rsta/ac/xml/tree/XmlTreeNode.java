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
package org.fife.rsta.ac.xml.tree;

import javax.swing.text.Position;

import org.fife.rsta.ac.SourceTreeNode;


/**
 * The tree node in <code>XmlOutlineTree</code>s.
 *
 * @author Robert Futrell
 * @version 1.0
 * @see XmlOutlineTree
 */
public class XmlTreeNode extends SourceTreeNode {

	private String mainAttr;
	private Position offset;
	private Position endOffset;


	public XmlTreeNode(String name) {
		super(name);
	}


	public boolean containsOffset(int offs) {
		return offset!=null && endOffset!=null &&
				offs>=offset.getOffset() && offs<=endOffset.getOffset();
	}


	public String getElement() {
		return (String)getUserObject();
	}


	public int getEndOffset() {
		return endOffset!=null ? endOffset.getOffset() : Integer.MAX_VALUE;
	}


	public String getMainAttr() {
		return mainAttr;
	}


	public int getStartOffset() {
		return offset!=null ? offset.getOffset() : -1;
	}


	public void setEndOffset(Position pos) {
		this.endOffset = pos;
	}


	public void setMainAttribute(String attr) {
		this.mainAttr = attr;
	}


	public void setStartOffset(Position pos) {
		this.offset = pos;
	}


	/**
	 * Returns a string representation of this tree node.
	 *
	 * @return A string representation of this tree node.
	 */
	public String toString() {
		String text = getElement();
		if (mainAttr!=null) {
			text += " " + mainAttr;
		}
		return text;
	}


}