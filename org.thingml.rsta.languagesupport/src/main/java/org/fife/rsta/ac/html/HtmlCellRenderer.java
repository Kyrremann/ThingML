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
package org.fife.rsta.ac.html;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;

import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionCellRenderer;
import org.fife.ui.autocomplete.EmptyIcon;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.MarkupTagCompletion;
import org.fife.ui.autocomplete.VariableCompletion;


/**
 * The cell renderer used for HTML.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class HtmlCellRenderer extends CompletionCellRenderer {

	private Icon tagIcon;
	private Icon attrIcon;
	private Icon emptyIcon;


	/**
	 * Constructor.
	 */
	public HtmlCellRenderer() {
		tagIcon = getIcon("tag.png");
		attrIcon = getIcon("attribute.png");
		emptyIcon = new EmptyIcon(16);
	}


	/**
	 * Returns an icon.
	 *
	 * @param resource The icon to retrieve.  This should either be a file,
	 *        or a resource loadable by the current ClassLoader.
	 * @return The icon.
	 */
	private Icon getIcon(String resource) {
		URL url = getClass().getResource(resource);
		if (url==null) {
			File file = new File(resource);
			try {
				url = file.toURI().toURL();
			} catch (MalformedURLException mue) {
				mue.printStackTrace(); // Never happens
			}
		}
		return url!=null ? new ImageIcon(url) : null;
	}


	/**
	 * {@inheritDoc}
	 */
	protected void prepareForFunctionCompletion(JList list,
			FunctionCompletion fc, int index, boolean selected,
			boolean hasFocus) {
		super.prepareForFunctionCompletion(list, fc, index, selected,
										hasFocus);
		setIcon(emptyIcon);
	}


	/**
	 * {@inheritDoc}
	 */
	protected void prepareForMarkupTagCompletion(JList list,
		MarkupTagCompletion c, int index, boolean selected, boolean hasFocus) {
		super.prepareForMarkupTagCompletion(list, c, index, selected, hasFocus);
		setIcon(tagIcon);
	}


	/**
	 * {@inheritDoc}
	 */
	protected void prepareForOtherCompletion(JList list,
			Completion c, int index, boolean selected,
			boolean hasFocus) {
		super.prepareForOtherCompletion(list, c, index, selected, hasFocus);
		if (c instanceof AttributeCompletion) {
			setIcon(attrIcon);
		}
		else {
			setIcon(emptyIcon);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	protected void prepareForVariableCompletion(JList list,
			VariableCompletion vc, int index, boolean selected,
			boolean hasFocus) {
		super.prepareForVariableCompletion(list, vc, index, selected,
										hasFocus);
		setIcon(emptyIcon);
	}


}