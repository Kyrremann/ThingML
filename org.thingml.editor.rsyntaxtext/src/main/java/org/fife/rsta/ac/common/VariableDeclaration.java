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
package org.fife.rsta.ac.common;

import org.fife.rsta.ac.LanguageSupport;


/**
 * A marker for a variable declaration.  This can be used by
 * {@link LanguageSupport}s to mark variables, and is especially helpful
 * when used in conjunction with {@link CodeBlock}.
 *
 * @author Robert Futrell
 * @version 1.0
 * @see CodeBlock
 */
public class VariableDeclaration {

	private String type;
	private String name;
	private int offset;


	public VariableDeclaration(String name, int offset) {
		this(null, name, offset);
	}


	public VariableDeclaration(String type, String name, int offset) {
		this.type = type;
		this.name = name;
		this.offset = offset;
	}


	public String getName() {
		return name;
	}


	public int getOffset() {
		return offset;
	}


	/**
	 * Returns the type of this variable.
	 *
	 * @return The variable's type, or <code>null</code> if none.
	 */
	public String getType() {
		return type;
	}


}