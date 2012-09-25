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
 * Class corresponding to a <code>CONSTANT_InterfaceMethodref_info</code>
 * structure.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class ConstantInterfaceMethodrefInfo extends ConstantPoolInfo {


	private int classIndex;

	private int nameAndTypeIndex;


	/**
	 * Constructor.
	 *
	 * @param classIndex
	 * @param nameAndTypeIndex
	 */
	public ConstantInterfaceMethodrefInfo(int classIndex, int nameAndTypeIndex){
		super(CONSTANT_InterfaceMethodref);
		this.classIndex = classIndex;
		this.nameAndTypeIndex = nameAndTypeIndex;
	}


	public int getClassIndex() {
		return classIndex;
	}


	public int getNameAndTypeIndex() {
		return nameAndTypeIndex;
	}


	/**
	 * Returns a string representation of this object.  Useful for debugging.
	 *
	 * @return A string representation of this object.
	 */
	public String toString() {
		return "[ConstantInterfaceMethodrefInfo: " +
		"classIndex=" + getClassIndex() +
		"; nameAndTypeIndex=" + getNameAndTypeIndex() +
		"]";
	}


}