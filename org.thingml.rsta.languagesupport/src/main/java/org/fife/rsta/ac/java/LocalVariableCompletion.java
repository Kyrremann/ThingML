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

import org.fife.rsta.ac.java.rjc.ast.LocalVariable;
import org.fife.ui.autocomplete.CompletionProvider;

class LocalVariableCompletion extends AbstractJavaSourceCompletion {

	private LocalVariable localVar;

	/**
	 * The relevance of local variables.  This allows local variables to be
	 * "higher" in the completion list than other types.
	 */
	private static final int RELEVANCE		= 4;


	public LocalVariableCompletion(CompletionProvider provider,
									LocalVariable localVar) {
		super(provider, localVar.getName());
		this.localVar = localVar;
		setRelevance(RELEVANCE);
	}


	public boolean equals(Object obj) {
		return (obj instanceof LocalVariableCompletion) &&
			((LocalVariableCompletion)obj).getReplacementText().
												equals(getReplacementText());
	}


	public Icon getIcon() {
		return IconFactory.get().getIcon(IconFactory.LOCAL_VARIABLE_ICON);
	}


	public int hashCode() {
		return getReplacementText().hashCode(); // Match equals()
	}


	public void rendererText(Graphics g, int x, int y, boolean selected) {

		StringBuffer sb = new StringBuffer();
		sb.append(localVar.getName());
		sb.append(" : ");
		sb.append(localVar.getType());
		g.drawString(sb.toString(), x, y);

	}


}