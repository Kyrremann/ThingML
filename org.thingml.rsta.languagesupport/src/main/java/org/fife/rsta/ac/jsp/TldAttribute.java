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
package org.fife.rsta.ac.jsp;

import org.fife.rsta.ac.html.AttributeCompletion;
import org.fife.ui.autocomplete.ParameterizedCompletion.Parameter;


/**
 * An attribute of an element defined in a TLD.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class TldAttribute extends AttributeCompletion {

	public boolean required;
	public boolean rtexprvalue;


	public TldAttribute(JspCompletionProvider provider,
			TldAttributeParam param) {
		super(provider, param);
	}


	public static class TldAttributeParam extends Parameter {

		private boolean required;
		private boolean rtextprvalue;
		
		public TldAttributeParam(Object type, String name, boolean required,
									boolean rtextprvalue) {
			super(type, name);
			this.required = required;
			this.rtextprvalue = rtextprvalue;
		}

		public boolean isRequired() {
			return required;
		}

		public boolean getRtextprvalue() {
			return rtextprvalue;
		}

	}


}