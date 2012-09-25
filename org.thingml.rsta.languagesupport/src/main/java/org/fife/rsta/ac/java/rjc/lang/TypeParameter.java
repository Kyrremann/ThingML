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
package org.fife.rsta.ac.java.rjc.lang;

import java.util.ArrayList;
import java.util.List;

import org.fife.rsta.ac.java.rjc.lexer.Token;


/**
 * A TypeParameter.
 *
 * <pre>
 * TypeParameter:
 *    Identifier ['extends' Bound]
 * 
 * Bound:
 *    Type { '&' Type }
 * </pre>
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class TypeParameter {

	private Token name;
	private List bounds;


	public TypeParameter(Token name) {
		this.name = name;
	}


	public void addBound(Type bound) {
		if (bounds==null) {
			bounds = new ArrayList(1); // Usually just 1
		}
		bounds.add(bound);
	}


	public String getName() {
		return name.getLexeme();
	}


}