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
 * An EnumBody.
 *
 * <pre>
 * EnumBody:
 *    '{' [EnumConstants] [,] [EnumBodyDeclarations] '}'
 *
 *
 * EnumConstants:
 *    EnumConstant
 *    EnumConstants , EnumConstant
 *
 * EnumConstant:
 *    Annotations Identifier [Arguments] [ClassBody]
 *
 * EnumBodyDeclarations:
 *    ; {ClassBodyDeclaration}
 * </pre>
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class EnumBody extends AbstractASTNode {


	public EnumBody(String name, Offset start) {
		super(name, start);
	}


}