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
package org.fife.rsta.ac.sh;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fife.rsta.ac.OutputCollector;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;


/**
 * Completion for Unix shell "functions" (command line utilities).
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class ShellFunctionCompletion extends FunctionCompletion {


	/**
	 * Constructor.
	 *
	 * @param provider
	 * @param name
	 * @param returnType
	 */
	public ShellFunctionCompletion(CompletionProvider provider, String name,
			String returnType) {
		super(provider, name, returnType);
	}


	/**
	 * {@inheritDoc}
	 */
	public String getSummary() {

		String summary = null;
		if (ShellCompletionProvider.getUseLocalManPages()) {
			summary = getSummaryFromManPage();
		}
		//else { // Don't use else - fallback for if man isn't found
		if (summary==null) {
			summary = super.getSummary();
		}

		return summary;

	}


	/**
	 * Gets a summary of this function from the local system's man pages.
	 *
	 * @return The summary.
	 */
	private String getSummaryFromManPage() {

		Process p = null;

		String[] cmd = { "/usr/bin/man", getName() };
		try {
			p = Runtime.getRuntime().exec(cmd);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}

		// TODO: Launch waitFor() in a thread and interrupt after set time
		OutputCollector stdout = new OutputCollector(p.getInputStream());
		Thread t = new Thread(stdout);
		t.start();
		int rc = 0;
		try {
			rc = p.waitFor();
			t.join();
			//System.out.println(rc);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

		StringBuffer output = null;
		if (rc==0) {
			output = stdout.getOutput();
			if (output!=null && output.length()>0) {
				output = manToHtml(output);
			}
		}

		return output==null ? null : output.toString();

	}


	private static final StringBuffer manToHtml(CharSequence text) {
//		text = text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		Pattern p = Pattern.compile("(?:_\\010.)+|(?:(.)\\010\\1)+");//"(?:.\\010.)+");
		Matcher m = p.matcher(text);
		StringBuffer sb = new StringBuffer("<html><pre>");
		while ((m.find())) {
			System.out.println("... found '" + m.group() + "'");
			String group = m.group();
			if (group.startsWith("_")) {
				sb.append("<u>");
				String replacement = group.replaceAll("_\\010", "");
				replacement = quoteReplacement(replacement);
				m.appendReplacement(sb, replacement);
				System.out.println("--- '" + replacement);
				sb.append("</u>");
			}
			else {
				String replacement = group.replaceAll(".\\010.", "");
				replacement = quoteReplacement(replacement);
				m.appendReplacement(sb, replacement);
				System.out.println("--- '" + replacement);
			}
		}
		m.appendTail(sb);
		//System.out.println(sb.toString());
		return sb;
	}

	// Matcher.quoteReplacement() in 1.5.
	private static String quoteReplacement(String text) {
		if (text.indexOf('$')>-1 || text.indexOf('\\')>-1) {
			StringBuffer sb = new StringBuffer();
			for (int i=0; i<text.length(); i++) {
				char ch = text.charAt(i);
				if (ch=='$' || ch=='\\') {
					sb.append('\\');
				}
				sb.append(ch);
			}
			text = sb.toString();
		}
		return text;
	}


}