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

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;


/**
 * Renderer for the AST tree in the UI.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class AstTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;


	public Component getTreeCellRendererComponent(JTree tree, Object value,
							boolean sel, boolean expanded, boolean leaf,
							int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
											row, hasFocus);
		if (value instanceof JavaTreeNode) { // Should always be true
			JavaTreeNode node = (JavaTreeNode)value;
			setText(node.getText(sel));
			setIcon(node.getIcon());
		}
		return this;
	}


}