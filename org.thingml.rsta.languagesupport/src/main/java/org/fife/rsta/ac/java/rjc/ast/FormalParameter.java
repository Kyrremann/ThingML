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

import java.util.List;

import org.fife.rsta.ac.java.rjc.lang.Type;
import org.fife.rsta.ac.java.rjc.lexer.Scanner;


/**
 * A parameter to a method.
 *
 * @author Robert Futrell
 * @version 1.0
 */
/*
 * FormalParameter:
 *    ['final'] [Annotations] Type VariableDeclaratorId
 *   
 * VariableDeclaratorId:
 *    Identifier { "[" "]" }
 */
public class FormalParameter extends LocalVariable {

	private List annotations;


	public FormalParameter(Scanner s, boolean isFinal,
			Type type, int offs, String name, List annotations) {
		super(s, isFinal, type, offs, name);
		this.annotations = annotations;
	}


	public int getAnnotationCount() {
		return annotations==null ? 0 : annotations.size();
	}


	/**
	 * Overridden to return "<code>getType() getName()</code>".
	 *
	 * @return This parameter, as a string.
	 */
	public String toString() {
		return getType() + " " + getName();
	}


}