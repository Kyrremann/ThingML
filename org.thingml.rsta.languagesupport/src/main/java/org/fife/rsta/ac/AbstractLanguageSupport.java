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
package org.fife.rsta.ac;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ListCellRenderer;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;


/**
 * A base class for language support implementations.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public abstract class AbstractLanguageSupport implements LanguageSupport {

	/**
	 * Map of all text areas using this language support to their installed
	 * {@link AutoCompletion} instances.  This should be maintained by
	 * subclasses by adding to, and removing from, it in their
	 * {@link #install(org.fife.ui.rsyntaxtextarea.RSyntaxTextArea)} and
	 * {@link #uninstall(org.fife.ui.rsyntaxtextarea.RSyntaxTextArea)} methods.
	 */
	private Map textAreaToAutoCompletion;

	/**
	 * Whether auto-completion is enabled for this language.
	 */
	private boolean autoCompleteEnabled;

	/**
	 * Whether auto-activation for completion choices is enabled for this
	 * language.  Note that this parameter only matters if
	 * {@link #autoCompleteEnabled} is <code>true</code>.
	 */
	private boolean autoActivationEnabled;

	/**
	 * The delay for auto-activation, in milliseconds.  This parameter is only
	 * honored if both {@link #autoCompleteEnabled} and
	 * {@link #autoActivationEnabled} are <code>true</code>.
	 */
	private int autoActivationDelay;

	/**
	 * Whether parameter assistance should be enabled for this language.
	 */
	private boolean parameterAssistanceEnabled;

	/**
	 * Whether the description window is displayed when the completion list
	 * window is displayed.
	 */
	private boolean showDescWindow;

	/**
	 * The default renderer for the completion list.
	 */
	private ListCellRenderer renderer;


	/**
	 * Constructor.
	 */
	protected AbstractLanguageSupport() {
		setDefaultCompletionCellRenderer(null); // Force default
		textAreaToAutoCompletion = new HashMap();
		autoCompleteEnabled = true;
		autoActivationEnabled = false;
		autoActivationDelay = 300;
	}


	/**
	 * Creates an auto-completion instance pre-configured and usable by
	 * most <code>LanguageSupport</code>s.
	 *
	 * @param p The completion provider.
	 * @return The auto-completion instance.
	 */
	protected AutoCompletion createAutoCompletion(CompletionProvider p) {
		AutoCompletion ac = new AutoCompletion(p);
		ac.setListCellRenderer(getDefaultCompletionCellRenderer());
		ac.setAutoCompleteEnabled(isAutoCompleteEnabled());
		ac.setAutoActivationEnabled(isAutoActivationEnabled());
		ac.setAutoActivationDelay(getAutoActivationDelay());
		ac.setParameterAssistanceEnabled(isParameterAssistanceEnabled());
		ac.setShowDescWindow(getShowDescWindow());
		return ac;
	}


	/**
	 * Creates the default cell renderer to use when none is specified.
	 * Subclasses can override this method if there is a "better" default
	 * renderer for a specific language.
	 *
	 * @return The default renderer for the completion list.
	 */
	protected ListCellRenderer createDefaultCompletionCellRenderer() {
		return new DefaultListCellRenderer();
	}


	/**
	 * {@inheritDoc}
	 */
	public int getAutoActivationDelay() {
		return autoActivationDelay;
	}


	/**
	 * Returns the auto completion instance used by a text area.
	 *
	 * @param textArea The text area.
	 * @return The auto completion instance, or <code>null</code> if none
	 *         is installed on the text area.
	 */
	protected AutoCompletion getAutoCompletionFor(RSyntaxTextArea textArea) {
		return (AutoCompletion)textAreaToAutoCompletion.get(textArea);
	}


	/**
	 * {@inheritDoc}
	 */
	public ListCellRenderer getDefaultCompletionCellRenderer() {
		return renderer;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean getShowDescWindow() {
		return showDescWindow;
	}


	/**
	 * Returns the text areas with this language support currently installed.
	 *
	 * @return The text areas.
	 */
	protected Set getTextAreas() {
		return textAreaToAutoCompletion.keySet();
	}


	/**
	 * Registers an auto-completion instance.  This should be called by
	 * subclasses in their
	 * {@link #install(org.fife.ui.rsyntaxtextarea.RSyntaxTextArea)} methods
	 * so that this language support can update all of them at once.
	 *
	 * @param textArea The text area that just installed the auto completion.
	 * @param ac The auto completion instance.
	 * @see #uninstallImpl(RSyntaxTextArea)
	 */
	protected void installImpl(RSyntaxTextArea textArea, AutoCompletion ac) {
		textAreaToAutoCompletion.put(textArea, ac);
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isAutoActivationEnabled() {
		return autoActivationEnabled;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isAutoCompleteEnabled() {
		return autoCompleteEnabled;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isParameterAssistanceEnabled() {
		return parameterAssistanceEnabled;
	}


	/**
	 * {@inheritDoc}
	 */
	public void setAutoActivationDelay(int ms) {
		ms = Math.max(0, ms);
		if (ms!=autoActivationDelay) {
			autoActivationDelay = ms;
			Iterator i=textAreaToAutoCompletion.values().iterator();
			while (i.hasNext()) {
				AutoCompletion ac = (AutoCompletion)i.next();
				ac.setAutoActivationDelay(autoActivationDelay);
			}
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public void setAutoActivationEnabled(boolean enabled) {
		if (enabled!=autoActivationEnabled) {
			autoActivationEnabled = enabled;
			Iterator i=textAreaToAutoCompletion.values().iterator();
			while (i.hasNext()) {
				AutoCompletion ac = (AutoCompletion)i.next();
				ac.setAutoActivationEnabled(enabled);
			}
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public void setAutoCompleteEnabled(boolean enabled) {
		if (enabled!=autoCompleteEnabled) {
			autoCompleteEnabled = enabled;
			Iterator i=textAreaToAutoCompletion.values().iterator();
			while (i.hasNext()) {
				AutoCompletion ac = (AutoCompletion)i.next();
				ac.setAutoCompleteEnabled(enabled);
			}
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public void setDefaultCompletionCellRenderer(ListCellRenderer r) {
		if (r==null) {
			r = createDefaultCompletionCellRenderer();
		}
		renderer = r;
	}


	/**
	 * {@inheritDoc}
	 */
	public void setParameterAssistanceEnabled(boolean enabled) {
		if (enabled!=parameterAssistanceEnabled) {
			parameterAssistanceEnabled = enabled;
			Iterator i=textAreaToAutoCompletion.values().iterator();
			while (i.hasNext()) {
				AutoCompletion ac = (AutoCompletion)i.next();
				ac.setParameterAssistanceEnabled(enabled);
			}
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public void setShowDescWindow(boolean show) {
		if (show!=showDescWindow) {
			showDescWindow = show;
			Iterator i=textAreaToAutoCompletion.values().iterator();
			while (i.hasNext()) {
				AutoCompletion ac = (AutoCompletion)i.next();
				ac.setShowDescWindow(show);
			}
		}
	}


	/**
	 * Unregisters an textArea.  This should be called by subclasses in their
	 * {@link #uninstall(org.fife.ui.rsyntaxtextarea.RSyntaxTextArea)} methods.
	 * This method will also call the <code>uninstall</code> method on the
	 * <code>AutoCompletion</code>.
	 *
	 * @param textArea The text area.
	 * @see #installImpl(RSyntaxTextArea, AutoCompletion)
	 */
	protected void uninstallImpl(RSyntaxTextArea textArea) {
		AutoCompletion ac = getAutoCompletionFor(textArea);
		if (ac!=null) {
			ac.uninstall();
		}
		textAreaToAutoCompletion.remove(textArea);
	}


}