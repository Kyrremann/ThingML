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

import javax.swing.text.Element;

import org.fife.rsta.ac.LanguageSupport;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Token;


/**
 * Returns non-whitespace, non-comment tokens from an {@link RSyntaxDocument},
 * one at a time.  This can be used by simplistic {@link LanguageSupport}s to
 * "parse" for simple, easily-identifiable tokens, such as curly braces and
 * {@link Token#VARIABLE}s.  For example, to identify code blocks for languages
 * structured like C and Java, you can use this class in conjunction with
 * {@link CodeBlock} and {@link VariableDeclaration} to create an
 * easily-parsable model of your source code.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class TokenScanner {

	private RSyntaxDocument doc;
	private Element root;
	private Token t;
	private int line;


	public TokenScanner(RSyntaxTextArea textArea) {
		this((RSyntaxDocument)textArea.getDocument());
	}


	public TokenScanner(RSyntaxDocument doc) {
		this.doc = doc;
		root = doc.getDefaultRootElement();
		line = 0;
		t = null;//textArea.getTokenListForLine(line++);
	}


	/**
	 * Returns the next non-whitespace, non-comment token in the text area.
	 *
	 * @return The next token, or <code>null</code> if we are at the end of
	 *         its document.
	 */
	public Token next() {
		Token next = nextRaw();
		while (next!=null && (next.isWhitespace() || next.isComment())) {
			next = nextRaw();
		}
		return next;
	}


	/**
	 * Returns the next token in the text area.
	 *
	 * @return The next token, or <code>null</code> if we are at the end of
	 *         its document.
	 */
	private Token nextRaw() {
		if (t==null || !t.isPaintable()) {
			int lineCount = root.getElementCount();
			while (line<lineCount && (t==null || !t.isPaintable())) {
				t = doc.getTokenListForLine(line++);
			}
			if (line==lineCount) {
				return null;
			}
		}
		Token next = t;
		t = t.getNextToken();
		return next;
	}


}