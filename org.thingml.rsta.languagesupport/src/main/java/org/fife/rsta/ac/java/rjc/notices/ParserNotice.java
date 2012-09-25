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
package org.fife.rsta.ac.java.rjc.notices;

import org.fife.rsta.ac.java.rjc.lexer.Token;


/**
 * A notice (e.g., a warning or error) from a parser.
 *
 * @author Robert Futrell
 * @version 0.1
 */
public class ParserNotice {

	private int line;
	private int column;
	private int length;
	private String message;


	public ParserNotice(Token t, String msg) {
		line = t.getLine();
		column = t.getColumn();
		length = t.getLexeme().length();
		message = msg;
	}


	/**
	 * Constructor.
	 *
	 * @param line The line of the notice.
	 * @param column The column of the notice.
	 * @param length The length of the code the message is concerned with.
	 * @param message The message.
	 */
	public ParserNotice(int line, int column, int length, String message) {
		this.line = line;
		this.column = column;
		this.length = length;
		this.message = message;
	}


	/**
	 * Returns the character offset into the line of the parser notice,
	 * if any.
	 *
	 * @return The column.
	 */
	public int getColumn() {
		return column;
	}


	/**
	 * Returns the length of the code the message is concerned with.
	 *
 	 * @return The length of the code the message is concerned with.
	 */
	public int getLength() {
		return length;
	}


	/**
	 * Returns the line number the notice is about, if any.
	 *
	 * @return The line number.
	 */
	public int getLine() {
		return line;
	}


	/**
	 * Returns the message from the parser.
	 *
	 * @return The message from the parser.
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * Returns a string representation of this parser notice.
	 *
	 * @return This parser notice as a string.
	 */
	public String toString() {
		return "(" + getLine() + ", " + getColumn() + ": " + getMessage();
	}


}