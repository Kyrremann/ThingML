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
 * Class corresponding to the <code>CONSTANT_Double_info</code> structure.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class ConstantDoubleInfo extends ConstantPoolInfo {

	private int highBytes;
	private int lowBytes;


	/**
	 * Constructor.
	 *
	 * @param highBytes
	 * @param lowBytes
	 */
	public ConstantDoubleInfo(int highBytes, int lowBytes) {
		super(CONSTANT_Double);
		this.highBytes = highBytes;
		this.lowBytes = lowBytes;
	}


	/**
	 * Returns the <code>double</code> value represented by this constant.
	 *
	 * @return The double value.
	 */
	public double getDoubleValue() {
		long bits = (((long)highBytes<<32)) + lowBytes;
		return Double.longBitsToDouble(bits);
	}


	public int getHighBytes() {
		return highBytes;
	}


	public int getLowBytes() {
		return lowBytes;
	}


	/**
	 * Returns a string representation of this object.  Useful for debugging.
	 *
	 * @return A string representation of this object.
	 */
	public String toString() {
		return "[ConstantDoubleInfo: " +
				"value=" + getDoubleValue() +
				"]";
	}


}