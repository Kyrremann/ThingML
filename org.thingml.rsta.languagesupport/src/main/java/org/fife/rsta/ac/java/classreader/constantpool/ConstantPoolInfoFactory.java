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

import java.io.DataInputStream;
import java.io.IOException;

import org.fife.rsta.ac.java.classreader.*;


public class ConstantPoolInfoFactory implements ConstantTypes {


	/**
	 * Private constructor to prevent instantiation.
	 */
	private ConstantPoolInfoFactory() {
	}


	public static ConstantPoolInfo readConstantPoolInfo(ClassFile cf,
							DataInputStream in) throws IOException {

		ConstantPoolInfo cpi = null;
		int tag = in.read();

		switch (tag) {

			case CONSTANT_Class:
				int nameIndex = in.readUnsignedShort();
				cpi = new ConstantClassInfo(nameIndex);
				break;

			case CONSTANT_Double:
				int highBytes = in.readInt();
				int lowBytes = in.readInt();
				cpi = new ConstantDoubleInfo(highBytes, lowBytes);
				break;

			case CONSTANT_Fieldref:
				int classIndex = in.readUnsignedShort();
				int nameAndTypeIndex = in.readUnsignedShort();
				cpi = new ConstantFieldrefInfo(classIndex, nameAndTypeIndex);
				break;

			case CONSTANT_Float:
				int bytes = in.readInt();
				cpi = new ConstantFloatInfo(bytes);
				break;

			case CONSTANT_Integer:
				bytes = in.readInt();
				cpi = new ConstantIntegerInfo(bytes);
				break;

			case CONSTANT_InterfaceMethodref:
				classIndex = in.readUnsignedShort();
				nameAndTypeIndex = in.readUnsignedShort();
				cpi = new ConstantInterfaceMethodrefInfo(classIndex, nameAndTypeIndex);
				break;

			case CONSTANT_Long:
				highBytes = in.readInt();
				lowBytes = in.readInt();
				cpi = new ConstantLongInfo(highBytes, lowBytes);
				break;

			case CONSTANT_Methodref:
				classIndex = in.readUnsignedShort();
				nameAndTypeIndex = in.readUnsignedShort();
				cpi = new ConstantMethodrefInfo(classIndex, nameAndTypeIndex);
				break;

			case CONSTANT_NameAndType:
				nameIndex = in.readUnsignedShort();
				int descriptorIndex = in.readUnsignedShort();
				cpi = new ConstantNameAndTypeInfo(nameIndex, descriptorIndex);
				break;

			case CONSTANT_String:
				int stringIndex = in.readUnsignedShort();
				cpi = new ConstantStringInfo(cf, stringIndex);
				break;

			case CONSTANT_Utf8:
				int count = in.readUnsignedShort();
				byte[] byteArray = new byte[count];
				in.readFully(byteArray);
				cpi = new ConstantUtf8Info(byteArray);
				break;

			default:
				throw new IOException("Unknown tag for constant pool info: " + tag);

		}

		return cpi;

	}


}