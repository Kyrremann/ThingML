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
package org.fife.rsta.ac.c;

import javax.swing.ListCellRenderer;

import org.fife.rsta.ac.AbstractLanguageSupport;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;


/**
 * Language support for C.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class CLanguageSupport extends AbstractLanguageSupport {

	/**
	 * The completion provider, shared amongst all text areas editing C.
	 */
	private CCompletionProvider provider;


	/**
	 * Constructor.
	 */
	public CLanguageSupport() {
		setParameterAssistanceEnabled(true);
		setShowDescWindow(true);
	}


	/**
	 * {@inheritDoc}
	 */
	protected ListCellRenderer createDefaultCompletionCellRenderer() {
		return new CCellRenderer();
	}


	private CCompletionProvider getProvider() {
		if (provider==null) {
			provider = new CCompletionProvider();
		}
		return provider;
	}


	/**
	 * {@inheritDoc}
	 */
	public void install(RSyntaxTextArea textArea) {

		CCompletionProvider provider = getProvider();
		AutoCompletion ac = createAutoCompletion(provider);
		ac.install(textArea);
		installImpl(textArea, ac);

		textArea.setToolTipSupplier(provider);

	}


	/**
	 * {@inheritDoc}
	 */
	public void uninstall(RSyntaxTextArea textArea) {
		uninstallImpl(textArea);
	}


}