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


/**
 * A completion that represents a package name.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class PackageNameCompletion extends AbstractJavaSourceCompletion {


	public PackageNameCompletion(CompletionProvider provider, String text,
								String alreadyEntered) {
		super(provider, text.substring(text.lastIndexOf('.')+1));
	}


	public boolean equals(Object obj) {
		return (obj instanceof PackageNameCompletion) &&
			((PackageNameCompletion)obj).getReplacementText().equals(getReplacementText());
	}


	public Icon getIcon() {
		return IconFactory.get().getIcon(IconFactory.PACKAGE_ICON);
	}


	public int hashCode() {
		return getReplacementText().hashCode();
	}


	public void rendererText(Graphics g, int x, int y, boolean selected) {
		g.drawString(getInputText(), x, y);
	}


}