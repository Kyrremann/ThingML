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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.fife.rsta.ac.java.classreader.ClassFile;
import org.fife.rsta.ac.java.rjc.ast.CompilationUnit;
import org.fife.rsta.ac.java.rjc.lexer.Scanner;
import org.fife.rsta.ac.java.rjc.parser.ASTFactory;


/**
 * Represents Java source in a directory, such as in a project's source folder.
 * 
 * @author Robert Futrell
 * @version 1.0
 */
public class DirSourceLocation implements SourceLocation {

	private File dir;


	/**
	 * Constructor.
	 *
	 * @param dir The directory containing the source files.
	 */
	public DirSourceLocation(String dir) {
		this(new File(dir));
	}


	/**
	 * Constructor.
	 *
	 * @param dir The directory containing the source files.
	 */
	public DirSourceLocation(File dir) {
		this.dir = dir;
	}


	/**
	 * {@inheritDoc}
	 */
	public CompilationUnit getCompilationUnit(ClassFile cf) throws IOException {

		CompilationUnit cu = null;

		String entryName = cf.getClassName(true);
		entryName = entryName.replace('.', '/');
		entryName += ".java";
		//System.out.println("DEBUG: entry name: " + entryName);
		File file = new File(dir, entryName);
		if (!file.isFile()) {
			// Be nice and check for "src/" subdirectory
			file = new File(dir, "src/" + entryName);
		}

		if (file.isFile()) {
			BufferedReader r = new BufferedReader(new FileReader(file));
			try {
				Scanner s = new Scanner(r);
				cu = new ASTFactory().getCompilationUnit(entryName, s);
				//System.out.println("DEBUG: cu: " + cu);
			} finally {
				r.close();
			}
		}

		return cu;

	}


	/**
	 * {@inheritDoc}
	 */
	public String getLocationAsString() {
		return dir.getAbsolutePath();
	}


}