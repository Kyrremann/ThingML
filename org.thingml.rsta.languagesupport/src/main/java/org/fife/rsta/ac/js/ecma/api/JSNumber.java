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


public class JSNumber extends JSObject {

    /**
     * Object Number()
     * 
     * @constructor
     * @extends Object
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSNumber() {
    }
    
    /**
     * Property prototype
     * 
     * @type Number
     * @memberOf Number
     * @see Number
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSNumber protype;
    
    /**
     * Property constructor
     * 
     * @type Function
     * @memberOf Number
     * @see Function
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    protected JSFunction constructor;

    /**
     * property MIN_VALUE
     * 
     * @type Number
     * @memberOf Number
     * @static
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public static JSNumber MIN_VALUE;

    /**
     * property MAX_VALUE
     * 
     * @type Number
     * @memberOf Number
     * @static
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public static JSNumber MAX_VALUE;

    /**
     * property NaN
     * 
     * @type Number
     * @memberOf Number
     * @static
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public static JSNumber NaN;

    /**
     * property NEGATIVE_INFINITY
     * 
     * @type Number
     * @memberOf Number
     * @static
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public static JSNumber NEGATIVE_INFINITY;

    /**
     * property POSITIVE_INFINITY
     * 
     * @type Number
     * @memberOf Number
     * @static
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public static JSNumber POSITIVE_INFINITY;

    /**
     * function toFixed(fractionDigits)
     * 
     * @memberOf Number
     * @param {Number} fractionDigits
     * @returns {String}
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString toFixed(JSNumber fractionDigits){return null;}

    /**
     * function toExponential(fractionDigits)
     * 
     * @memberOf Number
     * @param {Number} fractionDigits
     * @returns {String}
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString toExponential(JSNumber fractionDigits){return null;}

    /**
     * function toPrecision(precision)
     * 
     * @memberOf Number
     * @param {Number} fractionDigits
     * @returns {String}
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString toPrecision(JSNumber fractionDigits){return null;}
}
