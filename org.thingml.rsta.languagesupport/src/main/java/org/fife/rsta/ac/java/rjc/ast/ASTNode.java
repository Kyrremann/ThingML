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


/**
 * A node in a Java AST.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public interface ASTNode {


	/**
	 * Returns the "name" of this node.  This will be the name of the method,
	 * the name of the member or local variable, etc.  For {@link CodeBlock}s
	 * it will be {@link CodeBlock#NAME}.<p>
	 *
	 * Note that this may not be unique.  For example, a class with an
	 * overloaded method will have multiple methods with the same "name,"
	 * just with different signatures.
	 *
	 * @return The "name" of this node.
	 */
	public String getName();


	/**
	 * Returns the end offset of the "name" of this node.
	 *
	 * @return The end offset.
	 */
	public int getNameEndOffset();


	/**
	 * Returns the start offset of the "name" of this node.
	 *
	 * @return The start offset.
	 */
	public int getNameStartOffset();


}