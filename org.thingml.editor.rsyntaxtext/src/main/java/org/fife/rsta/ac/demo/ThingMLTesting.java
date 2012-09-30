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
package org.fife.rsta.ac.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.resource.thingml.IThingmlTextToken;
import org.sintef.thingml.resource.thingml.mopp.ThingmlAntlrScanner;
import org.sintef.thingml.resource.thingml.mopp.ThingmlLexer;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResourceFactory;
import org.thingml.cgenerator.CGenerator;

public class ThingMLTesting {

	public void testCodeGeneration() throws IOException {

		// Register the generated package and the XMI Factory
		EPackage.Registry.INSTANCE.put(ThingmlPackage.eNS_URI,
				ThingmlPackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"thingml", new ThingmlResourceFactory());

		String configPath = "/home/kyrremann/workspace/fork/ThingML/org.thingml.samples/src/main/thingml/samples/_arduino/blink.thingml";
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = URI.createFileURI(configPath);
		Resource model = resourceSet.createResource(uri);
		model.load(null);

		File dir = new File("test_out");
		if (!dir.exists()) {
			dir.mkdir();
		}

		try {
			Map<Configuration, String> ccode = CGenerator
					.compileAllArduinoJava((ThingMLModel) model.getContents()
							.get(0));
			for (Configuration t : ccode.keySet()) {
				System.out.println(" -> Writing file " + t.getName() + ".pde");
				PrintWriter w = new PrintWriter(new FileWriter("test_out/"
						+ new File(t.getName() + ".pde")));
				w.println(ccode.get(t));
				w.close();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void testParsing() {

		ThingmlLexer lexer = new ThingmlLexer();
		ThingmlAntlrScanner scanner = new ThingmlAntlrScanner(lexer);
		String blink = "import \"../hardware/bricks/led.thingml\"\n\nthing Blink includes LedMsgs, TimerMsgs {\nrequired port HW {\nsends led_toggle, timer_start\nreceives timer_timeout\n}\n\nstatechart BlinkImpl init Blinking {\nstate Blinking {\non entry HW!timer_start (1000)\ntransition -> Blinking\nevent HW?timer_timeout\naction HW!led_toggle ()\n}\n}\n}";
		scanner.setText(blink);
		System.out.println(blink);

		IThingmlTextToken token = scanner.getNextToken();
		while (token != null) {
			// int type = getType(token.getName());
			System.out.printf("Token: %s\nName: %s\nText: %s\nLine: %s\nOffset: %s\nColumn: %s\n\n", token, token.getName(), token.getText(), token.getLine(), token.getOffset(), token.getColumn());
			token = scanner.getNextToken();
		}
	}

	private int getType(String name) {
		System.out.println(name);

		if (name.equals("SL_COMMENT")) {
			return -1; // COMMENT
		} else if (name.equals("ML_COMMENT")) {
			return -1; // COMMENT
		} else if (name.equals("ANNOTATION")) {
			return -1; // ANNOTATION
		} else if (name.equals("STRING_LITERAL")) {
			return -1; // STRING
		} else if (name.equals("2")) { // _@name
			return -1;
		} else if (name.equals("1")) { // Some(e)
			return -1;
		}

		return -1; // DEFAULT
	}
}
