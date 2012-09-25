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

import org.fife.rsta.ac.java.classreader.ClassFile;
import org.fife.rsta.ac.java.classreader.constantpool.ConstantUtf8Info;


/**
 * The <code>SourceFile</code> attribute, an optional fixed-length attribute
 * in the attributes table of a {@link ClassFile}.  There can be no more than
 * one <code>SourceFile</code> attribute for a given <code>ClassFile</code>.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class SourceFile extends AttributeInfo {

	/**
	 * Index into the constant pool of a {@link ConstantUtf8Info} structure
	 * representing the name of the source file from which this class file
	 * was compiled.
	 */
	private int sourceFileIndex;


	/**
	 * Constructor.
	 *
	 * @param cf The class file.
	 * @param sourceFileIndex Index into the constant pool of a
	 *        {@link ConstantUtf8Info} structure representing the source file
	 *        name.
	 */
	public SourceFile(ClassFile cf, int sourceFileIndex) {
		super(cf);
		this.sourceFileIndex = sourceFileIndex;
	}


	/**
	 * Returns the name of the source file that was compiled to create this
	 * class file.
	 *
	 * @return The name of the source file.
	 */
	public String getSourceFileName() {
		return getClassFile().getUtf8ValueFromConstantPool(sourceFileIndex);
	}


	/**
	 * Returns a string representation of this attribute.  Useful for
	 * debugging.
	 *
	 * @return A string representation of this attribute.
	 */
	public String toString() {
		return "[SourceFile: " +
				"file=" + getSourceFileName() +
				"]";
	}


}