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
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;


/**
 * Basic JavaScript completion that requires no icon
 * e.g for or while
 */
public class JavascriptBasicCompletion extends BasicCompletion implements
		JSCompletionUI {

	public JavascriptBasicCompletion(CompletionProvider provider,
			String replacementText, String shortDesc, String summary) {
		super(provider, replacementText, shortDesc, summary);
	}


	public JavascriptBasicCompletion(CompletionProvider provider,
			String replacementText, String shortDesc) {
		super(provider, replacementText, shortDesc);
	}


	public JavascriptBasicCompletion(CompletionProvider provider,
			String replacementText) {
		super(provider, replacementText);
	}


	public Icon getIcon() {
		return IconFactory.getIcon(IconFactory.getEmptyIcon());
	}


	public int getRelevance() {
		return BASIC_COMPLETION_RELEVANCE;
	}

}
