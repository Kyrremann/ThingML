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
package org.fife.rsta.ac.java;

import java.awt.Graphics;

import org.fife.ui.autocomplete.Completion;


/**
 * Interface for Java source code completions.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public interface JavaSourceCompletion extends Completion {


	/**
	 * Force subclasses to override equals().
	 * TODO: Remove me
	 */
	public boolean equals(Object obj);


	/**
	 * Used by {@link JavaCellRenderer} to render this completion choice.
	 *
	 * @param g
	 * @param x
	 * @param y
	 * @param selected
	 */
	public void rendererText(Graphics g, int x, int y, boolean selected);


}