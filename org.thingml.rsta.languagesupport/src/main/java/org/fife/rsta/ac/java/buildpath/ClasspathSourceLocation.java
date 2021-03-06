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
import java.io.InputStream;
import java.io.InputStreamReader;

import org.fife.rsta.ac.java.classreader.ClassFile;
import org.fife.rsta.ac.java.rjc.ast.CompilationUnit;
import org.fife.rsta.ac.java.rjc.lexer.Scanner;
import org.fife.rsta.ac.java.rjc.parser.ASTFactory;


/**
 * Represents Java source files somewhere on the classpath.  This might be
 * somewhat of a unique situation, since often source isn't on the classpath,
 * only class files are.  However, there may be times when you want to ship
 * both the classes and source for a library and put them on your classpath
 * for simplicity of integrating with this code completion library.  In such a
 * case, you would use a <code>ClasspathLibraryInfo</code> and use this class
 * for the source location.<p>
 *
 * This class has no state; any classes it's asked about, it assumes it can
 * find the corresponding .java file somewhere on the classpath using the
 * class's ClassLoader.
 *
 * @author Robert Futrell
 * @version 1.0
 * @see ClasspathLibraryInfo
 */
public class ClasspathSourceLocation implements SourceLocation {


	/**
	 * {@inheritDoc}
	 */
	public CompilationUnit getCompilationUnit(ClassFile cf) throws IOException {

		CompilationUnit cu = null;

		String res = cf.getClassName(true).replace('.', '/') + ".java";
		InputStream in = getClass().getClassLoader().getResourceAsStream(res);
		if (in!=null) {
			Scanner s = new Scanner(new InputStreamReader(in));
			cu = new ASTFactory().getCompilationUnit(res, s);
		}

		return cu;

	}


	/**
	 * {@inheritDoc}
	 */
	public String getLocationAsString() {
		return null;
	}


}