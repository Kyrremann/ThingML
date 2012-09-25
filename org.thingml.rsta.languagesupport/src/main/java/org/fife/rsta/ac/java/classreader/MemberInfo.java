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
package org.fife.rsta.ac.java.classreader;

import java.io.DataInputStream;
import java.io.IOException;

import org.fife.rsta.ac.java.classreader.attributes.AttributeInfo;
import org.fife.rsta.ac.java.classreader.attributes.Signature;


/**
 * Base class for information about members (fields and methods).
 *
 * @author Robert Futrell
 * @version 1.0
 * @see FieldInfo
 * @see MethodInfo
 */
public abstract class MemberInfo {

	/**
	 * The class file in which this method is defined.
	 */
	protected ClassFile cf;

	/**
	 * A mask of flags used to denote access permission to and properties of
	 * this method.
	 */
	private int accessFlags; // u2

	/**
	 * Whether this member is deprecated.
	 */
	private boolean deprecated;

	/**
	 * Attribute marking a member as deprecated.
	 */
	public static final String DEPRECATED			= "Deprecated";

	/**
	 * Attribute containing index of the member's signature.
	 */
	public static final String SIGNATURE			= "Signature";

	/**
	 * Attribute containing runtime-visible annotations.
	 */
	public static final String RUNTIME_VISIBLE_ANNOTATIONS = "RuntimeVisibleAnnotations";


	/**
	 * Constructor.
	 *
	 * @param cf The class file defining this member.
	 */
	protected MemberInfo(ClassFile cf, int accessFlags) {
		this.cf = cf;
		this.accessFlags = accessFlags;
	}


	/**
	 * Returns the access flags for this field.
	 *
	 * @return The access flags, as a bit field.
	 * @see AccessFlags
	 */
	public int getAccessFlags() {
		return accessFlags;
	}


	/**
	 * Returns the parent class file.
	 *
	 * @return The parent class file.
	 */
	public ClassFile getClassFile() {
		return cf;
	}


	/**
	 * Returns the name of this member.
	 *
	 * @return The name of this member.
	 */
	public abstract String getName();


	/**
	 * Returns whether this member is deprecated.
	 *
	 * @return Whether this member is deprecated.
	 */
	public boolean isDeprecated() {
		return deprecated;
	}


	/**
	 * Returns the descriptor of this member.
	 *
	 * @return The descriptor of this member.
	 */
	public abstract String getDescriptor();


	/**
	 * Returns whether this member is final.
	 *
	 * @return Whether this member is final.
	 */
	public boolean isFinal() {
		return (getAccessFlags()&AccessFlags.ACC_FINAL)>0;
	}


	/**
	 * Returns whether this member is static.
	 *
	 * @return Whether this member is static.
	 */
	public boolean isStatic() {
		return (getAccessFlags()&AccessFlags.ACC_STATIC)>0;
	}


	/**
	 * Reads attributes common to all members.  If the specified attribute is
	 * not common to members, the attribute returned is an "unsupported"
	 * attribute.
	 *
	 * @param in
	 * @param attrName
	 * @param attrLength
	 * @return The attribute, or <code>null</code> if it was purposely skipped
	 *         for some reason (known to be useless for our purposes, etc.).
	 * @throws IOException
	 */
	protected AttributeInfo readAttribute(DataInputStream in, String attrName,
										int attrLength) throws IOException {

		AttributeInfo ai = null;

		if (DEPRECATED.equals(attrName)) { // 4.7.10
			// No need to read anything else, attributeLength==0
			deprecated = true;
		}

		else if (SIGNATURE.equals(attrName)) { // 4.8.8
			//System.err.println(">>> " + attributeLength);
			int signatureIndex = in.readUnsignedShort();
			String typeSig = cf.getUtf8ValueFromConstantPool(signatureIndex);
			ai = new Signature(cf, typeSig);
		}

		else if (RUNTIME_VISIBLE_ANNOTATIONS.equals(attrName)) { // 4.8.15
			//String name = getClassFile().getClassName(false) + "." + getName();
			//System.out.println(name + ": Attribute " + attrName + " not supported");
			Util.skipBytes(in, attrLength);
			//ai = null;
		}

		else {
			//String name = getClassFile().getClassName(false) + "." + getName();
			//System.out.println(name + ": Unsupported attribute: " + attrName);
			ai = AttributeInfo.readUnsupportedAttribute(cf, in, attrName,
				attrLength);
		}

		return ai;

	}


}