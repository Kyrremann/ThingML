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
package org.fife.rsta.ac.java.tree;

import org.fife.rsta.ac.java.IconFactory;
import org.fife.rsta.ac.java.rjc.ast.LocalVariable;


/**
 * Tree node for a local variable.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class LocalVarTreeNode extends JavaTreeNode {

	private String text;


	public LocalVarTreeNode(LocalVariable var) {

		super(var);
		setIcon(IconFactory.get().getIcon(IconFactory.LOCAL_VARIABLE_ICON));
		setSortPriority(PRIORITY_LOCAL_VAR);

		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append(var.getName());
		sb.append(" : ");
		sb.append("<font color='#888888'>");
		MemberTreeNode.appendType(var.getType(), sb);
		text = sb.toString();
	}


	public String getText(boolean selected) {
		// Strip out HTML tags
		return selected ? text.replaceAll("<[^>]*>", "").
				replaceAll("&lt;", "<").replaceAll("&gt;", ">") : text;
	}


}