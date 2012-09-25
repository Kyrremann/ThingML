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
 * An interface declaration:
 * 
 * <pre>
 * NormalInterfaceDeclaration:
 *    'interface' Identifier [TypeParameters] ['extends' TypeList] InterfaceBody
 * </pre>
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class NormalInterfaceDeclaration extends AbstractTypeDeclarationNode {

	private List extendedList;


	public NormalInterfaceDeclaration(Scanner s, int offs, String name) {
		super(name, s.createOffset(offs), s.createOffset(offs+name.length()));
		extendedList = new ArrayList(1); // Usually small
	}


	public void addExtended(Type extended) {
		extendedList.add(extended);
	}


	public int getExtendedCount() {
		return extendedList.size();
	}


	public Iterator getExtendedIterator() {
		return extendedList.iterator();
	}


	public String getTypeString() {
		return "interface";
	}


}