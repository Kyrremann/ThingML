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
package org.fife.rsta.ac.perl;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.Element;

import org.fife.rsta.ac.OutputCollector;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;


/**
 * Listens to stderr from Perl to determine syntax errors in code.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class PerlOutputCollector extends OutputCollector {

	private PerlParser parser;
	private DefaultParseResult result;
	private Element root;

	private static final Pattern ERROR_PATTERN = Pattern.compile(" at .+ line (\\d+)\\.$");


	/**
	 * Constructor.
	 *
	 * @param in The input stream.
	 */
	public PerlOutputCollector(InputStream in, PerlParser parser,
								DefaultParseResult result, Element root) {
		super(in);
		this.parser = parser;
		this.result = result;
		this.root = root;
	}


	/**
	 * {@inheritDoc}
	 */
	protected void handleLineRead(String line) throws IOException {

		Matcher m = ERROR_PATTERN.matcher(line);

		if (m.find()) {

			line = line.substring(0, line.length()-m.group().length());

			int lineNumber = Integer.parseInt(m.group(1)) - 1;
			Element elem = root.getElement(lineNumber);
			int start = elem.getStartOffset();
			int end = elem.getEndOffset();

			DefaultParserNotice pn = new DefaultParserNotice(
					parser, line, lineNumber, start, end-start);

			result.addNotice(pn);

		}

	}


}