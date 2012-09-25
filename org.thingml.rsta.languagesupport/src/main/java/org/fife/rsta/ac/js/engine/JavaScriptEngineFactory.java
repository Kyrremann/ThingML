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
package org.fife.rsta.ac.js.engine;

import java.util.HashMap;


public class JavaScriptEngineFactory {

	
	public static final String DEFAULT = JSR223JavaScriptEngine.JSR223_ENGINE;
	
	private HashMap supportedEngines = new HashMap();

	private static JavaScriptEngineFactory Instance = new JavaScriptEngineFactory();

	static {
		Instance().addEngine(EMCAJavaScriptEngine.EMCA_ENGINE, new EMCAJavaScriptEngine());
		Instance().addEngine(JSR223JavaScriptEngine.JSR223_ENGINE, new JSR223JavaScriptEngine());
		Instance().addEngine(RhinoJavaScriptEngine.RHINO_ENGINE, new RhinoJavaScriptEngine());
	}


	private JavaScriptEngineFactory() {
	}


	public static JavaScriptEngineFactory Instance() {
		return Instance;
	}


	public JavaScriptEngine getEngineFromCache(String name) {
		if(name == null) {
			name = DEFAULT;
		}
		return (JavaScriptEngine) supportedEngines.get(name);
	}


	public void addEngine(String name, JavaScriptEngine engine) {
		supportedEngines.put(name, engine);
	}


	public void removeEngine(String name) {
		supportedEngines.remove(name);
	}

}
