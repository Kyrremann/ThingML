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

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.fife.rsta.ac.thingml.syntaxtree.Program;
import org.fife.rsta.ac.thingml.thingmlparser.Lexer;
import org.fife.rsta.ac.thingml.thingmlparser.parser;
import org.fife.rsta.ac.thingml.tree.ThingMLTree;
import org.fife.rsta.ac.thingml.tree.ThingMLTreeNode;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;
import org.fife.ui.rsyntaxtextarea.parser.ParserNotice;
import org.sintef.thingml.resource.thingml.IThingmlTextDiagnostic;
import org.sintef.thingml.resource.thingml.IThingmlTextToken;
import org.sintef.thingml.resource.thingml.mopp.ThingmlAntlrScanner;
import org.sintef.thingml.resource.thingml.mopp.ThingmlLexer;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResource;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import javax.swing.text.BadLocationException;

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
		ThingmlResource resource = null;
		// Program program;
		long start = 0;

		try {
			start = System.currentTimeMillis();
			resource = new ThingmlResource(URI.createURI("http://thingml.org"));
			ResourceSetImpl resourceSetImpl = new ResourceSetImpl();
			resourceSetImpl.getResources().add(resource);
			InputStream inputStream;

			inputStream = new ByteArrayInputStream(document.getText(0,
					document.getLength()).getBytes());
			resource.load(inputStream, null);

			if (!resource.getErrors().isEmpty())
				throw new ParseException("", -1);

			long time = System.currentTimeMillis() - start;
			System.out.println("TIME: " + time);
			result.setParseTime(time);
			// root = createAst(document);
			ThingMLTree tree = new ThingMLTree(resource.getAllContents());
			root = tree.getRoot();
			System.out.println();
			int lineCount = document.getDefaultRootElement().getElementCount();
			result.setParsedLines(0, lineCount - 1);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			IThingmlTextDiagnostic diagnostic = (IThingmlTextDiagnostic) resource
					.getErrors().get(0);
			int offset = getIndex(document, diagnostic.getLine(),
					diagnostic.getColumn());
			DefaultParserNotice notice = new DefaultParserNotice(this,
					diagnostic.getMessage(), diagnostic.getLine(), offset,
					getLength(diagnostic.getMessage()));
			notice.setLevel(ParserNotice.ERROR);
			result.addNotice(notice);
			// result.setError(e);
			// e.printStackTrace();
		}

		support.firePropertyChange(PROPERTY_AST, null, root);
		return result;
	}

	private ThingMLTreeNode createAst(RSyntaxDocument document)
			throws BadLocationException {
		ThingmlLexer lexer = new ThingmlLexer();
		ThingmlAntlrScanner scanner = new ThingmlAntlrScanner(lexer);
		scanner.setText(document.getText(0, document.getLength()));
		IThingmlTextToken token = scanner.getNextToken();
		while (token != null) {

			token = scanner.getNextToken();
		}
		return new ThingMLTreeNode("ROOT");
	}

	public int getIndex(RSyntaxDocument doc, int line, int column) {
		int lineStart = doc.getDefaultRootElement().getElement(line - 1)
				.getStartOffset();
		return lineStart + column;
	}

	public int getLength(String message) {
		// TODO: Not a proper way to find the length
		return message.split("\"")[1].split(" ")[0].length();
	}
}
