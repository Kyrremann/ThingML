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
package org.fife.rsta.ac.sh;

import org.fife.rsta.ac.c.CCompletionProvider;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;


/**
 * A completion provider for Unix shell scripts.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class ShellCompletionProvider extends CCompletionProvider {

	/**
	 * Whether local man pages should be used for function descriptions.
	 */
	private static boolean useLocalManPages;


	/**
	 * Constructor.
	 */
	public ShellCompletionProvider() {
	}


	/**
	 * {@inheritDoc}
	 */
	protected void addShorthandCompletions(DefaultCompletionProvider codeCP) {
		// Add nothing for now.
	}


	/**
	 * {@inheritDoc}
	 */
	protected CompletionProvider createStringCompletionProvider() {
		DefaultCompletionProvider cp = new DefaultCompletionProvider();
		return cp;
	}


	/**
	 * {@inheritDoc}
	 */
	public char getParameterListEnd() {
		return 0;
	}


	/**
	 * {@inheritDoc}
	 */
	public char getParameterListStart() {
		return 0;
	}


	/**
	 * Returns whether the local system's man pages should be used for
	 * descriptions of functions.  If this returns <tt>false</tt>, or man
	 * cannot be found (e.g. if this is Windows), a shorter description will
	 * be used instead.
	 *
	 * @return Whether to use the local man pages in function descriptions.
	 * @see #setUseLocalManPages(boolean)
	 */
	public static boolean getUseLocalManPages() {
		return useLocalManPages;
	}


	/**
	 * {@inheritDoc}
	 */
	protected String getXmlResource() {
		return "data/sh.xml";
	}


	/**
	 * Sets whether the local system's man pages should be used for
	 * descriptions of functions.  If this is set to <tt>false</tt>, or man
	 * cannot be found (e.g. if this is Windows), a shorter description will
	 * be used instead.
	 *
	 * @param use Whether to use the local man pages in function descriptions.
	 * @see #getUseLocalManPages()
	 */
	public static void setUseLocalManPages(boolean use) {
		useLocalManPages = use;
	}


}