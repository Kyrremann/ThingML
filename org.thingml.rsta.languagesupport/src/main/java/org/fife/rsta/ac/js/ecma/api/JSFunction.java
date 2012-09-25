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

public class JSFunction extends JSObject {

    /**
     * Object Function()
     * @constructor
     * @extends Object
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
    */
    public JSFunction(){
	
    }
    
    /**
     * property length
     * @type    Number
     * @since   Standard ECMA-262 3rd. Edition 
     * @since   Level 2 Document Object Model Core Definition.    
    */ 
   protected JSNumber length;
   
   /**
    * Property prototype
    * 
    * @type Function
    * @memberOf Function
    * @see Function
    * @since Standard ECMA-262 3rd. Edition
    * @since Level 2 Document Object Model Core Definition.
    */
   public JSFunction protype;
   
   /**
    * Property constructor
    * 
    * @type Function
    * @memberOf Function
    * @see Function
    * @since Standard ECMA-262 3rd. Edition
    * @since Level 2 Document Object Model Core Definition.
    */
   protected JSFunction constructor;
   
   
    
    /**
     * function apply (thisObject, argArray)
     * @param {Object} thisObject
     * @param {Array} argArray
     * @returns {Object}
     * @since   Standard ECMA-262 3rd. Edition 
     * @since   Level 2 Document Object Model Core Definition.
     */ 
    public JSObject apply(JSObject thisObject, JSArray argArray){return null;}
    
    /**
      * function call (thisObject, args)
      * @param {Object} thisObject
      * @param {Object} args
      * @returns {Object}
      * @since   Standard ECMA-262 3rd. Edition 
      * @since   Level 2 Document Object Model Core Definition.    
     */ 
    public JSObject call(JSObject thisObject, JSObject args){return null;}
    
    

}
