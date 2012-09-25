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
package org.fife.rsta.ac.js.tree;

import javax.swing.Icon;
import javax.swing.text.Position;

import org.fife.rsta.ac.SourceTreeNode;
import org.mozilla.javascript.ast.AstNode;


/**
 * Tree node for JavaScript outline trees.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class JavaScriptTreeNode extends SourceTreeNode {

	/**
	 * The location of this source element in the document.
	 */
	private Position pos;

	/**
	 * The text to display in the tree.
	 */
	private String text;

	/**
	 * The icon this node displays in the tree.
	 */
	private Icon icon;


	public JavaScriptTreeNode(AstNode userObject) {
		super(userObject);
	}


	public JavaScriptTreeNode(AstNode userObject, boolean sorted) {
		super(userObject, sorted);
	}


	public Icon getIcon() {
		return icon;
	}


	/**
	 * Returns the length in the document of this source element.
	 *
	 * @return The length of this element.
	 * @see #getOffset()
	 */
	public int getLength() {
		return ((AstNode)getUserObject()).getLength();
	}


	/**
	 * Returns the offset into the document of this source element.  This
	 * offset tracks modifications in the document and has been updated
	 * accordingly.
	 *
	 * @return The offset.
	 * @see #getLength()
	 */
	public int getOffset() {
		return pos.getOffset();
	}


	public String getText(boolean selected) {
		return text;
	}


	public void setIcon(Icon icon) {
		this.icon = icon;
	}


	/**
	 * Sets the absolute offset of this element in the document.
	 *
	 * @param offs The offset.
	 * @see #getOffset()
	 */
	public void setOffset(Position offs) {
		this.pos = offs;
	}


	/**
	 * Sets the text to display in the tree for this node.
	 *
	 * @param text The text to display.
	 */
	public void setText(String text) {
		this.text = text;
	}


	/**
	 * Overridden to return the textual representation displayed in the tree
	 * view.
	 *
	 * @return The text of this tree node.
	 */
	public String toString() {
		return getText(false);
	}


}