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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Reads plain text files enumerating classes to take from the classpath and
 * add to a {@link ClasspathLibraryInfo}.  Files should have a format similar
 * to the following:
 * 
 * <pre>
 * - com.mycompany.pkg1.Class1
 * Class2
 * Class3
 * - com.mycompany.pkg2.Foo
 * Bar
 * - aonther.pkg.Utils
 * ...
 * </pre>
 * 
 * Such files are expected to be UTF-8. The exact file structure is as follows:
 * <ul>
 *    <li>Lines that start with a "<code>-</code>" denote a fully-qualified
 *        class, interface, or enum name.
 *    <li>Lines following a line starting with "<code>-</code>" are simply a
 *        class, interface, or enum name, and are assumed to be in the same
 *        package as the previous class on the "<code>-</code>" line.
 *    <li>Blank lines and lines starting with "<code>#</code>" are ignored.
 * </ul>
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class ClassEnumerationReader {


	/**
	 * Private constructor to prevent instantiation.
	 */
	private ClassEnumerationReader() {
	}


	/**
	 * Returns the list of classes specified in the given stream.
	 *
	 * @param in The input stream to read from.  This will be closed when
	 *        this method returns.
	 * @return The list of class names read.
	 * @throws IOException If an IO error occurs.
	 */
	public static List getClassNames(InputStream in) throws IOException {

		String lastPkg = null;
		String line = null;
		List classNames = new ArrayList();

		BufferedReader r = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		try {

			while ((line=r.readLine())!=null) {

				// Skip blank lines and comments
				line = line.trim();
				if (line.length()==0 || line.charAt(0)=='#') {
					continue;
				}

				// A new fully-qualified class name
				if (line.charAt(0)=='-') {
					line = line.substring(1).trim();
					classNames.add(line);
					int lastDot = line.lastIndexOf('.');
					lastPkg = line.substring(0, lastDot+1);
				}

				// Just a class name
				else {
					String className = line;
					if (lastPkg!=null) {
						className = lastPkg + className;
					}
					classNames.add(className);
				}

			}

		} finally {
			r.close();
		}

		return classNames;

	}

	
}