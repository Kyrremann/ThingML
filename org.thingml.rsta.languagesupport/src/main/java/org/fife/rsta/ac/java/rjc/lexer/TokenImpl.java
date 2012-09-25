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
 * Implementation of a token in a Java source file.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class TokenImpl implements Token {

	private int type;

	/**
	 * The token's text.
	 */
	private String lexeme;

	/**
	 * The line the token is on.
	 */
	private int line;

	/**
	 * The column the token is on.
	 */
	private int column;

	/**
	 * The absolute offset into the source of the token.
	 */
	private int offset;

	/**
	 * Whether the token is invalid (e.g. an invalid char of String).
	 */
	private boolean invalid;


	public TokenImpl(int type, String lexeme, int line, int column, int offs) {
		this(type, lexeme, line, column, offs, false);
	}


	public TokenImpl(int type, String lexeme, int line, int column, int offs,
					boolean invalid) {
		this.type = type;
		this.lexeme = lexeme;
		this.line = line;
		this.column = column;
		this.offset = offs;
		this.invalid = invalid;
	}


	public boolean equals(Object obj) {
		if (obj==this) {
			return true;
		}
		if (obj instanceof Token) {
			Token t2 = (Token)obj;
			return type==t2.getType() && lexeme.equals(t2.getLexeme()) &&
					line==t2.getLine() && column==t2.getColumn() &&
					invalid==t2.isInvalid();
		}
		return false;
	}


	public int getColumn() {
		return column;
	}


	public int getLength() {
		return lexeme.length();
	}


	public String getLexeme() {
		return lexeme;
	}


	public int getLine() {
		return line;
	}


	public int getOffset() {
		return offset;
	}


	public int getType() {
		return type;
	}


	public int hashCode() {
		return lexeme.hashCode();
	}


	public boolean isBasicType() {
		switch (getType()) {
			case KEYWORD_BYTE:
			case KEYWORD_SHORT:
			case KEYWORD_CHAR:
			case KEYWORD_INT:
			case KEYWORD_LONG:
			case KEYWORD_FLOAT:
			case KEYWORD_DOUBLE:
			case KEYWORD_BOOLEAN:
				return true;
			default:
				return false;
		}
	}


	public boolean isIdentifier() {
		return (getType()&IDENTIFIER)>0;
	}


	public boolean isInvalid() {
		return invalid;
	}


	public boolean isOperator() {
		return (getType()&OPERATOR)>0;
	}


	public boolean isType(int type) {
		return this.type==type;
	}


	public String toString() {
		return "[TokenImpl: " +
			"type=" + type +
			"; lexeme=\"" + lexeme + "\"" +
			"; line=" + getLine() +
			"; col=" + getColumn() +
			"; offs=" + getOffset() +
			"; invalid=" + isInvalid() +
			"]";
	}


}