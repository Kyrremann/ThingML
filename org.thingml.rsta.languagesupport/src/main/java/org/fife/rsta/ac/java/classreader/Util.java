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


/**
 * Utility methods for this package.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class Util implements AccessFlags {


	/**
	 * Private constructor to prevent instantiation.
	 */
	private Util() {
	}


	/**
	 * Returns whether an object has default scope.
	 *
	 * @return Whether an object has default scope.
	 * @see #isDefault(int)
	 * @see #isPrivate(int)
	 * @see #isPublic(int)
	 */
	public static boolean isDefault(int accessFlags) {
		int access = ACC_PUBLIC | ACC_PROTECTED | ACC_PRIVATE;
		return (accessFlags&access)==0;
	}


	/**
	 * Returns whether an object has private scope.
	 *
	 * @return Whether an object has private scope.
	 * @see #isDefault(int)
	 * @see #isPrivate(int)
	 * @see #isPublic(int)
	 */
	public static boolean isPrivate(int accessFlags) {
		return (accessFlags&ACC_PRIVATE)>0;
	}


	/**
	 * Returns whether an object has protected scope.
	 *
	 * @return Whether an object has protected scope.
	 * @see #isDefault(int)
	 * @see #isPrivate(int)
	 * @see #isPublic(int)
	 */
	public static boolean isProtected(int accessFlags) {
		return (accessFlags&ACC_PROTECTED)>0;
	}


	/**
	 * Returns whether an object has public scope.
	 *
	 * @return Whether an object has public scope.
	 * @see #isDefault(int)
	 * @see #isPrivate(int)
	 * @see #isPublic(int)
	 */
	public static boolean isPublic(int accessFlags) {
		return (accessFlags&ACC_PUBLIC)>0;
	}


	/**
	 * Fully skips a given number of bytes in an input stream.
	 *
	 * @param in The input stream.
	 * @param count The number of bytes to skip.
	 * @throws IOException If an IO error occurs.
	 */
	public static void skipBytes(DataInputStream in, int count)
												throws IOException {
		int skipped = 0;
		while (skipped<count) {
			skipped += in.skipBytes(count-skipped);
		}
	}


}