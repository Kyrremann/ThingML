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
package org.fife.rsta.ac.js;


public class Logger {

	private static boolean DEBUG = false;
	
	static
	{
		DEBUG = Boolean.valueOf(System.getProperty("javascript.debug")).booleanValue(); 
	}
	
	/**
	 * TODO change logging to Log4J?
	 * Log message to console
	 * @param msg
	 */
	public static final void log(String msg) {
		if (DEBUG) {
			System.out.println(msg);
		}
	}
	
	public static final void logError(String msg) {
		System.err.println(msg);
	}
}
