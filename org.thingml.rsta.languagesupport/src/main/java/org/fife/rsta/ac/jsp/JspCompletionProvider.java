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
package org.fife.rsta.ac.jsp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.fife.rsta.ac.html.AttributeCompletion;
import org.fife.rsta.ac.html.HtmlCompletionProvider;
import org.fife.rsta.ac.jsp.TldAttribute.TldAttributeParam;
import org.fife.ui.autocomplete.MarkupTagCompletion;


/**
 * Completion provider for JSP.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class JspCompletionProvider extends HtmlCompletionProvider {

	/**
	 * Mapping of prefixes to TLD's.
	 */
	private Map prefixToTld;


	public JspCompletionProvider() {

		prefixToTld = new HashMap();

		String fileName = File.separatorChar=='/' ?
				"/users/robert/struts-2.2.3/lib/struts2-core-2.2.3.jar" :
				"c:/dev/struts/struts-2.2.3/lib/struts2-core-2.2.3.jar";
		File file = new File(fileName);
		if (!file.exists()) {
			file = new File("C:/temp/struts2-core-2.2.1.jar");
		}

		try {
			prefixToTld.put("s", new TldFile(this, file));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		setAutoActivationRules(false, "<:");

	}


	/**
	 * Overridden to handle JSP tags on top of standard HTML tags.
	 */
	protected List getAttributeCompletionsForTag(String tagName) {

		List list = super.getAttributeCompletionsForTag(tagName);

		if (list==null) {

			int colon = tagName.indexOf(':');
			if (colon>-1) {

				String prefix = tagName.substring(0, colon);
				tagName = tagName.substring(colon+1);

				TldFile tldFile = (TldFile)prefixToTld.get(prefix);
				if (tldFile!=null) {
					List attrs = tldFile.getAttributesForTag(tagName);
					if (attrs!=null && attrs.size()>-1) {
						list = new ArrayList();
						for (int i=0; i<attrs.size(); i++) {
							TldAttributeParam param = (TldAttributeParam)attrs.get(i);
							list.add(new AttributeCompletion(this, param));
						}
					}
				}

			}

		}

		return list;

	}


	/**
	 * Overridden to include JSP-specific tags in addition to the standard
	 * HTML tags.
	 *
	 * @return The list of tags.
	 */
	protected List getTagCompletions() {

		List completions = new ArrayList(super.getTagCompletions());

		for (Iterator i=prefixToTld.entrySet().iterator(); i.hasNext(); ) {
			Map.Entry entry = (Map.Entry)i.next();
			String prefix = (String)entry.getKey();
			TldFile tld = (TldFile)entry.getValue();
			for (int j=0; j<tld.getElementCount(); j++) {
				TldElement elem = tld.getElement(j);
				MarkupTagCompletion mtc = new MarkupTagCompletion(this,
						prefix + ":" + elem.getName());
				mtc.setDescription(elem.getDescription());
				completions.add(mtc);
			}
		}

		Collections.sort(completions);
		return completions;

	}


	/**
	 * Overridden to load <code>jsp:*</code> tags also.
	 */
	protected void initCompletions() {

		super.initCompletions();

		// Load our JSP completions, but remember the basic HTML ones too.
		try {
			loadFromXML("data/jsp.xml");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		// The completions array is expected to be sorted alphabetically.
		// We must re-sort since we added to it.
		Collections.sort(completions, comparator);

	}


	protected boolean isValidChar(char ch) {
		return super.isValidChar(ch) || ch==':';
	}


}