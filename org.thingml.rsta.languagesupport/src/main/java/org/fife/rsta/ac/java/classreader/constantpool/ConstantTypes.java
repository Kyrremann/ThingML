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
 * Constant types used by {@link ConstantPoolInfo}s.
 *
 * @author Robert Futrell
 * @version 1.0
 */
interface ConstantTypes {

	public static final int CONSTANT_Class				= 7;

	public static final int CONSTANT_Fieldref			= 9;

	public static final int CONSTANT_Methodref			= 10;

	public static final int CONSTANT_InterfaceMethodref	= 11;

	public static final int CONSTANT_String				= 8;

	public static final int CONSTANT_Integer			= 3;

	public static final int CONSTANT_Float				= 4;

	public static final int CONSTANT_Long				= 5;

	public static final int CONSTANT_Double				= 6;

	public static final int CONSTANT_NameAndType		= 12;

	public static final int CONSTANT_Utf8				= 1;

}