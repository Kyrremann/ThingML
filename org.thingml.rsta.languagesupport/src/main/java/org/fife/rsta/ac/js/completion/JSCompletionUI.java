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
package org.fife.rsta.ac.js.completion;


public interface JSCompletionUI {

	static final int LOCAL_VARIABLE_RELEVANCE = 8;
	static final int GLOBAL_VARIABLE_RELEVANCE = 7;
	static final int DEFAULT_VARIABLE_RELEVANCE = 6;
	static final int STATIC_FIELD_RELEVANCE = 5;
	static final int BEAN_METHOD_RELEVANCE = 4;
	static final int DEFAULT_FUNCTION_RELEVANCE = 3;
	static final int GLOBAL_FUNCTION_RELEVANCE = 2;
	static final int BASIC_COMPLETION_RELEVANCE = 1;
	static final int TEMPLATE_RELEVANCE = 0;


}
