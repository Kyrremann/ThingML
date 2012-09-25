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

import org.fife.ui.autocomplete.FunctionCompletion;


public class FunctionDeclaration {

	private FunctionCompletion fc;
	private int offset;


	public FunctionDeclaration(FunctionCompletion fc, int offset) {
		this.fc = fc;
		this.offset = offset;
	}


	public FunctionCompletion getFunction() {
		return fc;
	}


	public int getOffset() {
		return offset;
	}


}