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
package org.fife.rsta.ac.js.ecma.api;

public class JSRegExp extends JSObject {

	/**
	 * Object RegExp()
	 * 
	 * @super Object
	 * @constructor
	 * @memberOf RegExp
	 * @since Standard ECMA-262 3rd. Edition
	 * @since Level 2 Document Object Model Core Definition.
	 */
	public JSRegExp() {
	};
	
	/**
    * Property prototype
    * 
    * @type RegExp
    * @memberOf RegExp
    * @see RegExp
    * @since Standard ECMA-262 3rd. Edition
    * @since Level 2 Document Object Model Core Definition.
    */
   public JSRegExp protype;
   
   /**
    * Property constructor
    * 
    * @type Function
    * @memberOf RegExp
    * @see Function
    * @since Standard ECMA-262 3rd. Edition
    * @since Level 2 Document Object Model Core Definition.
    */
   protected JSFunction constructor;


	/**
	 * function exec(string)
	 * 
	 * @param {String} string
	 * @returns {Array}
	 * @type Array
	 * @memberOf RegExp
	 * @since Standard ECMA-262 3rd. Edition
	 * @since Level 2 Document Object Model Core Definition.
	 */
	public JSArray exec(String string){return null;}


	/**
	 * function test(string)
	 * 
	 * @param {String} string
	 * @returns {Boolean}
	 * @type Boolean
	 * @memberOf RegExp
	 * @since Standard ECMA-262 3rd. Edition
	 * @since Level 2 Document Object Model Core Definition.
	 */
	public JSBoolean test(String string){return null;}


	/**
	 * property source
	 * 
	 * @type String
	 * @memberOf RegExp
	 * @since Standard ECMA-262 3rd. Edition
	 * @since Level 2 Document Object Model Core Definition.
	 */
	protected JSString source;

	/**
	 * property global
	 * 
	 * @type Boolean
	 * @memberOf RegExp
	 * @since Standard ECMA-262 3rd. Edition
	 * @since Level 2 Document Object Model Core Definition.
	 */
	protected JSBoolean global;

	/**
	 * property ignoreCase
	 * 
	 * @type Boolean
	 * @memberOf RegExp
	 * @since Standard ECMA-262 3rd. Edition
	 * @since Level 2 Document Object Model Core Definition.
	 */
	protected JSBoolean ignoreCase;

	/**
	 * property multiline
	 * 
	 * @type Boolean
	 * @memberOf RegExp
	 * @since Standard ECMA-262 3rd. Edition
	 * @since Level 2 Document Object Model Core Definition.
	 */
	protected JSBoolean multiline;

	/**
	 * property lastIndex
	 * 
	 * @type Number
	 * @memberOf RegExp
	 * @since Standard ECMA-262 3rd. Edition
	 * @since Level 2 Document Object Model Core Definition.
	 */
	protected JSNumber lastIndex;

}
