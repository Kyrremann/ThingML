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

import java.io.*;

import org.fife.rsta.ac.java.classreader.ClassFile;
import org.fife.rsta.ac.java.classreader.Util;


public abstract class AttributeInfo {

	private ClassFile cf;
	public int attributeNameIndex; // u2


	protected AttributeInfo(ClassFile cf) {
		this.cf = cf;
	}


	public ClassFile getClassFile() {
		return cf;
	}


	/**
	 * Returns the name of this attribute.
	 *
	 * @return The name of this attribute.
	 */
	public String getName() {
		return cf.getUtf8ValueFromConstantPool(attributeNameIndex);
	}


	/**
	 * Reads an unknown/unsupported attribute from an input stream.
	 *
	 * @param cf The class file containing the attribute.
	 * @param in The input stream to read from.
	 * @param attrName The name of the attribute.
	 * @param attrLength The length of the data to read from <code>in</code>,
	 *        in bytes.
	 * @return The attribute.
	 * @throws IOException If an IO error occurs.
	 */
	public static UnsupportedAttribute readUnsupportedAttribute(ClassFile cf,
										DataInputStream in, String attrName,
										int attrLength) throws IOException {
		/*
		int[] info = new int[attrLength];
		for (int i=0; i<attrLength; i++) {
			info[i] = in.readUnsignedByte();
		}
		*/
		Util.skipBytes(in, attrLength);
		return new UnsupportedAttribute(cf, attrName);
	}


}