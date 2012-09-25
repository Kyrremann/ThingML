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


public class JSObject {

    /**
     * Object Object()
     * 
     * @constructor
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSObject() {
    }
    
    /**
     * Property prototype
     * 
     * @type Object
     * @memberOf Object
     * @see Object
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSObject protype;
    
    /**
     * Property constructor
     * 
     * @type Function
     * @memberOf Object
     * @see Function
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    protected JSFunction constructor;

    /**
     * function toString()
     * 
     * @memberOf Object
     * @returns {String}
     * @see JSObject
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public String toString(){return null;}

    /**
     * function toLocaleString()
     * 
     * @memberOf Object
     * @returns {String}
     * @see Object
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSString toLocaleString(){return null;}

    /**
     * function valueOf()
     * 
     * @memberOf Object
     * @returns {Object}
     * @see Object
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSObject valueOf(){return null;};

    /**
     * function hasOwnProperty(name)
     * 
     * @memberOf Object
     * @param {String} name
     * @returns {Boolean}
     * @see Object
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSBoolean hasOwnProperty(){return null;}

    /**
     * function isPrototypeOf(o)
     * 
     * @memberOf Object
     * @param {Object} o
     * @returns {Boolean}
     * @see Object
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSBoolean isPrototypeOf(JSObject o){return null;}

    /**
     * function propertyIsEnumerable(name)
     * 
     * @memberOf Object
     * @param {Object} name
     * @returns {Boolean}
     * @see Object
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
     */
    public JSBoolean propertyIsEnumerable(JSObject name){return null;}

}
