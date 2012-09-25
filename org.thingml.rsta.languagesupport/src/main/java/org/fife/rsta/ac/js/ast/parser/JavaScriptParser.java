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
package org.fife.rsta.ac.js.ast.parser;

import java.util.Set;

import org.fife.rsta.ac.js.SourceCompletionProvider;
import org.fife.rsta.ac.js.ast.CodeBlock;
import org.mozilla.javascript.ast.AstRoot;


public abstract class JavaScriptParser {
	
	protected SourceCompletionProvider provider;
	protected int dot;
	protected boolean preProcessingMode;
	
	/**
	 * JavaScriptParser constructor
	 * @param provider
	 * @param dot
	 * @param preProcessingMode
	 */
	public JavaScriptParser(SourceCompletionProvider provider, int dot,
			boolean preProcessingMode) {
		this.provider = provider;
		this.dot = dot;
		this.preProcessingMode = preProcessingMode;
	}
	
	/**
	 * Converts AstRoot to CodeBlock 
	 * @param root AstRoot to iterate
	 * @param set completions set
	 * @param entered text entered by user
	 * @return CodeBlock tree 
	 */
	public abstract CodeBlock convertAstNodeToCodeBlock(AstRoot root, Set set, String entered);
}
