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


public class JSString extends JSObject {

    /**
     * Object JSString()
     * 
     * @constructor
     * @extends JSObject
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString() {
    }

    /**
     * static function fromCharCode(charCode1, ...)
     * 
     * @memberOf String
     * @param {Number} charCode
     * @returns {String}
     * @static
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public static JSString fromCharCode(JSNumber charCode){return null;}

    /**
     * Property length
     * 
     * @type Number
     * @memberOf String
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    protected JSNumber length;
    
    /**
     * Property prototype
     * 
     * @type String
     * @memberOf String
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString protype;
    
    /**
     * Property constructor
     * 
     * @type Function
     * @memberOf String
     * @see Function
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    protected JSFunction constructor;


    /**
     * function charAt(position)
     * 
     * @memberOf String
     * @param {Number} position
     * @returns {String}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString charAt(JSNumber position){return null;}

    /**
     * function charCodeAt(position)
     * 
     * @memberOf String
     * @param {Number} position
     * @returns {Number}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSNumber charCodeAt(JSNumber position){return null;}

    /**
     * function concat(value1, ...)
     * 
     * @memberOf String
     * @param {String} value
     * @returns {String}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString concat(JSString value){return null;}

    /**
     * function indexOf(searchString, startPosition)
     * 
     * @memberOf String
     * @param {String} searchString
     * @param {Number} startPosition
     * @returns {Number}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSNumber indexOf(JSNumber searchString, JSNumber startPosition){return null;}

    /**
     * function lastIndexOf(searchString, startPosition)
     * 
     * @memberOf String
     * @param {String} searchString
     * @param {Number} startPosition
     * @returns {Number}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSNumber lastIndexOf(JSNumber searchString, JSNumber startPosition){return null;}

    /**
     * function localeCompare(otherString)
     * 
     * @memberOf String
     * @param {String} otherString
     * @returns {Number}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSNumber localeCompare(JSString otherString){return null;}

    /**
     * function match(regexp)
     * 
     * @memberOf String
     * @param {RegExp} regexp
     * @returns {Array}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString match(JSRegExp regexp){return null;}

    /**
     * function replace(regexp, replaceValue)
     * 
     * @memberOf String
     * @param {RegExp} regexp
     * @param {String} replaceValue
     * @returns {String}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString replace(JSRegExp regexp, JSString replaceValue){return null;}

    /**
     * function search(regexp)
     * 
     * @memberOf String
     * @param {RegExp} regexp
     * @returns {Number}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSNumber search(JSRegExp regexp){return null;}

    /**
     * function slice(start, end)
     * 
     * @memberOf String
     * @param {Number} start
     * @param {Number} end
     * @returns {String}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString slice(JSNumber start, JSNumber end){return null;}

    /**
     * function split(separator, limit)
     * 
     * @memberOf String
     * @param {String} separator
     * @param {Number} limit
     * @returns {Array}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSArray split(JSString separator, JSNumber limit){return null;}

    /**
     * function substring(start, end)
     * 
     * @memberOf String
     * @param {Number} start
     * @param {Number} end
     * @returns {String}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString substring(JSNumber start, JSNumber end){return null;}

    /**
     * function toLowerCase()
     * 
     * @memberOf String
     * @returns {String}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString toLowerCase(){return null;}

    /**
     * function toLocaleLowerCase()
     * 
     * @memberOf String
     * @returns {String}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString toLocaleLowerCase(){return null;}

    /**
     * function toUpperCase()
     * 
     * @memberOf String
     * @returns {String}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString toUpperCase(){return null;}

    /**
     * function toLocaleUpperCase()
     * 
     * @memberOf String
     * @returns {String}
     * @see String
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString toLocaleUpperCase(){return null;}

}
