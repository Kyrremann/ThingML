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

import java.util.Iterator;
import java.util.List;

import org.fife.rsta.ac.java.rjc.lang.Modifiers;


public interface TypeDeclaration extends ASTNode, TypeDeclarationContainer {


	public boolean getBodyContainsOffset(int offs);


	public int getBodyEndOffset();


	public int getBodyStartOffset();


	public TypeDeclaration getChildType(int index);


	/**
	 * Returns the child type declaration of this one that contains the
	 * specified offset, if any.
	 *
	 * @param offs The offset.
	 * @return The type declaration, or <code>null</code> if the offset is
	 *         outside of any child type declaration.
	 */
	public TypeDeclaration getChildTypeAtOffset(int offs);


	public int getChildTypeCount();


	public String getDocComment();


	/**
	 * Returns an iterator over all fields defined in this type.
	 *
	 * @return The iterator.
	 * @see #getMethodIterator()
	 * @see #getMemberIterator()
	 */
	public Iterator getFieldIterator();


	public int getMemberCount();


	/**
	 * Returns an iterator over all members of this type.  Note 
	 * that an exception may be thrown if a method is added to this type
	 * while this iterator is being used.
	 *
	 * @return The iterator.
	 * @see #getMethodIterator()
	 */
	public Iterator getMemberIterator();


	/**
	 * Returns an iterator over all methods defined in this type.
	 *
	 * @return The iterator.
	 * @see #getFieldIterator()
	 * @see #getMemberIterator()
	 */
	public Iterator getMethodIterator();


	/**
	 * Returns all methods declared in this type with the given name.  Does
	 * not check for methods with this name in subclasses.
	 *
	 * @param name The name to check for.
	 * @return Any method overloads with that name, or an empty list if none.
	 */
	public List getMethodsByName(String name);


	public Modifiers getModifiers();


	/**
	 * Returns the name of this type, unqualified.
	 *
	 * @return The name of this type.
	 * @see #getName(boolean)
	 */
	public String getName();


	/**
	 * Returns the name of this type.
	 *
	 * @param fullyQualified Whether the name returned should be fully
	 *        qualified.
	 * @return The type's name.
	 * @see #getName()
	 */
	public String getName(boolean fullyQualified);


	/**
	 * Returns the package this type is in.
	 *
	 * @return The package, or <code>null</code> if it's in the default package.
	 */
	public Package getPackage();


	public String getTypeString();


	public boolean isDeprecated();


	public void setDocComment(String comment);


}