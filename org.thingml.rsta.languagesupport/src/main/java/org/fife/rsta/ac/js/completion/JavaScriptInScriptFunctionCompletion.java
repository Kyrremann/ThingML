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
import org.fife.rsta.ac.js.ast.type.TypeDeclaration;
import org.fife.rsta.ac.js.ast.type.TypeDeclarationFactory;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;


public class JavaScriptInScriptFunctionCompletion extends FunctionCompletion
		implements JSCompletion {

	private TypeDeclaration returnType;
	
	public JavaScriptInScriptFunctionCompletion(CompletionProvider provider,
			String name, TypeDeclaration returnType) {
		super(provider, name, null);
		setRelevance(DEFAULT_FUNCTION_RELEVANCE);
		this.returnType = returnType;
	}


	public String getSummary() {
		String summary = super.getShortDescription(); // Could be just the
														// method name

		// If it's the Javadoc for the method...
		if (summary != null && summary.startsWith("/**")) {
			summary = org.fife.rsta.ac.java.Util.docCommentToHtml(summary);
		}

		return summary;
	}


	public Icon getIcon() {
		return IconFactory.getIcon(IconFactory.DEFAULT_FUNCTION_ICON);
	}


	public String getLookupName() {
		StringBuffer sb = new StringBuffer(getName());
		sb.append('(');
		int count = getParamCount();
		for (int i = 0; i < count; i++) {
			sb.append("p");
			if (i < count - 1) {
				sb.append(",");
			}
		}
		sb.append(')');
		return sb.toString();
	}


	public String getType() {
		String value = getType(true);
		return TypeDeclarationFactory.convertJavaScriptType(value, false);
	}


	public String getType(boolean qualified) {
		String type = returnType != null ? returnType.getQualifiedName() : null;
		return TypeDeclarationFactory.convertJavaScriptType(type, qualified);
	}
	
	
	
	public String getEnclosingClassName(boolean fullyQualified) {
		return null;
	}


	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(obj instanceof JSCompletion)
		{
			JSCompletion jsComp = (JSCompletion) obj;
			return getLookupName().equals(jsComp.getLookupName());
		}
		return super.equals(obj);
	}


	public int hashCode() {
		return getLookupName().hashCode();
	}


}