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
package org.fife.rsta.ac.js.ast.jsType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.fife.rsta.ac.js.Logger;
import org.fife.rsta.ac.js.SourceCompletionProvider;
import org.fife.rsta.ac.js.ast.type.TypeDeclaration;
import org.fife.rsta.ac.js.completion.JSCompletion;
import org.fife.ui.autocomplete.FunctionCompletion;


public class JSR223Type extends JavaScriptType {

	public JSR223Type(TypeDeclaration type) {
		super(type);
	}


	/**
	 * @param completionLookup
	 * @return JSCompletion using lookup name
	 * @see JSCompletion
	 */
	protected JSCompletion _getCompletion(String completionLookup,
			SourceCompletionProvider provider) {
		JSCompletion completion = (JSCompletion) typeCompletions
				.get(completionLookup);
		if (completion != null) {
			return completion;
		}
		// else
		if (completionLookup.indexOf('(') != -1) {
			// must be a function, so compare function strings
			// get a list of best fit methods
			Logger.log("Completion Lookup : " + completionLookup);
			JavaScriptFunctionType javaScriptFunctionType = JavaScriptFunctionType
					.parseFunction(completionLookup, provider);

			JSCompletion[] matches = getPotentialLookupList(javaScriptFunctionType
					.getName());

			// iterate through types and check best fit parameters
			int bestFitIndex = -1;
			int bestFitWeight = -1;
			Logger.log("Potential matches : " + matches.length);
			for (int i = 0; i < matches.length; i++) {
				Logger.log("Potential match : " + matches[i].getLookupName());
				JavaScriptFunctionType matchFunctionType = JavaScriptFunctionType
						.parseFunction(matches[i].getLookupName());
				Logger.log("Matching against completion: " + completionLookup);
				int weight = matchFunctionType.compare(javaScriptFunctionType,
						provider);
				Logger.log("Weight: " + weight);
				if (weight < JavaScriptFunctionType.CONVERSION_NONE
						&& (weight < bestFitWeight || bestFitIndex == -1)) {
					bestFitIndex = i;
					bestFitWeight = weight;
				}
			}
			if (bestFitIndex > -1) {
				Logger
						.log("BEST FIT: "
								+ matches[bestFitIndex].getLookupName());
				return matches[bestFitIndex];
			}
		}

		return null;
	}

	private JSCompletion[] getPotentialLookupList(String name)
	{
		//get a list of all potential matches, including extended
		ArrayList completionMatches = new ArrayList();
		getPotentialLookupList(name, completionMatches, this);
		return (JSCompletion[]) completionMatches.toArray(new JSCompletion[completionMatches.size()]);
	}

	// get a list of all potential method matches
	private void getPotentialLookupList(String name, ArrayList completionMatches, JavaScriptType type) {
		
		Map typeCompletions = type.typeCompletions;
		
		for (Iterator i = typeCompletions.keySet().iterator(); i.hasNext();) {
			String key = (String) i.next();
			if (key.startsWith(name)) {
				JSCompletion completion = (JSCompletion) typeCompletions
						.get(key);
				if (completion instanceof FunctionCompletion) {
					completionMatches.add(completion);
				}
			}
		}
		
		//loop through extended and add it's methods too recursively
		for(Iterator extended = type.getExtendedClasses().iterator(); extended.hasNext();) {
			JavaScriptType extendedType = (JavaScriptType) extended.next();
			getPotentialLookupList(name, completionMatches, extendedType);
		}

		
	}

}
