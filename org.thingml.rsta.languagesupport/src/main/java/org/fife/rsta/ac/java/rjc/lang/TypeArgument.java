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
package org.fife.rsta.ac.java.rjc.lang;


public class TypeArgument {

	public static final int NOTHING			= 0;
	public static final int EXTENDS			= 1;
	public static final int SUPER			= 2;

	private Type type;
	private int doesExtend;
	private Type otherType;


	public TypeArgument(Type type) {
		this.type = type;
	}


	public TypeArgument(Type type, int doesExtend, Type otherType) {
		if (doesExtend<0 || doesExtend>2) {
			throw new IllegalArgumentException("Illegal doesExtend: " + doesExtend);
		}
		this.type = type;
		this.doesExtend = doesExtend;
		this.otherType = otherType;
	}


	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (type==null) {
			sb.append('?');
		}
		else {
			sb.append(type.toString());
		}
		if (doesExtend==EXTENDS) {
			sb.append(" extends ");
			sb.append(otherType.toString());
		}
		else if (doesExtend==SUPER) {
			sb.append(" super ");
			sb.append(otherType.toString());
		}
		return sb.toString();
	}


}