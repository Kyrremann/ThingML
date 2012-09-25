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

import javax.swing.text.JTextComponent;

import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionProvider;


/**
 * Base class for Java source completions.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public abstract class AbstractJavaSourceCompletion extends BasicCompletion
									implements JavaSourceCompletion {


	public AbstractJavaSourceCompletion(CompletionProvider provider,
										String replacementText) {
		super(provider, replacementText);
	}


	/**
	 * Overridden to ensure that two completions don't just have the same
	 * text value (ignoring case), but that they're of the same "type" of
	 * <code>Completion</code> as well, so, for example, a completion for the
	 * "String" class won't clash with a completion for a "string" LocalVar.
	 *
	 * @param o Another completion instance.
	 * @return How this completion compares to the other one.
	 */
	public int compareTo(Object o) {

		int rc = -1;

		if (o==this) {
			rc = 0;
		}

		else if (o instanceof Completion) {
			Completion c2 = (Completion)o;
			rc = toString().compareToIgnoreCase(c2.toString());
			if (rc==0) { // Same text value
				String clazz1 = getClass().getName();
				clazz1 = clazz1.substring(clazz1.lastIndexOf('.'));
				String clazz2 = c2.getClass().getName();
				clazz2 = clazz2.substring(clazz2.lastIndexOf('.'));
				rc = clazz1.compareTo(clazz2);
			}
		}

		return rc;

	}


	public String getAlreadyEntered(JTextComponent comp) {
		String temp = getProvider().getAlreadyEnteredText(comp);
		int lastDot = temp.lastIndexOf('.');
		if (lastDot>-1) {
			temp = temp.substring(lastDot+1);
		}
		return temp;
	}


}