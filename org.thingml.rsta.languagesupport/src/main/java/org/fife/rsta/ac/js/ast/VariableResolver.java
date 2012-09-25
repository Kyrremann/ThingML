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

import java.util.HashMap;
import java.util.Iterator;

import org.fife.rsta.ac.js.ast.type.TypeDeclaration;


/**
 * Cache Local and System scope variables Local scope variables are cleared each
 * time the <code>SourceCompletionProvider</code> finishes parsing the script
 * System scope variables will not be cleared
 */
public class VariableResolver {

	// HashMap of local variables mapped Name --> JSVariableDeclaration
	private HashMap localVariables = new HashMap();
	// pre processing variables - these are set when pre-processing
	private HashMap preProcessedVariables = new HashMap();
	// HashMap of system variables mapped Name --> JSVariableDeclaration
	// system variables do not get cleared as they are always available to the
	// system
	private HashMap systemVariables = new HashMap();
	
	private HashMap localFunctions = new HashMap();
	private HashMap preProcessedFunctions = new HashMap();


	/**
	 * Add local scope variable to cache.
	 * 
	 * @param declaration variable to add
	 */
	public void addLocalVariable(JavaScriptVariableDeclaration declaration) {
		localVariables.put(declaration.getName(), declaration);
	}


	/**
	 * Add pre-processing scope variable to cache.
	 * 
	 * @param declaration variable to add
	 */
	public void addPreProcessingVariable(
			JavaScriptVariableDeclaration declaration) {
		preProcessedVariables.put(declaration.getName(), declaration);
	}


	/**
	 * Add system scope variable to cache
	 * 
	 * @param declaration variable to add
	 */
	public void addSystemVariable(JavaScriptVariableDeclaration declaration) {
		systemVariables.put(declaration.getName(), declaration);
	}


	/**
	 * remove pre-processing variable from system variable cache
	 * 
	 * @param name of the system variable to remove
	 */
	public void removePreProcessingVariable(String name) {
		preProcessedVariables.remove(name);
	}


	/**
	 * remove system variable from system variable cache
	 * 
	 * @param name of the system variable to remove
	 */
	public void removeSystemVariable(String name) {
		systemVariables.remove(name);
	}


	/**
	 * Find JSVariableDeclaration and check is in scope of caret position
	 * 
	 * @param name
	 * @param dot
	 * @return JSVariableDeclaration from the name
	 */
	public JavaScriptVariableDeclaration findDeclaration(String name, int dot) {
		JavaScriptVariableDeclaration findDeclaration = findDeclaration(
				localVariables, name, dot);
		// test whether this was found and then try pre-processing variable
		findDeclaration = findDeclaration == null ? findDeclaration(
				preProcessedVariables, name, dot) : findDeclaration;
		// last chance... look in system variables
		return findDeclaration == null ? findDeclaration(systemVariables, name,
				dot) : findDeclaration;
	}


	/**
	 * Find JSVariableDeclaration and check the scope of the caret position
	 * 
	 * @param name
	 * @param dot
	 * @return JSVariableDeclaration from the name
	 */
	private JavaScriptVariableDeclaration findDeclaration(HashMap variables,
			String name, int dot) {
		JavaScriptVariableDeclaration dec = (JavaScriptVariableDeclaration) variables
				.get(name);

		if (dec != null) {
			if (dec.getCodeBlock() == null || dec.getCodeBlock().contains(dot)) {
				int decOffs = dec.getOffset();
				if (dot <= decOffs) {
					return dec;
				}
			}
		}
		return null;
	}


	/**
	 * Find the <code>TypeDeclaration</code> for the variable and check the
	 * scope of the caret position
	 * 
	 * @param name name of variable
	 * @param dot position
	 * @return TypeDeclaration from the name
	 */
	public TypeDeclaration getTypeDeclarationForVariable(String name, int dot) {
		JavaScriptVariableDeclaration dec = findDeclaration(name, dot);
		return dec != null ? dec.getTypeDeclaration() : null;
	}


	/**
	 * Clear all local scope variables
	 */
	public void resetLocalVariables() {
		localVariables.clear();
		localFunctions.clear();
	}


	public void resetPreProcessingVariables(boolean clear) {
		if (clear) {
			preProcessedVariables.clear();
			preProcessedFunctions.clear();
		}
		else {
			for (Iterator i = preProcessedVariables.values().iterator(); i
					.hasNext();) {
				JavaScriptVariableDeclaration dec = (JavaScriptVariableDeclaration) i
						.next();
				dec.resetVariableToOriginalType();
			}
		}
	}


	public void resetSystemVariables() {
		systemVariables.clear();
	}


	/**
	 * Resolve the entered text by chopping up the text and working from left to
	 * right, resolving each type in turn
	 * 
	 * @param entered
	 * @param provider
	 * @param dot
	 * @return TypeDeclaration for variable name
	 */
	public TypeDeclaration resolveType(String varName, int dot) {

		// just look up variable
		return getTypeDeclarationForVariable(varName, dot);
	}
	
	
	public void addLocalFunction(JavaScriptFunctionDeclaration func)
	{
		localFunctions.put(func.getName(), func);
	}
	
	public JavaScriptFunctionDeclaration findFunctionDeclaration(String name)
	{
		JavaScriptFunctionDeclaration dec = (JavaScriptFunctionDeclaration) localFunctions.get(name);
		if(dec == null) {
			dec = (JavaScriptFunctionDeclaration) preProcessedFunctions.get(name);
		}
		return dec;	
	}
	
	/**
	 * Add pre-processing scope function to cache.
	 * 
	 * @param function variable to add
	 */
	public void addPreProcessingFunction(
			JavaScriptFunctionDeclaration func) {
		preProcessedFunctions.put(func.getName(), func);
	}

}
