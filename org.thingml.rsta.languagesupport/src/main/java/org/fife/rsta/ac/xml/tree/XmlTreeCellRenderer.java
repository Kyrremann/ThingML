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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.fife.ui.rsyntaxtextarea.RSyntaxUtilities;


/**
 * Renders nodes in the XML tree.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class XmlTreeCellRenderer extends DefaultTreeCellRenderer {

	private Icon elemIcon;
	private String elem;
	private String attr;
	private boolean selected;

	private static final XmlTreeCellUI UI = new XmlTreeCellUI();
	private static final Color ATTR_COLOR = new Color(0x808080);


	public XmlTreeCellRenderer() {
		URL url = getClass().getResource("tag.png");
		if (url!=null) { // Always true
			elemIcon = new ImageIcon(url);
		}
		setUI(UI);
	}


	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean focused) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded,
											leaf, row, focused);
		this.selected = sel;
		if (value instanceof XmlTreeNode) {
			// Don't modify setText() since it determines width of tree node.
			XmlTreeNode node = (XmlTreeNode)value;
			elem = node.getElement();
			attr = node.getMainAttr();
		}
		else {
			elem = attr = null;
		}
		setIcon(elemIcon);
		return this;
	}


	public void updateUI() {
		setUI(UI);
	}


	/**
	 * Custom UI for our renderer.  This is basically a performance hack to
	 * avoid using HTML for our rendering.  Swing's HTML rendering engine is
	 * very slow, making tree views many thousands of nodes large using HTML
	 * very slow for expand operations (our expandInitialNodes() method).  This
	 * is caused by calls to get the preferred size of each HTML view.  A
	 * "plain text" renderer that can paint the different colors itself is
	 * much faster (~ 4x faster), but still doesn't eliminate the issue for
	 * huge trees.
	 */
	private static class XmlTreeCellUI extends BasicLabelUI {

		protected void paintEnabledText(JLabel l, Graphics g, String s, 
				int textX, int textY) {
			XmlTreeCellRenderer r = (XmlTreeCellRenderer)l;
			Graphics2D g2d = (Graphics2D)g.create();
			g2d.addRenderingHints(RSyntaxUtilities.getDesktopAntiAliasHints());
			g2d.drawString(r.elem, textX, textY);
			if (r.attr!=null) {
				textX += g2d.getFontMetrics().stringWidth(r.elem + " ");
				if (!r.selected) {
					g2d.setColor(ATTR_COLOR);
				}
				g2d.drawString(r.attr, textX, textY);
			}
			g2d.dispose();
		}

	}

   
}