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


/**
 * Class/interface access flag masks.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public interface AccessFlags {

	/**
	 * Declared public; may be accessed from outside its package.
	 */
	public static final int ACC_PUBLIC			= 0x0001;

	/**
	 * Declared private; usable only within the defining class.
	 */
	public static final int ACC_PRIVATE			= 0x0002;

	/**
	 * Declared protected; may be accessed within subclasses.
	 */
	public static final int ACC_PROTECTED		= 0x0004;

	/**
	 * Declared static.
	 */
	public static final int ACC_STATIC			= 0x0008;

	/**
	 * Declared final; no subclasses allowed.
	 */
	public static final int ACC_FINAL			= 0x0010;

	/**
	 * Treat superclass methods specially when invoked by the
	 * <em>invokespecial</em> instruction.
	 */
	/*
	 * NOTE: This is the same value as ACC_SYNCHRONIZED.
	 */
	public static final int ACC_SUPER			= 0x0020;

	/**
	 * Declared synchronized; invocation is wrapped in a monitor block.
	 */
	/*
	 * NOTE: This is the same value as ACC_SUPER.
	 */
	public static final int ACC_SYNCHRONIZED	= 0x0020;

	/**
	 * Declared volatile; cannot be cached.
	 */
	public static final int ACC_VOLATILE		= 0x0040;

	/**
	 * Declared transient; not written or read by a persistent object manager.
	 */
	public static final int ACC_TRANSIENT		= 0x0080;

	/**
	 * Declared native; implemented in a language other than Java.
	 */
	public static final int ACC_NATIVE			= 0x0100;

	/**
	 * Is an interface, not a class.
	 */
	public static final int ACC_INTERFACE		= 0x0200;

	/**
	 * Declared abstract; may not be instantiated.
	 */
	public static final int ACC_ABSTRACT		= 0x0400;

	/**
	 * Declared strictfp; floating-point mode is FP-strict.
	 */
	public static final int ACC_STRICT			= 0x0800;

	/**
	 * Declared <code>synthetic</codeL; not present in the source code.
	 */
	public static final int ACC_SYNTHETIC		= 0x1000;

	/**
	 * Declared as an annotation type.
	 */
	public static final int ACC_ANNOTATION		= 0x2000;

	/**
	 * Declared as an enum type.
	 */
	public static final int ACC_ENUM			= 0x4000;

}