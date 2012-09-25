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
package org.fife.rsta.ac.js;

import javax.swing.Icon;
import javax.swing.JList;

import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionCellRenderer;
import org.fife.ui.autocomplete.EmptyIcon;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.TemplateCompletion;
import org.fife.ui.autocomplete.VariableCompletion;


/**
 * The cell renderer used for JavaScript completion choices.
 * 
 * @author Robert Futrell
 * @version 1.0
 */
public class JavaScriptCellRenderer extends CompletionCellRenderer {

	private Icon emptyIcon;


	/**
	 * Constructor.
	 */
	public JavaScriptCellRenderer() {
		emptyIcon = new EmptyIcon(16);
	}


	/**
	 * {@inheritDoc}
	 */
	protected void prepareForOtherCompletion(JList list, Completion c,
			int index, boolean selected, boolean hasFocus) {
		super.prepareForOtherCompletion(list, c, index, selected, hasFocus);
		setIconWithDefault(c, emptyIcon);
	}


	/**
	 * {@inheritDoc}
	 */
	protected void prepareForTemplateCompletion(JList list,
		TemplateCompletion tc, int index, boolean selected, boolean hasFocus) {
		super.prepareForTemplateCompletion(list, tc, index, selected, hasFocus);
		setIconWithDefault(tc, IconFactory.getIcon(IconFactory.TEMPLATE_ICON));
	}


	/**
	 * {@inheritDoc}
	 */
	protected void prepareForVariableCompletion(JList list,
			VariableCompletion vc, int index, boolean selected, boolean hasFocus) {
		super.prepareForVariableCompletion(list, vc, index, selected, hasFocus);
		setIconWithDefault(vc, IconFactory.getIcon(IconFactory.LOCAL_VARIABLE_ICON));
	}


	/**
	 * {@inheritDoc}
	 */
	protected void prepareForFunctionCompletion(JList list,
			FunctionCompletion fc, int index, boolean selected, boolean hasFocus) {
		super.prepareForFunctionCompletion(list, fc, index, selected, hasFocus);
		setIconWithDefault(fc, IconFactory.getIcon(IconFactory.DEFAULT_FUNCTION_ICON));
	}


}