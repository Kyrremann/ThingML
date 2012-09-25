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
import javax.swing.text.JTextComponent;

import org.fife.rsta.ac.java.JarManager;
import org.fife.rsta.ac.java.classreader.MethodInfo;
import org.fife.rsta.ac.java.rjc.ast.Method;
import org.fife.rsta.ac.js.IconFactory;
import org.fife.rsta.ac.js.JavaScriptHelper;
import org.fife.rsta.ac.js.ast.type.TypeDeclarationFactory;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.VariableCompletion;


public class JSBeanCompletion extends VariableCompletion implements
		JSCompletion {

	private JSMethodData methodData;
	private Method method;


	public JSBeanCompletion(CompletionProvider provider, MethodInfo methodInfo,
			JarManager jarManager) {
		super(provider, convertNameToBean(methodInfo.getName()), null);
		setRelevance(BEAN_METHOD_RELEVANCE);
		this.methodData = new JSMethodData(methodInfo, jarManager);
		this.method = methodData.getMethod();
	}


	public boolean equals(Object obj) {
		return (obj instanceof JSFunctionCompletion)
				&& ((JSBeanCompletion) obj).getName().equals(getName());
	}


	public Icon getIcon() {
		return IconFactory.getIcon(IconFactory.GLOBAL_VARIABLE_ICON);
	}


	public String getAlreadyEntered(JTextComponent comp) {
		String temp = getProvider().getAlreadyEnteredText(comp);
		int lastDot = JavaScriptHelper
				.findLastIndexOfJavaScriptIdentifier(temp);
		if (lastDot > -1) {
			temp = temp.substring(lastDot + 1);
		}
		return temp;
	}

	public String getType() {
		String value = getType(true);
		return TypeDeclarationFactory.convertJavaScriptType(value, false);
	}


	public String getType(boolean qualified) {
		return TypeDeclarationFactory.convertJavaScriptType(methodData
				.getType(qualified), qualified);
	}


	private String getMethodSummary() {
		String docComment = method != null ? method.getDocComment() : getName();
		return docComment != null ? docComment : method != null ? method
				.toString() : null;
	}


	public String getSummary() {
		String summary = getMethodSummary(); // Could be just the method name

		// If it's the Javadoc for the method...
		if (summary != null && summary.startsWith("/**")) {
			summary = org.fife.rsta.ac.java.Util.docCommentToHtml(summary);
		}

		return summary;
	}


	public String getLookupName() {
		return getName();
	}


	public String getEnclosingClassName(boolean fullyQualified) {
		return methodData.getEnclosingClassName(fullyQualified);
	}


	private static String convertNameToBean(String name) {
		boolean memberIsGetMethod = name.startsWith("get");
		boolean memberIsSetMethod = name.startsWith("set");
		boolean memberIsIsMethod = name.startsWith("is");
		if (memberIsGetMethod || memberIsIsMethod || memberIsSetMethod) {
			// Double check name component.
			String nameComponent = name.substring(memberIsIsMethod ? 2 : 3);
			if (nameComponent.length() == 0)
				return name; // return name

			// Make the bean property name.
			String beanPropertyName = nameComponent;
			char ch0 = nameComponent.charAt(0);
			if (Character.isUpperCase(ch0)) {
				if (nameComponent.length() == 1) {
					beanPropertyName = nameComponent.toLowerCase();
				}
				else {
					char ch1 = nameComponent.charAt(1);
					if (!Character.isUpperCase(ch1)) {
						beanPropertyName = Character.toLowerCase(ch0)
								+ nameComponent.substring(1);
					}
				}
			}
			name = beanPropertyName;
		}
		return name;

	}


	/**
	 * Overridden since {@link #equals(Object)} is overridden.
	 * 
	 * @return The hash code.
	 */
	public int hashCode() {
		return getName().hashCode();
	}

}