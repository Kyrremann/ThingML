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
package org.fife.rsta.ac.java.rjc.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fife.rsta.ac.java.rjc.lang.Type;
import org.fife.rsta.ac.java.rjc.lexer.Scanner;


/**
 * A class declaration:
 * 
 * <pre>
 * NormalClassDeclaration:
 *    'class' Identifier [TypeParameters] ['extends' Type] ['implements' TypeList] ClassBody
 * </pre>
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class NormalClassDeclaration extends AbstractTypeDeclarationNode {

	// --- "NormalClassDeclaration" fields ---
	private List typeParams;
	private Type extendedType;
	private List implementedList;


	public NormalClassDeclaration(Scanner s, int offs, String className) {
		super(className, s.createOffset(offs), s.createOffset(offs+className.length()));
		implementedList = new ArrayList(0); // Usually not many
		// If parsing java.lang.Object.java, setExtendedType(null) should be
		// called.  This is here for all other classes without an explicit
		// super class declared.
		extendedType = new Type("java.lang.Object");
	}


	public void addImplemented(Type implemented) {
		implementedList.add(implemented);
	}


	public Type getExtendedType() {
		return extendedType;
	}


	public int getImplementedCount() {
		return implementedList.size();
	}


	public Iterator getImplementedIterator() {
		return implementedList.iterator();
	}


	/**
	 * Gets the method in this class that contains a given offset.
	 *
	 * @param offs The offset.
	 * @return The method containing the offset, or <code>null</code> if no
	 *         method in this class contains the offset.
	 */
	public Method getMethodContainingOffset(int offs) {
		for (Iterator i=getMethodIterator(); i.hasNext(); ) {
			Method method = (Method)i.next();
			if (method.getBodyContainsOffset(offs)) {
				return method;
			}
		}
		return null;
	}


	public List getTypeParameters() {
		return typeParams;
	}


	public String getTypeString() {
		return "class";
	}


	/**
	 * Returns whether a <code>Type</code> and a type name are type
	 * compatible.  This method currently is a sham!
	 *
	 * @param type
	 * @param typeName
	 * @return
	 */
	// TODO: Get me working!  Probably need better parameters passed in!!!
	private boolean isTypeCompatible(Type type, String typeName) {

		String typeName2 = type.getName(false);

		// Remove generics info for now
		// TODO: Handle messy generics cases
		int lt = typeName2.indexOf('<');
		if (lt>-1) {
			String arrayDepth = null;
			int brackets = typeName2.indexOf('[', lt);
			if (brackets>-1) {
				arrayDepth = typeName2.substring(brackets);
			}
			typeName2 = typeName2.substring(lt);
			if (arrayDepth!=null) {
				typeName2 += arrayDepth;
			}
		}

		return typeName2.equalsIgnoreCase(typeName);

	}


	public void setExtendedType(Type type) {
		extendedType = type;
	}


	public void setTypeParameters(List typeParams) {
		this.typeParams = typeParams;
	}


}