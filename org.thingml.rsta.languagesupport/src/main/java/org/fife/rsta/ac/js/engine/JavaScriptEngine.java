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
package org.fife.rsta.ac.js.engine;

import org.fife.rsta.ac.js.SourceCompletionProvider;
import org.fife.rsta.ac.js.ast.jsType.JavaScriptTypesFactory;
import org.fife.rsta.ac.js.ast.parser.JavaScriptParser;
import org.fife.rsta.ac.js.resolver.JavaScriptResolver;


/**
 * JavaScript Engine Interface used for resolving Types
 * SourceCompletionProvider needs a JavaScriptEngine for creating the following:
 * 
 *  JavaScriptResolver
 *  JavaScriptTypesFactory
 *  JavaScriptParser
 */
public interface JavaScriptEngine {

	
	/**
	 * @param provider SourceCompletionProvider
	 * @return JavaScriptResolver used to resolve JavaScriptType and TypeDeclaration
	 */
	public JavaScriptResolver getJavaScriptResolver(SourceCompletionProvider provider);
	
	/**
	 * 
	 * @param provider SourceCompletionProvider
	 * @return JavaScriptTypesFactory that holds a cache of JavaScriptType
	 */
	public JavaScriptTypesFactory getJavaScriptTypesFactory(SourceCompletionProvider provider);
	
	/**
	 * 
	 * @param provider SourceCompletionProvider
	 * @param dot caret position
	 * @param preProcessingMode flag whether processing script before JTextComponent's text within SourceCompletionProvider  
	 * @return JavaScriptParser that converts AstRoot to CodeBlock
	 */
	public JavaScriptParser getParser(SourceCompletionProvider provider, int dot, boolean preProcessingMode);
	
}
