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
package org.fife.rsta.ac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.fife.ui.autocomplete.AbstractCompletionProvider;
import org.fife.ui.autocomplete.Completion;


/**
 * A cache to store completions for Template completions and Comment
 * completions.  Template completions should extend
 * <code>TemplateCompletion</code> that uses parameterized variables/values.<p> 
 * 
 * While template completion example:
 * <pre>
 * while --&gt; while(condition) {
 *              //cursor here
 *           }
 * </pre>
 * 
 * Comment completion example:
 * <pre>
 * null --&gt; &lt;code&gt;null&lt;/code&gt;
 * </pre> 
 * 
 * This is really a convenient place to store these types of completions that
 * are re-used.
 * 
 * @author Steve
 */
public class ShorthandCompletionCache {
	
	private ArrayList shorthandCompletion = new ArrayList();
	private ArrayList commentCompletion = new ArrayList();
	
	private AbstractCompletionProvider templateProvider, commentProvider;


	public ShorthandCompletionCache(AbstractCompletionProvider templateProvider,
			AbstractCompletionProvider commentProvider) {
		this.templateProvider = templateProvider;
		this.commentProvider = commentProvider;
	}
	
	public void addShorthandCompletion(Completion completion) {
		addSorted(shorthandCompletion, completion);
	}


	private static final void addSorted(List list, Completion completion) {
		int index = Collections.binarySearch(list, completion);
		if (index<0) {
			// index = -insertion_point - 1
			index = -(index+1);
		}
		list.add(index, completion);
	}


	public List getShorthandCompletions() {
		return shorthandCompletion;
	}
	
	public void removeShorthandCompletion(Completion completion) {
		shorthandCompletion.remove(completion);
	}
	
	public void clearCache() {
		shorthandCompletion.clear();
	}
	
	//comments
	public void addCommentCompletion(Completion completion) {
		addSorted(commentCompletion, completion);
	}

	public List getCommentCompletions() {
		return commentCompletion;
	}
	
	public void removeCommentCompletion(Completion completion) {
		commentCompletion.remove(completion);
	}
	
	public AbstractCompletionProvider getTemplateProvider() {
		return templateProvider;
	}
	
	public AbstractCompletionProvider getCommentProvider() {
		return commentProvider;
	}

}