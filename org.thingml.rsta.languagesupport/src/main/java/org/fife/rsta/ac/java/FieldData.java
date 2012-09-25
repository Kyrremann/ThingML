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

import org.fife.rsta.ac.java.MemberCompletion.Data;
import org.fife.rsta.ac.java.rjc.ast.Field;
import org.fife.rsta.ac.java.rjc.ast.TypeDeclaration;
import org.fife.rsta.ac.java.rjc.lang.Modifiers;


/**
 * Metadata about a field as read from a Java source file.  This class is
 * used by instances of {@link FieldCompletion}.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class FieldData implements Data {

	private Field field;


	public FieldData(Field field) {
		this.field = field;
	}


	/**
	 * {@inheritDoc}
	 */
	public String getEnclosingClassName(boolean fullyQualified) {
		// NOTE: This check isn't really necessary, but is here just in case
		// there's a bug in the parsing code.
		TypeDeclaration td = field.getParentTypeDeclaration();
		if (td==null) {
			new Exception("No parent type declaration for: " + getSignature()).
							printStackTrace();
			return "";
		}
		return td.getName(fullyQualified);
	}


	/**
	 * {@inheritDoc}
	 */
	public String getIcon() {

		String key = null;

		Modifiers mod = field.getModifiers();
		if (mod==null) {
			key = IconFactory.FIELD_DEFAULT_ICON;
		}
		else if (mod.isPrivate()) {
			key = IconFactory.FIELD_PRIVATE_ICON;
		}
		else if (mod.isProtected()) {
			key = IconFactory.FIELD_PROTECTED_ICON;
		}
		else if (mod.isPublic()) {
			key = IconFactory.FIELD_PUBLIC_ICON;
		}
		else {
			key = IconFactory.FIELD_DEFAULT_ICON;
		}

		return key;

	}


	/**
	 * {@inheritDoc}
	 */
	public String getSignature() {
		return field.getName();
	}


	/**
	 * {@inheritDoc}
	 */
	public String getSummary() {
		String docComment = field.getDocComment();
		return docComment!=null ? docComment : field.toString();
	}


	/**
	 * {@inheritDoc}
	 */
	public String getType() {
		return field.getType().toString();
	}


	public boolean isAbstract() {
		return field.getModifiers().isAbstract();
	}


	/**
	 * Always returns <code>false</code>, fields cannot be constructors.
	 *
	 * @return <code>false</code> always.
	 */
	public boolean isConstructor() {
		return false;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isDeprecated() {
		return field.isDeprecated();
	}


	public boolean isFinal() {
		return field.getModifiers().isFinal();
	}


	public boolean isStatic() {
		return field.getModifiers().isStatic();
	}


}