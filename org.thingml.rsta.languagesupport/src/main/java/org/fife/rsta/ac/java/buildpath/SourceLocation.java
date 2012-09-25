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
package org.fife.rsta.ac.java.buildpath;

import java.io.IOException;

import org.fife.rsta.ac.java.classreader.ClassFile;
import org.fife.rsta.ac.java.rjc.ast.CompilationUnit;


/**
 * Represents the location of Java source, either in a zip file (src.zip),
 * a flat file (source in a project's source folder), or in some other location.
 *
 * @author Robert Futrell
 * @version 1.0
 * @see DirSourceLocation
 * @see ZipSourceLocation
 * @see ClasspathSourceLocation
 */
public interface SourceLocation {


	/**
	 * Returns an AST for the specified class file.
	 *
	 * @param cf The class file to grab the AST for.
	 * @return The AST, or <code>null</code> if it cannot be found.
	 * @throws IOException If an IO error occurs.
	 */
	CompilationUnit getCompilationUnit(ClassFile cf) throws IOException;


	/**
	 * Returns a string representation of this source location.  For locations
	 * on disk such as zip files or directories, this should be the full path
	 * to the resource.
	 *
	 * @return The location of this source as a string, or <code>null</code> if
	 *         it is not an accessible location.
	 */
	public String getLocationAsString();


}