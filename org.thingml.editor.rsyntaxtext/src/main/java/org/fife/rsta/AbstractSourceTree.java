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
package org.fife.rsta;

import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.fife.rsta.GoToMemberAction;
import org.fife.rsta.LanguageSupportFactory;
import org.fife.rsta.SourceTreeNode;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * A tree showing the structure of a source file being edited in an
 * <code>RSyntaxTextArea</code>. This can be used to display an "outline view"
 * of the code, for example.
 * <p>
 * 
 * Concrete implementations typically specialize in displaying code structure
 * for a single language, and are registered to listen to code changes in an
 * <code>RSyntaxTextArea</code> instance by calling
 * {@link #listenTo(RSyntaxTextArea)}. They should then listen to document
 * changes and adjust themselves to reflect the code structure of the current
 * content as best as possible.
 * <p>
 * 
 * You should only add instances of {@link SourceTreeNode} or subclasses to this
 * tree. You should also provide a no-argument constructor if you wish to use
 * your subclass in {@link GoToMemberAction}.
 * 
 * @author Robert Futrell
 * @version 1.0
 * @see SourceTreeNode
 * @see JavaOutlineTree
 * @see JavaScriptOutlineTree
 * @see XmlOutlineTree
 */
public abstract class AbstractSourceTree extends JTree {

	private static final long serialVersionUID = -5280289291263187811L;
	protected RSyntaxTextArea textArea;
	private boolean sorted;
	private String prefix;
	private boolean gotoSelectedElementOnClick;
	private boolean showMajorElementsOnly;

	public AbstractSourceTree() {
		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		gotoSelectedElementOnClick = true;
		showMajorElementsOnly = false;
	}

	/**
	 * Expands all nodes in the specified tree. Subclasses should implement this
	 * in a way logical for the language.
	 */
	public abstract void expandInitialNodes();

	/**
	 * Filters visible tree nodes based on the specified prefix.
	 * 
	 * @param prefix
	 *            The prefix. If this is <code>null</code>, all possible
	 *            children are shown.
	 */
	public void filter(String prefix) {
		if ((prefix == null && this.prefix != null)
				|| (prefix != null && !prefix.equals(this.prefix))) {
			if (prefix != null) {
				prefix = prefix.toLowerCase();
			}
			this.prefix = prefix;
			Object root = getModel().getRoot();
			if (root instanceof SourceTreeNode) {
				((SourceTreeNode) root).filter(prefix);
			}
			((DefaultTreeModel) getModel()).reload();
			expandInitialNodes();
		}
	}

	/**
	 * Returns whether, when a source element is selected in this tree, the same
	 * source element should be selected in the editor.
	 * 
	 * @return Whether to highlight the source element in the editor.
	 * @see #setGotoSelectedElementOnClick(boolean)
	 */
	public boolean getGotoSelectedElementOnClick() {
		return gotoSelectedElementOnClick;
	}

	/**
	 * Returns whether only "major" structural elements are shown in this source
	 * tree. An example of a "minor" element could be a local variable in a
	 * function or method.
	 * 
	 * @return Whether only major elements are shown in this source tree.
	 * @see #setShowMajorElementsOnly(boolean)
	 */
	public boolean getShowMajorElementsOnly() {
		return showMajorElementsOnly;
	}

	/**
	 * Highlights the selected source element in the text editor, if any.
	 * 
	 * @return Whether anything was selected in the tree.
	 */
	public abstract boolean gotoSelectedElement();

	/**
	 * Returns whether the contents of this tree are sorted.
	 * 
	 * @return Whether the contents of this tree are sorted.
	 * @see #setSorted(boolean)
	 */
	public boolean isSorted() {
		return sorted;
	}

	/**
	 * Causes this outline tree to reflect the source code in the specified text
	 * area.
	 * 
	 * @param textArea
	 *            The text area. This should have been registered with the
	 *            {@link LanguageSupportFactory}, and be editing the language
	 *            we're interested in.
	 * @see #uninstall()
	 */
	public abstract void listenTo(RSyntaxTextArea textArea);

	/**
	 * Refreshes what children are visible in the tree. This should be called
	 * manually when updating a source tree with a new root, and is also called
	 * internally on filtering and sorting.
	 */
	public void refresh() {
		Object root = getModel().getRoot();
		if (root instanceof SourceTreeNode) {
			SourceTreeNode node = (SourceTreeNode) root;
			node.refresh();
			((DefaultTreeModel) getModel()).reload();
			expandInitialNodes();
		}
	}

	/**
	 * Selects the first visible tree node matching the filter text.
	 */
	public void selectFirstNodeMatchingFilter() {

		if (prefix == null) {
			return;
		}

		DefaultTreeModel model = (DefaultTreeModel) getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		Enumeration en = root.depthFirstEnumeration();
		String prefixLower = prefix.toLowerCase();

		while (en.hasMoreElements()) {
			SourceTreeNode stn = (SourceTreeNode) en.nextElement();
			JLabel renderer = (JLabel) getCellRenderer()
					.getTreeCellRendererComponent(this, stn, true, true,
							stn.isLeaf(), 0, true);
			String text = renderer.getText();
			if (text != null && text.toLowerCase().startsWith(prefixLower)) {
				setSelectionPath(new TreePath(model.getPathToRoot(stn)));
				return;
			}
		}

	}

	/**
	 * Selects the next visible row.
	 * 
	 * @see #selectPreviousVisibleRow()
	 */
	public void selectNextVisibleRow() {
		int currentRow = getLeadSelectionRow();
		if (++currentRow < getRowCount()) {
			TreePath path = getPathForRow(currentRow);
			setSelectionPath(path);
			scrollPathToVisible(path);
		}
	}

	/**
	 * Selects the previous visible row.
	 * 
	 * @see #selectNextVisibleRow()
	 */
	public void selectPreviousVisibleRow() {
		int currentRow = getLeadSelectionRow();
		if (--currentRow >= 0) {
			TreePath path = getPathForRow(currentRow);
			setSelectionPath(path);
			scrollPathToVisible(path);
		}
	}

	/**
	 * Sets whether, when a source element is selected in this tree, the same
	 * source element should be selected in the editor.
	 * 
	 * @param gotoSelectedElement
	 *            Whether to highlight the source element in the editor.
	 * @see #getGotoSelectedElementOnClick()
	 */
	public void setGotoSelectedElementOnClick(boolean gotoSelectedElement) {
		gotoSelectedElementOnClick = gotoSelectedElement;
	}

	/**
	 * Toggles whether only "major" structural elements should be shown in this
	 * source tree. An example of a "minor" element could be a local variable in
	 * a function or method.
	 * 
	 * @param show
	 *            Whether only major elements are shown in this source tree.
	 * @see #getShowMajorElementsOnly()
	 */
	public void setShowMajorElementsOnly(boolean show) {
		showMajorElementsOnly = show;
	}

	/**
	 * Toggles whether the contents of this tree are sorted.
	 * 
	 * @param sorted
	 *            Whether the contents of this tree are sorted.
	 * @see #isSorted()
	 */
	public void setSorted(boolean sorted) {
		if (this.sorted != sorted) {
			this.sorted = sorted;
			Object root = getModel().getRoot();
			if (root instanceof SourceTreeNode) {
				((SourceTreeNode) root).setSorted(sorted);
			}
			((DefaultTreeModel) getModel()).reload();
			expandInitialNodes();
		}
	}

	/**
	 * Makes this outline tree stop listening to its current text area.
	 * 
	 * @see #listenTo(RSyntaxTextArea)
	 */
	public abstract void uninstall();

}