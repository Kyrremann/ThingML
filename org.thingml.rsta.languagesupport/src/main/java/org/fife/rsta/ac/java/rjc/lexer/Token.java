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
package org.fife.rsta.ac.java.rjc.lexer;


/**
 * A lexical token in a Java file.
 *
 * @author Robert Futrell
 * @version 0.1
 */
public interface Token extends TokenTypes {

	public int getColumn();


	public String getLexeme();


	public int getLength();


	public int getLine();


	public int getOffset();


	public int getType();


	public boolean isBasicType();


	public boolean isIdentifier();


	public boolean isInvalid();


	public boolean isOperator();


	public boolean isType(int type);


}