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
package org.fife.rsta.ac.java.tree;

import javax.swing.Icon;

import org.fife.rsta.ac.SourceTreeNode;
import org.fife.rsta.ac.java.IconFactory;
import org.fife.rsta.ac.java.rjc.ast.ASTNode;


/**
 * Base class for nodes in the Java outline tree.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class JavaTreeNode extends SourceTreeNode {

	private ASTNode astNode;
	private Icon icon;

	protected static final int PRIORITY_TYPE = 0;
	protected static final int PRIORITY_FIELD = 1;
	protected static final int PRIORITY_CONSTRUCTOR = 2;
	protected static final int PRIORITY_METHOD = 3;
	protected static final int PRIORITY_LOCAL_VAR = 4;
	protected static final int PRIORITY_BOOST_STATIC = -16;


	protected JavaTreeNode(ASTNode node) {
		this(node, null);
	}


	protected JavaTreeNode(ASTNode node, String iconName) {
		this(node, iconName, false);
	}


	protected JavaTreeNode(ASTNode node, String iconName, boolean sorted) {
		super(node, sorted);
		this.astNode = node;
		if (iconName!=null) {
			setIcon(IconFactory.get().getIcon(iconName));
		}
	}


	public JavaTreeNode(String text, String iconName) {
		this(text, iconName, false);
	}


	public JavaTreeNode(String text, String iconName, boolean sorted) {
		super(text, sorted);
		if (iconName!=null) {
			this.icon = IconFactory.get().getIcon(iconName);
		}
	}


	/**
	 * Overridden to compare tree text without HTML.
	 */
	public int compareTo(Object obj) {
		int res = -1;
		if (obj instanceof JavaTreeNode) {
			JavaTreeNode jtn2 = (JavaTreeNode)obj;
			res = getSortPriority() - jtn2.getSortPriority();
			if (res==0 && ((SourceTreeNode)getParent()).isSorted()) {
				res = getText(false).compareToIgnoreCase(jtn2.getText(false));
			}
		}
		return res;
	}


	public ASTNode getASTNode() {
		return astNode;
	}


	public Icon getIcon() {
		return icon;
	}


	public String getText(boolean selected) {
		Object obj = getUserObject();
		return obj!=null ? obj.toString() : null;
	}


	public void setIcon(Icon icon) {
		this.icon = icon;
	}


	/**
	 * Overridden to return the same thing as <tt>getText(false)</tt>, so
	 * we look nice with <tt>ToolTipTree</tt>s.
	 *
	 * @return A string representation of this tree node.
	 */
	public String toString() {
		return getText(false);
	}


}