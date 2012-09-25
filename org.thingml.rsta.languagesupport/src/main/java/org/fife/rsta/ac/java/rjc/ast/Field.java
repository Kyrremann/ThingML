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

import org.fife.rsta.ac.java.rjc.lang.Modifiers;
import org.fife.rsta.ac.java.rjc.lang.Type;
import org.fife.rsta.ac.java.rjc.lexer.Scanner;
import org.fife.rsta.ac.java.rjc.lexer.Token;


public class Field extends AbstractMember {

	private Modifiers modifiers;
	private Type type;
	private boolean deprecated;
	private String docComment;


	public Field(Scanner s, Modifiers modifiers, Type type, Token t) {
		super(t.getLexeme(), s.createOffset(t.getOffset()));
		setDeclarationEndOffset(s.createOffset(t.getOffset() + t.getLength()));
		if (modifiers==null) {
			modifiers = new Modifiers();
		}
		this.modifiers = modifiers;
		this.type = type;
	}


	public String getDocComment() {
		return docComment;
	}


	public Modifiers getModifiers() {
		return modifiers;
	}


	public Type getType() {
		return type;
	}


	public boolean isDeprecated() {
		return deprecated;
	}


	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}


	public void setDocComment(String comment) {
		docComment = comment;
	}


}