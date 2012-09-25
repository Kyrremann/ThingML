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
 * A <code>ConstantPool</code> table entry.<p>
 *
 * See <a href="http://java.sun.com/docs/books/jvms/second_edition/html/ClassFile.doc.html#20080">
 * http://java.sun.com/docs/books/jvms/second_edition/html/ClassFile.doc.html#20080</a>
 * for more information.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public abstract class ConstantPoolInfo implements ConstantTypes {

	private int tag;


	/**
	 * Constructor.
	 */
	public ConstantPoolInfo(int tag) {
		this.tag = tag;
	}


	/**
	 * Returns the tag item for this structure.
	 *
	 * @return The tag item.
	 */
	public int getTag() {
		return tag;
	}


}