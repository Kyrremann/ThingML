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
import org.fife.rsta.ac.js.ast.type.TypeDeclarationFactory;
import org.mozilla.javascript.ast.AstNode;


/**
 * JavaScript Variable Declaration class <code>TypeDeclarations</code>
 */
public class JavaScriptVariableDeclaration {

	private String name;
	private int offset;
	protected TypeDeclaration typeDec;
	protected SourceCompletionProvider provider;

	private boolean reassigned;
	private TypeDeclaration originalTypeDec;
	private CodeBlock block;


	/**
	 * @param name of the variable
	 * @param offset position within script
	 * @param provider JavaScript source provider
	 */
	public JavaScriptVariableDeclaration(String name, int offset,
			SourceCompletionProvider provider, CodeBlock block) {
		this.name = name;
		this.offset = offset;
		this.provider = provider;
		this.block = block;
	}


	/**
	 * Lookup TypeDeclaration from the Rhino <code>AstNode</code>
	 * 
	 * @param typeNode - Rhino AstNode linked to this variable
	 */
	public void setTypeDeclaration(AstNode typeNode) {
		typeDec = provider.getJavaScriptEngine().getJavaScriptResolver(provider)
				.resolveNode(typeNode);
	}

	/**
	 * Set the TypeDeclaration for the AstNode. Stores the original value so it can be reset 
	 * @param typeNode
	 * @param overrideOriginal
	 * @see #resetVariableToOriginalType()
	 */
	public void setTypeDeclaration(AstNode typeNode, boolean overrideOriginal) {
		// check whether the variable has been reassigned already
		if (!reassigned) {
			originalTypeDec = typeDec;
		}

		setTypeDeclaration(typeNode);

		if (overrideOriginal) {
			originalTypeDec = typeDec;
		}
		reassigned = true;

	}

	/**
	 * Resets the TypeDeclaration to the original value 
	 */
	public void resetVariableToOriginalType() {
		if (reassigned) {
			reassigned = false;
			typeDec = originalTypeDec;
		}
		originalTypeDec = null;
	}


	/**
	 * Set TypeDeclaration
	 * 
	 * @param typeDec
	 */
	public void setTypeDeclaration(TypeDeclaration typeDec) {
		this.typeDec = typeDec;
	}


	/**
	 * @return TypeDeclaration for the variable
	 */
	public TypeDeclaration getTypeDeclaration() {
		return typeDec;
	}


	/**
	 * @return JavaScript name for the type declaration e.g String, Number etc..
	 */
	public String getJavaScriptTypeName() {
		TypeDeclaration dec = getTypeDeclaration();
		return dec != null ? dec.getJSName() : TypeDeclarationFactory
				.getDefaultTypeDeclaration().getJSName();
	}


	/**
	 * @return Name of the variable
	 */
	public String getName() {
		return name;
	}


	/**
	 * @return variable position within the script
	 */
	public int getOffset() {
		return offset;
	}
	
	public CodeBlock getCodeBlock() {
		return block;
	}

}