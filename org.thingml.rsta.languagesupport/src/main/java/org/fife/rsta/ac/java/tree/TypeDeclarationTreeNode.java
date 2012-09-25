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

import javax.swing.Icon;

import org.fife.rsta.ac.java.DecoratableIcon;
import org.fife.rsta.ac.java.IconFactory;
import org.fife.rsta.ac.java.rjc.ast.EnumDeclaration;
import org.fife.rsta.ac.java.rjc.ast.NormalClassDeclaration;
import org.fife.rsta.ac.java.rjc.ast.NormalInterfaceDeclaration;
import org.fife.rsta.ac.java.rjc.ast.TypeDeclaration;
import org.fife.rsta.ac.java.rjc.lang.Modifiers;


/**
 * Tree node for type declarations.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class TypeDeclarationTreeNode extends JavaTreeNode {


	public TypeDeclarationTreeNode(TypeDeclaration typeDec) {

		super(typeDec);
		//System.out.println("... " + typeDec);
		String iconName = null;
		int priority = PRIORITY_TYPE;

		if (typeDec instanceof NormalClassDeclaration) {
			NormalClassDeclaration ncd = (NormalClassDeclaration)typeDec;
			if (ncd.getModifiers()!=null) {
				if (ncd.getModifiers().isPublic()) {
					iconName = IconFactory.CLASS_ICON;
				}
				else if (ncd.getModifiers().isProtected()) {
					iconName = IconFactory.INNER_CLASS_PROTECTED_ICON;
				}
				else if (ncd.getModifiers().isPrivate()) {
					iconName = IconFactory.INNER_CLASS_PRIVATE_ICON;
				}
				else {
					iconName = IconFactory.INNER_CLASS_DEFAULT_ICON;
				}
			}
			else {
//System.out.println("...  " + value);
				iconName = IconFactory.DEFAULT_CLASS_ICON;
			}
		}
		else if (typeDec instanceof NormalInterfaceDeclaration) {
			NormalInterfaceDeclaration nid = (NormalInterfaceDeclaration)typeDec;
			if (nid.getModifiers()!=null && nid.getModifiers().isPublic()) {
				iconName = IconFactory.INTERFACE_ICON;
			}
			else {
				iconName = IconFactory.DEFAULT_INTERFACE_ICON;
			}
		}
		else if (typeDec instanceof EnumDeclaration) {
			EnumDeclaration ed = (EnumDeclaration)typeDec;
			if (ed.getModifiers()!=null) {
				if (ed.getModifiers().isPublic()) {
					iconName = IconFactory.ENUM_ICON;
				}
				else if (ed.getModifiers().isProtected()) {
					iconName = IconFactory.ENUM_PROTECTED_ICON;;
				}
				else if (ed.getModifiers().isPrivate()) {
					iconName = IconFactory.ENUM_PRIVATE_ICON;
				}
				else {
					iconName = IconFactory.ENUM_DEFAULT_ICON;
				}
			}
			else {
				//System.out.println("...  " + value);
				iconName = IconFactory.ENUM_DEFAULT_ICON;
			}
		}

		IconFactory fact = IconFactory.get();
		Icon mainIcon = fact.getIcon(iconName);

		if (mainIcon==null) { // Unknown type ???
			System.out.println("*** " + typeDec);
		}
		else {
			DecoratableIcon di = new DecoratableIcon(mainIcon);
			di.setDeprecated(typeDec.isDeprecated());
			Modifiers mods = typeDec.getModifiers();
			if (mods!=null) {
				if (mods.isAbstract()) {
					di.addDecorationIcon(fact.getIcon(IconFactory.ABSTRACT_ICON));
				}
				else if (mods.isFinal()) {
					di.addDecorationIcon(fact.getIcon(IconFactory.FINAL_ICON));
				}
				if (mods.isStatic()) {
					di.addDecorationIcon(fact.getIcon(IconFactory.STATIC_ICON));
					priority = PRIORITY_BOOST_STATIC;
				}
			}
			setIcon(di);
		}

		setSortPriority(priority);

	}


	public String getText(boolean selected) {
		TypeDeclaration typeDec = (TypeDeclaration)getUserObject();
		//System.out.println("... " + typeDec);
		return typeDec!=null ? typeDec.getName() : null;
	}


}