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
package org.fife.rsta.ac.js.ast.type;

public class TypeDeclaration {

	private String pkg;
	private String apiName;
	private String jsName;
	private boolean staticsOnly;
	private boolean supportsBeanProperties;


	public TypeDeclaration(String pkg, String apiName, String jsName, boolean staticsOnly, boolean supportsBeanProperties) {
		this.staticsOnly = staticsOnly;
		this.pkg = pkg;
		this.apiName = apiName;
		this.jsName = jsName;
		this.supportsBeanProperties = supportsBeanProperties;
	}
	
	public TypeDeclaration(String pkg, String apiName, String jsName, boolean staticsOnly) {
		this(pkg, apiName, jsName, staticsOnly, true);
	}
	
	public TypeDeclaration(String pkg, String apiName, String jsName) {
		this(pkg, apiName, jsName, false, true);
	}


	public String getPackageName() {
		return pkg;
	}


	public String getAPITypeName() {
		return apiName;
	}


	public String getJSName() {
		return jsName;
	}


	public String getQualifiedName() {
		return pkg != null && pkg.length() > 0 ? (pkg + '.' + apiName) : apiName;
	}
	
	public boolean isStaticsOnly() {
		return staticsOnly;
	}
	
	public void setStaticsOnly(boolean staticsOnly) {
		this.staticsOnly = staticsOnly;
	}

	public void setSupportsBeanProperties(boolean supportsBeanProperties){
		this.supportsBeanProperties = supportsBeanProperties;
	}
	
	public boolean supportsBeanProperties() {
		return supportsBeanProperties;
	}

	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		
		if (obj instanceof TypeDeclaration) {
			TypeDeclaration dec = (TypeDeclaration) obj;
			return getQualifiedName().equals(dec.getQualifiedName()) &&
			isStaticsOnly() == dec.isStaticsOnly();
		}

		return super.equals(obj);
	}


	/**
	 * Overridden since {@link #equals(Object)} is overridden.
	 *
	 * @return The hash code.
	 */
	public int hashCode() {
		int hash = 7;
		hash = 31 * new Boolean(staticsOnly).hashCode();
		hash = 31 * hash + getQualifiedName().hashCode();
		return hash;
	}


}