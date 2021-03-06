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
package tests;

import java.io.*;
import java.util.List;
import static java.lang.Math.*;


/**
 * A simple class used in JUnit tests to validate the following features
 * of the Java parser when parsing a class file:
 * 
 * <ul>
 *    <li>Import statements</li>
 *    <li>Class variables</li>
 *    <li>Local variables</li>
 *    <li>Method parameters</li>
 *    <li>Member modifiers</li>
 * </li>
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class SimpleClass {

	/**
	 * A member int variable.
	 */
	public int classInt1;

	protected int classInt2;

	/**
	 * A string member variable.
	 */
	/*
	 * This should not interfere with the javadoc for this member.
	 */
	private String classStr1;

	private List<String> list;


	public SimpleClass() {
		list = new ArrayList<String>();
	}


	/**
	 * Returns a value.
	 *
	 * @return A value.
	 */
	public int getValue() {
		return classInt1;
	}


	/**
	 * Contains local variables that are a little harder to parse.
	 *
	 * @param newValue
	 * @param unused
	 */
	public void localVarsComplex(String newValue, float unused) {
		int foo = 5;
		double val1 = computeValue(foo, "yes"), val2, val3 = 3f, val4;
		classStr1 = newValue;
	}


	/**
	 * Contains local variables that are easy to parse.
	 */
	public void localVarsSimple() {
		int temp = classInt1;
		classInt1 = classInt2;
		classInt2 = temp;
		boolean unnecessary = true;
		if (unnecessary) {
			float f = 3.4f;
		}
	}


}