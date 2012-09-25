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
 * Represents a class or interface.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class ConstantClassInfo extends ConstantPoolInfo {

	/**
	 * An index into the constant_pool table.  The entry at this index
	 * must be a <code>CONSTANT_Utf8_info</code> structure representing
	 * a valid, fully-qualified class or interface name encoded in internal
	 * form.
	 */
	private int nameIndex;


	/**
	 * Constructor.
	 *
	 * @param nameIndex The index into the constant pool containing a
	 *        {@link ConstantUtf8Info} representing the fully-qualified
	 *        class or interface name, encoded in internal form.
	 */
	public ConstantClassInfo(int nameIndex) {
		super(CONSTANT_Class);
		this.nameIndex = nameIndex;
	}


	/**
	 * Returns the index into the constant pool table for a
	 * <code>ConstantUtf8Info</code> structure representing a valid,
	 * fully-qualified class or interface name, encoded in internal form.
	 *
	 * @return The index into the constant pool table.
	 */
	public int getNameIndex() {
		return nameIndex;
	}


	/**
	 * Returns a string representation of this object.  Useful for debugging.
	 *
	 * @return A string representation of this object.
	 */
	public String toString() {
		return "[ConstantClassInfo: " +
				"nameIndex=" + getNameIndex() +
				"]";
	}


}