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
package org.fife.rsta.demo;

import java.io.File;
import javax.swing.filechooser.FileFilter;


/**
 * A file filter for opening files with a specific extension.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class ExtensionFileFilter extends FileFilter {

	private String desc;
	private String ext;


	/**
	 * Constructor.
	 *
	 * @param desc A description of the file type.
	 * @param ext The extension of the file type.
	 */
	public ExtensionFileFilter(String desc, String ext) {
		this.desc = desc;
		this.ext = ext;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean accept(File f) {
		return f.isDirectory() || f.getName().endsWith(ext);
	}


	/**
	 * {@inheritDoc}
	 */
	public String getDescription() {
		return desc + " (*." + ext + ")";
	}


}