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

import org.fife.rsta.ac.java.rjc.lexer.Offset;


/**
 * Base implementation of an AST node.
 *
 * @author Robert Futrell
 * @version 1.0
 */
abstract class AbstractASTNode implements ASTNode {

	private String name;
	private Offset startOffs;
	private Offset endOffs;


	protected AbstractASTNode(String name, Offset start) {
		this(name, start, null);
	}


	protected AbstractASTNode(String name, Offset start, Offset end) {
		this.name = name;
		startOffs = start;
		endOffs = end;
	}


	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return name;
	}


	/**
	 * {@inheritDoc}
	 */
	public int getNameEndOffset() {
		return endOffs!=null ? endOffs.getOffset() : Integer.MAX_VALUE;
	}


	/**
	 * {@inheritDoc}
	 */
	public int getNameStartOffset() {
		return startOffs!=null ? startOffs.getOffset() : 0;
	}


	public void setDeclarationEndOffset(Offset end) {
		endOffs = end;
	}


	/**
	 * Sets the start and end offsets of this node.
	 *
	 * @param start The start offset.
	 * @param end The end offset.
	 */
	protected void setDeclarationOffsets(Offset start, Offset end) {
		startOffs = start;
		endOffs = end;
	}


	/**
	 * Returns the name of this node (e.g. the value of {@link #getName()}.
	 * Subclasses can override this method if appropriate.
	 *
	 * @return A string representation of this node.
	 */
	public String toString() {
		return getName();
	}


}