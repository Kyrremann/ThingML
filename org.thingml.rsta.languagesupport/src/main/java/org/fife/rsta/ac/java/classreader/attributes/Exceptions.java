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
import org.fife.rsta.ac.java.classreader.constantpool.*;


/**
 * Implementation of the "<code>Exceptions</code>" attribute found in
 * {@link MethodInfo}s.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class Exceptions extends AttributeInfo {

	/**
	 * The method this attribute is describing.
	 */
	private MethodInfo mi;

	/**
	 * Indices into the constant pool of {@link ConstantClassInfo}s, each
	 * representing a class type that this method is declared to throw.
	 */
	private int[] exceptionIndexTable;


	/**
	 * Constructor.
	 *
	 * @param mi The method this attribute is describing.
	 */
	public Exceptions(MethodInfo mi, int[] exceptionIndexTable) {
		super(mi.getClassFile());
		this.exceptionIndexTable = exceptionIndexTable;
	}


	/**
	 * Returns the fully-qualified name of the specified exception.
	 *
	 * @param index The index of the exception whose name to retrieve.
	 * @return The name of the exception.
	 */
	public String getException(int index) {
		ClassFile cf = getClassFile();
		ConstantPoolInfo cpi = cf.getConstantPoolInfo(
										exceptionIndexTable[index]);
		ConstantClassInfo cci = (ConstantClassInfo)cpi;
		int nameIndex = cci.getNameIndex();
		String name = cf.getUtf8ValueFromConstantPool(nameIndex);
		return name.replace('/', '.');
	}


	/**
	 * Returns the number of exceptions this attribute knows about.
	 *
	 * @return The number of exceptions.
	 */
	public int getExceptionCount() {
		return exceptionIndexTable==null ? 0 : exceptionIndexTable.length;
	}


	/**
	 * Returns information about the method this attribute is describing.
	 *
	 * @return The method information.
	 */
	public MethodInfo getMethodInfo() {
		return mi;
	}


}