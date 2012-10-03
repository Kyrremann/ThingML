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

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.text.JTextComponent;

import org.fife.rsta.ac.java.classreader.MethodInfo;
import org.fife.rsta.ac.java.rjc.ast.Method;
import org.fife.rsta.ac.js.IconFactory;
import org.fife.rsta.ac.js.JavaScriptHelper;
import org.fife.rsta.ac.js.SourceCompletionProvider;
import org.fife.rsta.ac.js.ast.type.TypeDeclarationFactory;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.ParameterizedCompletion;


public class JSFunctionCompletion extends FunctionCompletion implements
		JSCompletion {

	private JSMethodData methodData;
	private String compareString;


	public JSFunctionCompletion(CompletionProvider provider, MethodInfo method) {
		this(provider, method, false);
	}


	public JSFunctionCompletion(CompletionProvider provider,
			MethodInfo methodInfo, boolean showParameterType) {
		super(provider, methodInfo.getName(), null);
		this.methodData = new JSMethodData(methodInfo,
				((SourceCompletionProvider) provider).getJarManager());
		List params = populateParams(methodData, showParameterType);
		setParams(params);
	}


	private List populateParams(JSMethodData methodData,
			boolean showParameterType) {
		MethodInfo methodInfo = methodData.getMethodInfo();
		int count = methodInfo.getParameterCount();
		String[] paramTypes = methodInfo.getParameterTypes();
		List params = new ArrayList(count);
		for (int i = 0; i < count; i++) {
			String name = methodData.getParameterName(i);
			String type = methodData.getParameterType(paramTypes, i);
			params.add(new JSFunctionParam(type, name, showParameterType));
		}

		return params;
	}


	/**
	 * Overridden to compare methods by their comparison strings.
	 * 
	 * @param o A <code>Completion</code> to compare to.
	 * @return The sort order.
	 */
	public int compareTo(Object o) {

		int rc = -1;

		if (o == this)
			rc = 0;

		else if (o instanceof JSFunctionCompletion) {
			rc = getCompareString().compareTo(
					((JSFunctionCompletion) o).getCompareString());
		}

		else if (o instanceof Completion) {
			Completion c2 = (Completion) o;
			rc = toString().compareToIgnoreCase(c2.toString());
			if (rc == 0) { // Same text value
				String clazz1 = getClass().getName();
				clazz1 = clazz1.substring(clazz1.lastIndexOf('.'));
				String clazz2 = c2.getClass().getName();
				clazz2 = clazz2.substring(clazz2.lastIndexOf('.'));
				rc = clazz1.compareTo(clazz2);
			}
		}

		return rc;

	}


	public boolean equals(Object obj) {
		return (obj instanceof JSFunctionCompletion)
				&& ((JSFunctionCompletion) obj).getCompareString().equals(
						getCompareString());
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


	private String getCompareString() {

		/*
		 * This string compares the following parts of methods in this order, to
		 * optimize sort order in completion lists.
		 * 
		 * 1. First, by name 2. Next, by number of parameters. 3. Finally, by
		 * parameter type.
		 */

		if (compareString == null) {

			compareString = getLookupName();
		}

		return compareString;

	}


	public String getLookupName() {
		SourceCompletionProvider provider = (SourceCompletionProvider) getProvider();
		return provider.getJavaScriptEngine().getJavaScriptResolver(provider).getLookupText(methodData, getName());
	}


	public String getDefinitionString() {
		return getSignature();
	}


	private String getMethodSummary() {

		// String summary = methodData.getSummary(); // Could be just the method
		// name

		Method method = methodData.getMethod();
		String summary = method != null ? method.getDocComment() : null;
		// If it's the Javadoc for the method...
		if (summary != null && summary.startsWith("/**")) {
			summary = org.fife.rsta.ac.java.Util.docCommentToHtml(summary);
		}

		return summary != null ? summary : getNameAndParameters();
	}


	private String getNameAndParameters() {
		return formatMethodAtString(getName(), methodData);
	}


	private static String formatMethodAtString(String name, JSMethodData method) {
		StringBuffer sb = new StringBuffer(name);
		sb.append('(');
		int count = method.getParameterCount();
		for (int i = 0; i < count; i++) {
			sb.append(method.getParameterName(i));
			if (i < count - 1) {
				sb.append(", ");
			}
		}
		sb.append(')');
		return sb.toString();
	}


	public String getSignature() {
		return getNameAndParameters();
	}


	public String getSummary() {
		String summary = getMethodSummary(); // Could be just the method name

		// If it's the Javadoc for the method...
		if (summary != null && summary.startsWith("/**")) {
			summary = org.fife.rsta.ac.java.Util.docCommentToHtml(summary);
		}

		return summary;
	}


	public int hashCode() {
		return getCompareString().hashCode();
	}


	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return getSignature();
	}


	public String getType() {
		String value = getType(true);
		return TypeDeclarationFactory.convertJavaScriptType(value, false);
	}


	public String getType(boolean qualified) {
		return TypeDeclarationFactory.convertJavaScriptType(methodData
				.getType(qualified), qualified);
	}


	public Icon getIcon() {
		return methodData.isStatic() ? IconFactory
				.getIcon(IconFactory.PUBLIC_STATIC_FUNCTION_ICON) : IconFactory
				.getIcon(IconFactory.DEFAULT_FUNCTION_ICON);
	}


	public int getRelevance() {
		return DEFAULT_FUNCTION_RELEVANCE;
	}


	public String getEnclosingClassName(boolean fullyQualified) {
		return methodData.getEnclosingClassName(fullyQualified);
	}


	/**
	 * Override the FunctionCompletion.Parameter to lookup the Javascript name
	 * for the completion type
	 */
	public static class JSFunctionParam extends
			ParameterizedCompletion.Parameter {

		private boolean showParameterType;


		public JSFunctionParam(Object type, String name,
				boolean showParameterType) {
			super(type, name);
			this.showParameterType = showParameterType;
		}


		public String getType() {
			return showParameterType ? TypeDeclarationFactory
					.convertJavaScriptType(super.getType(), false) : null;
		}

	}

}