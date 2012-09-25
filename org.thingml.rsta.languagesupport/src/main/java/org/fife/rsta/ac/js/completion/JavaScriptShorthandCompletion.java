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
package org.fife.rsta.ac.js.completion;

import javax.swing.Icon;

import org.fife.rsta.ac.js.IconFactory;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;


public class JavaScriptShorthandCompletion extends ShorthandCompletion implements
		JSCompletionUI {

	private static final String PREFIX = "<html><nobr>";


	public JavaScriptShorthandCompletion(CompletionProvider provider,
			String inputText, String replacementText) {
		super(provider, inputText, replacementText);
	}


	public JavaScriptShorthandCompletion(CompletionProvider provider,
			String inputText, String replacementText, String shortDesc) {
		super(provider, inputText, replacementText, shortDesc);
	}


	public JavaScriptShorthandCompletion(CompletionProvider provider,
			String inputText, String replacementText, String shortDesc,
			String summary) {
		super(provider, inputText, replacementText, shortDesc, summary);
	}


	public Icon getIcon() {
		return IconFactory.getIcon(IconFactory.TEMPLATE_ICON);
	}


	public int getRelevance() {
		return TEMPLATE_RELEVANCE;
	}


	public String getShortDescriptionText() {
		StringBuffer sb = new StringBuffer(PREFIX);
		sb.append(getInputText());
		sb.append(" - ");
		sb.append(getShortDescription());
		return sb.toString();
	}

}
