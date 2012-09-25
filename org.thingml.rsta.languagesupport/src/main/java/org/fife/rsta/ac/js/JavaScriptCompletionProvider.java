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
package org.fife.rsta.ac.js;

import java.util.Iterator;

import org.fife.rsta.ac.ShorthandCompletionCache;
import org.fife.rsta.ac.java.JarManager;
import org.fife.ui.autocomplete.AbstractCompletionProvider;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.LanguageAwareCompletionProvider;
import org.mozilla.javascript.ast.AstRoot;


/**
 * Completion provider for JavaScript.
 * 
 * @author Robert Futrell
 * @version 1.0
 */
public class JavaScriptCompletionProvider extends
		LanguageAwareCompletionProvider {

	/**
	 * The AST for the JS.
	 */
	private AstRoot astRoot;

	/**
	 * The provider used for source code, kept here since it's used so much.
	 */
	private SourceCompletionProvider sourceProvider;
	
	private JavaScriptLanguageSupport languageSupport;


	public JavaScriptCompletionProvider(JarManager jarManager, JavaScriptLanguageSupport languageSupport) {
		this(new SourceCompletionProvider(), jarManager, languageSupport);
	}


	public JavaScriptCompletionProvider(SourceCompletionProvider provider,
			JarManager jarManager, JavaScriptLanguageSupport languageSupport) {
		super(provider);
		this.sourceProvider = (SourceCompletionProvider) getDefaultCompletionProvider();
		this.sourceProvider.setJarManager(jarManager);
		this.languageSupport = languageSupport;
		
		setShorthandCompletionCache(new JavaScriptShorthandCompletionCache(sourceProvider, new DefaultCompletionProvider(), languageSupport.isXmlAvailable()));
		sourceProvider.setParent(this);
	}


	/**
	 * Returns the AST for the JavaScript in the editor.
	 * 
	 * @return The AST.
	 */
	public synchronized AstRoot getASTRoot() {
		return astRoot;
	}


	public JarManager getJarManager() {
		return ((SourceCompletionProvider) getDefaultCompletionProvider())
				.getJarManager();
	}
	
	public JavaScriptLanguageSupport getLanguageSupport() {
		return languageSupport;
	}
	
	/**
	 * Set short hand completion cache
	 */
	public void setShorthandCompletionCache(ShorthandCompletionCache shorthandCache)
	{
		sourceProvider.setShorthandCache(shorthandCache);
		//reset comment completions too
		setCommentCompletions(shorthandCache);
	}
	
	/**
	 * load the comment completions from the short hand cache
	 * @param shorthandCache
	 */
	private void setCommentCompletions(ShorthandCompletionCache shorthandCache)
	{
		AbstractCompletionProvider provider = shorthandCache.getCommentProvider();
		if(provider != null) {
			for(Iterator i = shorthandCache.getCommentCompletions().iterator(); i.hasNext();) {
				Completion c = (Completion)i.next();
				provider.addCompletion(c);
			}
			setCommentCompletionProvider(provider);
		}
	}


	/**
	 * Sets the AST for the JavaScript in this editor.
	 * 
	 * @param root The AST.
	 */
	public synchronized void setASTRoot(AstRoot root) {
		this.astRoot = root;
	}


}