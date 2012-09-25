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
package org.fife.rsta.ac.thingml;

import org.fife.rsta.ac.thingml.syntaxtree.Program;
import org.fife.rsta.ac.thingml.thingmlparser.Lexer;
import org.fife.rsta.ac.thingml.thingmlparser.parser;
import org.fife.rsta.ac.thingml.tree.ThingMLTreeNode;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;
import org.fife.ui.rsyntaxtextarea.parser.ParserNotice;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Parses ThingML code in an <code>RSyntaxTextArea</code>.
 * <p>
 * 
 * Like all RSTA <code>Parser</code>s, an <code>ThingMLParser</code> instance is
 * notified when the RSTA's text content changes. After a small delay, it will
 * parse the content as ThingML, building an AST and looking for any errors.
 * When parsing is complete, a property change event of type
 * {@link #PROPERTY_AST} is fired. Listeners can check the new value of the
 * property for an {@link ThingMLTreeNode} that represents the root of a tree
 * structure modeling the ThingML content in the text area. Note that the
 * <code>XmlTreeNode</code> may be incomplete if there were parsing/syntax
 * errors (it will usually be complete "up to" the error in the content).
 * <p>
 * 
 * This parser cannot be shared amongst multiple instances of
 * <code>RSyntaxTextArea</code>.
 * <p>
 * 
 * @author Kyrre Havik Eriksen
 * @version 0.1
 */
public class ThingMLParser extends AbstractParser {

	public static final String PROPERTY_AST = "ThingMLAST";

	private PropertyChangeSupport support;
	private ThingMLTreeNode root;
	private List lineOffset;

	ThingMLParser(ThingMLLanguageSupport tls) {
		this.support = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(String prop, PropertyChangeListener l) {
		support.addPropertyChangeListener(prop, l);
	}

	public void removePropertyChangeListener(String prop,
			PropertyChangeListener l) {
		support.removePropertyChangeListener(prop, l);
	}

	/**
	 * Returns the ThingML model from the last time it was parsed.
	 * 
	 * @return The root node of the ThingML model, or <code>null</code> if it
	 *         has not yet been parsed or an error occurred while parsing.
	 */
	public ThingMLTreeNode getAst() {
		return root;
	}

	public ParseResult parse(RSyntaxDocument document, String arg1) {
		DefaultParseResult result = new DefaultParseResult(this);
		int lineCount = document.getDefaultRootElement().getElementCount();
		result.setParsedLines(0, lineCount - 1);

		parser parser = null;
		Program program;
		InputStream inputStream = null;
		long start = 0;

		try {
			inputStream = new ByteArrayInputStream(document.getText(0,
					document.getLength()).getBytes());
			start = System.currentTimeMillis();
			Lexer lexer = new Lexer(inputStream);
			lineOffset = lexer.getLineOffset();
			parser = new parser(lexer);
			program = (Program) parser.parse().value;
			long time = System.currentTimeMillis() - start;
			result.setParseTime(time);
			root = program.createAst();
		} catch (Exception e) {
			DefaultParserNotice notice = new DefaultParserNotice(this,
					parser.errorValue, parser.errorLine,
					getLineOffset(parser.errorLine) + parser.errorColumn,
					parser.errorValue.length());
			notice.setLevel(ParserNotice.ERROR);
			result.addNotice(notice);
			// result.setError(e);
			// e.printStackTrace();
		}

		support.firePropertyChange(PROPERTY_AST, null, root);
		return result;
	}

	public int getLineOffset(int line) {
		System.out.println("Size is " + lineOffset.size() + ", asks for " + line);
		if (line < 1)
			return 0;
		return ((Integer) lineOffset.get(line - 1)).intValue() + line;
	}

}
