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

import org.fife.rsta.ac.java.classreader.FieldInfo;
import org.fife.rsta.ac.java.rjc.ast.Field;
import org.fife.rsta.ac.java.rjc.lang.Type;
import org.fife.ui.autocomplete.CompletionProvider;


/**
 * A completion for a Java field.  This completion gets its information from
 * one of two sources:
 * 
 * <ul>
 *    <li>A {@link FieldInfo} instance, which is loaded by parsing a class
 *        file.  This is used when this completion represents a field found
 *        in a compiled library.</li>
 *    <li>A {@link Field} instance, which is created when parsing a Java
 *        source file.  This is used when the completion represents a field
 *        found in uncompiled source, such as the source in an
 *        <tt>RSyntaxTextArea</tt>, or in a loose file on disk.</li>
 * </ul>
 *
 * @author Robert Futrell
 * @version 1.0
 */
class FieldCompletion extends AbstractJavaSourceCompletion
						implements MemberCompletion {

	private Data data;

	/**
	 * The relevance of fields.  This allows fields to be "higher" in
	 * the completion list than other types.
	 */
	private static final int RELEVANCE		= 3;


	public FieldCompletion(CompletionProvider provider, Field field) {
		super(provider, field.getName());
		this.data = new FieldData(field);
		setRelevance(RELEVANCE);
	}


	public FieldCompletion(CompletionProvider provider, FieldInfo info) {
		super(provider, info.getName());
		this.data = new FieldInfoData(info, (SourceCompletionProvider)provider);
		setRelevance(RELEVANCE);
	}


	private FieldCompletion(CompletionProvider provider, String text) {
		super(provider, text);
		setRelevance(RELEVANCE);
	}


	public boolean equals(Object obj) {
		return (obj instanceof FieldCompletion) &&
			((FieldCompletion)obj).getSignature().equals(getSignature());
	}


	public static FieldCompletion createLengthCompletion(
							CompletionProvider provider, final Type type) {
		FieldCompletion fc = new FieldCompletion(provider, type.toString());
		fc.data = new Data() {

			public String getEnclosingClassName(boolean fullyQualified) {
				return type.getName(fullyQualified);
			}
			
			public String getIcon() {
				return IconFactory.FIELD_PUBLIC_ICON;
			}

			public String getSignature() {
				return "length";
			}

			public String getSummary() {
				return null;
			}

			public String getType() {
				return "int";
			}

			public boolean isConstructor() {
				return false;
			}

			public boolean isDeprecated() {
				return false;
			}

			public boolean isAbstract() {
				return false;
			}

			public boolean isFinal() {
				return false;
			}

			public boolean isStatic() {
				return false;
			}

		};
		return fc;
	}


	public String getEnclosingClassName(boolean fullyQualified) {
		return data.getEnclosingClassName(fullyQualified);
	}


	public Icon getIcon() {
		return IconFactory.get().getIcon(data);
	}


	public String getSignature() {
		return data.getSignature();
	}


	public String getSummary() {

		String summary = data.getSummary(); // Could be just the method name

		// If it's the Javadoc for the method...
		if (summary!=null && summary.startsWith("/**")) {
			summary = org.fife.rsta.ac.java.Util.docCommentToHtml(summary);
		}

		return summary;

	}


	public String getType() {
		return data.getType();
	}


	public int hashCode() {
		return getSignature().hashCode();
	}


	public boolean isDeprecated() {
		return data.isDeprecated();
	}


	public void rendererText(Graphics g, int x, int y, boolean selected) {
		MethodCompletion.rendererText(this, g, x, y, selected);
	}


}