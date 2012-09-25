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

import java.awt.Graphics;
import javax.swing.Icon;

import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.TemplateCompletion;


/**
 * A template completion for Java.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class JavaTemplateCompletion extends TemplateCompletion
		implements JavaSourceCompletion {

	private String icon;


	public JavaTemplateCompletion(CompletionProvider provider,
			String inputText, String definitionString, String template) {
		this(provider, inputText, definitionString, template, null);
	}


	public JavaTemplateCompletion(CompletionProvider provider,
			String inputText, String definitionString, String template,
			String shortDesc) {
		this(provider, inputText, definitionString, template, shortDesc, null);
	}


	public JavaTemplateCompletion(CompletionProvider provider,
			String inputText, String definitionString, String template,
			String shortDesc, String summary) {
		super(provider, inputText, definitionString, template, shortDesc, summary);
		setIcon(IconFactory.TEMPLATE_ICON);
	}


	public Icon getIcon() {
		return IconFactory.get().getIcon(icon);
	}


	public void rendererText(Graphics g, int x, int y, boolean selected) {
		JavaShorthandCompletion.renderText(g, getInputText(),
				getShortDescription(), x, y, selected);
	}


	public void setIcon(String iconId) {
		this.icon = iconId;
	}


}