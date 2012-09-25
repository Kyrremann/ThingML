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
package org.fife.rsta.ac.js.ast;

import org.fife.rsta.ac.js.SourceCompletionProvider;
import org.fife.rsta.ac.js.ast.type.TypeDeclaration;
import org.mozilla.javascript.ast.AstNode;


public class JavaScriptFunctionTypeDeclaration extends
		JavaScriptVariableDeclaration {

	private AstNode typeNode;
	
	public JavaScriptFunctionTypeDeclaration(String name, int offset,
			SourceCompletionProvider provider, CodeBlock block) {
		super(name, offset, provider, block);
	}
	
	public void setTypeDeclaration(AstNode typeNode) {
		this.typeNode = typeNode;
	}
	
	
	public TypeDeclaration getTypeDeclaration() {
		return provider.getJavaScriptEngine().getJavaScriptResolver(provider).resolveNode(typeNode);
	}

}
