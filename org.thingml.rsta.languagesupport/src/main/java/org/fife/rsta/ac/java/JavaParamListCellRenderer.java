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
package org.fife.rsta.ac.java;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JList;

import org.fife.ui.autocomplete.Completion;


/**
 * The renderer used for parameter completions (for methods) in Java.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class JavaParamListCellRenderer extends JavaCellRenderer {


	public JavaParamListCellRenderer() {
		// Param completions don't display type info, etc., because all
		// completions for a single parameter have the same type (or subclass
		// that type).
		setSimpleText(true);
	}


	/**
	 * Returns the preferred size of a particular cell.  Note that the parent
	 * class {@link JavaCellRenderer} doesn't override this method, because
	 * it doesn't use the cells to dictate the preferred size of the list, due
	 * to the large number of completions it shows at a time.
	 */
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		d.width += 32; // Looks better when less scrunched.
		return d;
	}


	/**
	 * Returns the renderer.
	 *
	 * @param list The list of choices being rendered.
	 * @param value The {@link Completion} being rendered.
	 * @param index The index into <code>list</code> being rendered.
	 * @param selected Whether the item is selected.
	 * @param hasFocus Whether the item has focus.
	 */
	public Component getListCellRendererComponent(JList list, Object value,
						int index, boolean selected, boolean hasFocus) {
		super.getListCellRendererComponent(list, value, index, selected,
											hasFocus);
		JavaSourceCompletion ajsc = (JavaSourceCompletion)value;
		setIcon(ajsc.getIcon());
		return this;
	}


}