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
package org.fife.rsta.thingml;

import org.fife.rsta.AbstractLanguageSupport;
import org.fife.rsta.thingml.ThingMLParser;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
 
public class ThingMLLanguageSupport extends AbstractLanguageSupport {

	private boolean showSyntaxErrors;

	public ThingMLLanguageSupport() {
		setAutoCompleteEnabled(true);
		//setParameterAssistanceEnabled(true);
		//setShowDescWindow(false);
		setShowSyntaxErrors(true);
	}

	public void install(RSyntaxTextArea textArea) {
		ThingMLParser mlParser = new ThingMLParser(this);
		textArea.addParser(mlParser);
		textArea.putClientProperty(PROPERTY_LANGUAGE_PARSER, mlParser);
	}

	public void uninstall(RSyntaxTextArea textArea) {
		uninstallImpl(textArea);

		ThingMLParser mlParser = getParser(textArea);
		if (mlParser != null) {
			textArea.removeParser(mlParser);
		}
	}
	
	/**
	 * Returns the ThingML parser running on a text area with this ThingML language
	 * support installed.
	 *
	 * @param textArea The text area.
	 * @return The ThingML parser.  This will be <code>null</code> if the text
	 *         area does not have this <tt>ThingMLLanguageSupport</tt> installed.
	 */
	public ThingMLParser getParser(RSyntaxTextArea textArea) {
		// Could be a parser for another language.
		Object mlParser = textArea.getClientProperty(PROPERTY_LANGUAGE_PARSER);
		if (mlParser instanceof ThingMLParser) {
			return (ThingMLParser) mlParser;
		}
		return null;
	}

/**
	 * Returns whether syntax errors are squiggle-underlined in the editor.
	 *
	 * @return Whether errors are squiggle-underlined.
	 * @see #setShowSyntaxErrors(boolean)
	 */
	public boolean getShowSyntaxErrors() {
		return showSyntaxErrors;
	}

	/**
	 * Sets whether syntax errors are squiggle-underlined in the editor.
	 * 
	 * @param show
	 *            Whether syntax errors are squiggle-underlined.
	 * @see #getShowSyntaxErrors()
	 */
	public void setShowSyntaxErrors(boolean show) {
		showSyntaxErrors = show;
	}

}
