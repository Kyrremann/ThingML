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
package org.fife.rsta.ac.java.classreader;

import java.util.*;

import org.fife.rsta.ac.java.classreader.attributes.Code;


/**
 * A <code>Frame</code> contains information on a method being decompiled,
 * similar to a Frame as defined in 3.6 of the JVM spec.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class Frame {

	private Stack operandStack;
	private LocalVarInfo[] localVars;


	/**
	 * Constructor.
	 *
	 * @param code The {@link Code} attribute being decompiled.
	 */
	public Frame(Code code) {

		operandStack = new Stack();

		localVars = new LocalVarInfo[code.getMaxLocals()];
		int i = 0;
		MethodInfo mi = code.getMethodInfo();

		// Instance methods have an implicit first parameter of "this".
		if (!mi.isStatic()) {
			localVars[i++] = new LocalVarInfo("this", true);
		}

		// Name the passed-in local vars by their types. longs and doubles
		// take up two slots.
		String[] paramTypes = mi.getParameterTypes();
		for (int param_i=0; param_i<paramTypes.length; param_i++) {
			String type = paramTypes[param_i];
			if (type.indexOf('.')>-1) { // Class types.
				type = type.substring(type.lastIndexOf('.')+1);
			}
			String name = "localVar_" + type + "_" + param_i;
			localVars[i] = new LocalVarInfo(name, true);
			i++;
			if ("long".equals(type) || "double".equals(type)) {
				i++; // longs and doubles take up two slots.
			}
		}

		// NOTE: Other local vars will still be "null" here!  We need to
		// infer their types from their usage during disassembly/decompilation.
		System.out.println("NOTE: " + (localVars.length-i) + " unknown localVars slots");

	}


	public LocalVarInfo getLocalVar(int index, String defaultType) {
		LocalVarInfo var = localVars[index];
		if (var==null) {
			String name = "localVar_" + defaultType + "_" + index;
			var = new LocalVarInfo(name, false);
			localVars[index] = var;
		}
		else {
			var.alreadyDeclared = true; // May be redundant
		}
		return var;
	}


	public String pop() {
		return (String)operandStack.pop();
	}


	public void push(String value) {
		operandStack.push(value);
	}


	public static class LocalVarInfo {

		private String value;
		private boolean alreadyDeclared;

		public LocalVarInfo(String value, boolean alreadyDeclared) {
			this.value = value;
			this.alreadyDeclared = alreadyDeclared;
		}

		public String getValue() {
			return value;
		}

		public boolean isAlreadyDeclared() {
			return alreadyDeclared;
		}

	}


}