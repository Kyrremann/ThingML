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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.fife.rsta.ac.java.classreader.ClassFile;
import org.fife.rsta.ac.java.rjc.ast.CompilationUnit;
import org.fife.rsta.ac.java.rjc.lexer.Scanner;
import org.fife.rsta.ac.java.rjc.parser.ASTFactory;


/**
 * Represents source inside a zip or jar file.  The source can be either in
 * a "<code>src/</code>" subfolder, or at the root level of the archive.  This
 * class is useful for the JDK or other libraries that come with a
 * <code>src.zip</code> file (<code>src.jar</code> on OS X).
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class ZipSourceLocation implements SourceLocation {

	private File archive;


	/**
	 * Constructor.
	 *
	 * @param archive The archive containing the source.  This should be an
	 *        absolute path to ensure correctness.
	 */
	public ZipSourceLocation(String archive) {
		this(new File(archive));
	}


	/**
	 * Constructor.
	 *
	 * @param archive The archive containing the source.
	 */
	public ZipSourceLocation(File archive) {
		this.archive = archive;
	}


	/**
	 * {@inheritDoc}
	 */
	public CompilationUnit getCompilationUnit(ClassFile cf) throws IOException {

		CompilationUnit cu = null;

		ZipFile zipFile = new ZipFile(archive);

		try {

			String entryName = cf.getClassName(true).replaceAll("\\.", "/");
			entryName += ".java";
			//System.out.println("DEBUG: entry name: " + entryName);
			ZipEntry entry = zipFile.getEntry(entryName);
			if (entry == null) {
				// Seen in some src.jar files, for example OS X's src.jar
				entry = zipFile.getEntry("src/" + entryName);
			}

			if (entry != null) {
				InputStream in = zipFile.getInputStream(entry);
				Scanner s = new Scanner(new InputStreamReader(in));
				cu = new ASTFactory().getCompilationUnit(entryName, s);
			}

		} finally {
			zipFile.close(); // Closes the input stream too
		}

		return cu;

	}


	/**
	 * {@inheritDoc}
	 */
	public String getLocationAsString() {
		return archive.getAbsolutePath();
	}


}