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
package org.fife.rsta.ac.java.classreader.attributes;

import org.fife.rsta.ac.java.classreader.*;


/**
 * An attribute that is unknown/unsupported by this decompiler.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class UnsupportedAttribute extends AttributeInfo {

	private String name;
	//private int[] info;


	/**
	 * Constructor.
	 *
	 * @param cf The class file.
	 */
	public UnsupportedAttribute(ClassFile cf, String name/*, int[] info*/) {
		super(cf);
		this.name = name;
		//this.info = info;
	}

/*
	public int[] getInfo() {
		return info;
	}
*/

	public String getName() {
		return name;
	}


	/**
	 * Returns a string representation of this attribute.  Useful for
	 * debugging.
	 *
	 * @return A string representation of this attribute.
	 */
	public String toString() {
		return "[UnsupportedAttribute: " +
				"name=" + getName() +
				"]";
	}


}