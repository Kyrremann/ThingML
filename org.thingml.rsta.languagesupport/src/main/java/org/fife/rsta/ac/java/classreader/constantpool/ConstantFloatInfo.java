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
package org.fife.rsta.ac.java.classreader.constantpool;


/**
 * Class corresponding to the <code>CONSTANT_Float_info</code> structure.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class ConstantFloatInfo extends ConstantPoolInfo {

	private int bytes; // u4


	/**
	 * Constructor.
	 *
	 * @param bytes
	 */
	public ConstantFloatInfo(int bytes) {
		super(CONSTANT_Float);
		this.bytes = bytes;
	}


	public long getBytes() {
		return bytes;
	}


	/**
	 * Returns the float value represented.
	 *
	 * @return The float value.
	 */
	public float getFloatValue() {
		return Float.intBitsToFloat(bytes);
	}


	/**
	 * Returns a string representation of this object.  Useful for debugging.
	 *
	 * @return A string representation of this object.
	 */
	public String toString() {
		return "[ConstantFloatInfo: " +
				"value=" + getFloatValue() +
				"]";
	}


}