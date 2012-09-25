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

import java.util.ResourceBundle;

import org.fife.rsta.ac.ShorthandCompletionCache;
import org.fife.rsta.ac.js.completion.JavaScriptTemplateCompletion;
import org.fife.rsta.ac.js.completion.JavascriptBasicCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;


/**
 * Cache of template and comment completions for JavaScript.
 *
 * @author Steve
 * @version 1.0
 */
public class JavaScriptShorthandCompletionCache extends ShorthandCompletionCache {

	private static final String MSG = "org.fife.rsta.ac.js.resources";
	private static final ResourceBundle msg = ResourceBundle.getBundle(MSG);


	public JavaScriptShorthandCompletionCache(DefaultCompletionProvider templateProvider, DefaultCompletionProvider commentsProvider, boolean e4xSuppport) {
		
		super(templateProvider, commentsProvider);

		//add basic keywords
		addShorthandCompletion(new JavascriptBasicCompletion(templateProvider, "do"));
        addShorthandCompletion(new JavascriptBasicCompletion(templateProvider, "if"));
        addShorthandCompletion(new JavascriptBasicCompletion(templateProvider, "while"));
        addShorthandCompletion(new JavascriptBasicCompletion(templateProvider, "for"));
        addShorthandCompletion(new JavascriptBasicCompletion(templateProvider, "switch"));
        addShorthandCompletion(new JavascriptBasicCompletion(templateProvider, "try"));
        addShorthandCompletion(new JavascriptBasicCompletion(templateProvider, "catch"));
        addShorthandCompletion(new JavascriptBasicCompletion(templateProvider, "case"));
		
        
        //add template completions
        //iterate array
		String template = "for (var ${i} = 0; ${i} < ${array}.length; ${i}++) {\n\t${cursor}\n}";
		addShorthandCompletion(new JavaScriptTemplateCompletion(templateProvider, "for", "for-loop-array",
				template, msg.getString("for.array.shortDesc"),  msg.getString("for.array.summary")));
		
		//standard for
        template = "for (var ${i} = 0; ${i} < ${10}; ${i}++) {\n\t${cursor}\n}";
        addShorthandCompletion(new JavaScriptTemplateCompletion(templateProvider, "for", "for-loop",
                template, msg.getString("for.loop.shortDesc"),msg.getString("for.loop.summary")));
        
        //for in
        template = "for (var ${iterable_element} in ${iterable})\n{\n\t${cursor}\n}";
        addShorthandCompletion(new JavaScriptTemplateCompletion(templateProvider, "for", "for-loop-in",
                template, msg.getString("for.in.shortDesc"), msg.getString("for.in.summary")));
        
        //e4x specific
        if(e4xSuppport) {
	        //for each
	        template = "for each (var ${iterable_element} in ${iterable})\n{\n\t${cursor}\n}";
	        addShorthandCompletion(new JavaScriptTemplateCompletion(templateProvider, "for", "for-loop-in-each",
	                template, msg.getString("for.in.each.shortDesc"), msg.getString("for.in.each.summary")));
        }

        //do while
		template = "do {\n\t${cursor}\n} while (${condition});";
		addShorthandCompletion(new JavaScriptTemplateCompletion(templateProvider, "do-while",
				"do-loop", template, msg.getString("do.shortDesc"), msg.getString("do.summary")));
		
		//if condition
        template = "if (${condition}) {\n\t${cursor}\n}";
        addShorthandCompletion(new JavaScriptTemplateCompletion(templateProvider, "if", "if-cond",
                template, msg.getString("if.cond.shortDesc"), msg.getString("if.cond.summary")));
        
        //if else condition
        template = "if (${condition}) {\n\t${cursor}\n} else {\n\t\n}";
        addShorthandCompletion(new JavaScriptTemplateCompletion(templateProvider, "if", "if-else",
                template, msg.getString("if.else.shortDesc"), msg.getString("if.else.summary")));
        
        //while condition
        template = "while (${condition}) {\n\t${cursor}\n}";
        addShorthandCompletion(new JavaScriptTemplateCompletion(templateProvider, "while", "while-cond",
                template, msg.getString("while.shortDesc"), msg.getString("while.summary")));
		
        //switch case statement
        template = "switch (${key}) {\n\tcase ${value}:\n\t\t${cursor}\n\t\tbreak;\n\tdefault:\n\t\tbreak;\n}";
        addShorthandCompletion(new JavaScriptTemplateCompletion(templateProvider, "switch", "switch-statement",
                template, msg.getString("switch.case.shortDesc"), msg.getString("switch.case.summary")));
        
        //try catch statement
        template = "try {\n\t ${cursor} \n} catch (${err}) {\n\t\n}";
        addShorthandCompletion(new JavaScriptTemplateCompletion(templateProvider, "try", "try-catch",
                template, msg.getString("try.catch.shortDesc"), msg.getString("try.catch.summary")));
        
        //catch block
        template = "catch (${err}) {\n\t${cursor}\n}";
        addShorthandCompletion(new JavaScriptTemplateCompletion(templateProvider, "catch", "catch-block",
                template, msg.getString("catch.block.shortDesc"), msg.getString("catch.block.summary")));
        
        /** Comments **/
        addCommentCompletion(new BasicCompletion(commentsProvider, "TODO:", null, msg.getString("todo")));
        addCommentCompletion(new BasicCompletion(commentsProvider, "FIXME:", null, msg.getString("fixme")));
	}


}