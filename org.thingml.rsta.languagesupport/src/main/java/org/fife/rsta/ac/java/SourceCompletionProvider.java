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
package org.fife.rsta.ac.java;

import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import org.fife.rsta.ac.ShorthandCompletionCache;
import org.fife.rsta.ac.java.buildpath.LibraryInfo;
import org.fife.rsta.ac.java.buildpath.SourceLocation;
import org.fife.rsta.ac.java.classreader.ClassFile;
import org.fife.rsta.ac.java.classreader.FieldInfo;
import org.fife.rsta.ac.java.classreader.MemberInfo;
import org.fife.rsta.ac.java.classreader.MethodInfo;
import org.fife.rsta.ac.java.rjc.ast.CodeBlock;
import org.fife.rsta.ac.java.rjc.ast.CompilationUnit;
import org.fife.rsta.ac.java.rjc.ast.Field;
import org.fife.rsta.ac.java.rjc.ast.FormalParameter;
import org.fife.rsta.ac.java.rjc.ast.ImportDeclaration;
import org.fife.rsta.ac.java.rjc.ast.LocalVariable;
import org.fife.rsta.ac.java.rjc.ast.Member;
import org.fife.rsta.ac.java.rjc.ast.Method;
import org.fife.rsta.ac.java.rjc.ast.NormalClassDeclaration;
import org.fife.rsta.ac.java.rjc.ast.TypeDeclaration;
import org.fife.rsta.ac.java.rjc.lang.Type;
import org.fife.rsta.ac.java.rjc.lang.TypeArgument;
import org.fife.rsta.ac.java.rjc.lang.TypeParameter;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.RSyntaxUtilities;
import org.fife.ui.rsyntaxtextarea.Token;


/**
 * Parses a Java AST for code completions.  It currently scans the following:
 * 
 * <ul>
 *    <li>Import statements
 *    <li>Method names
 *    <li>Field names
 * </ul>
 *
 * Also, if the caret is inside a method, local variables up to the caret
 * position are also returned.
 *
 * @author Robert Futrell
 * @version 1.0
 */
class SourceCompletionProvider extends DefaultCompletionProvider {

	/**
	 * The parent completion provider.
	 */
	private JavaCompletionProvider javaProvider;

	/**
	 * Used to get information about what classes match imports.
	 */
	private JarManager jarManager;

	private static final String JAVA_LANG_PACKAGE			= "java.lang.*";
	private static final String THIS						= "this";
	
	//Shorthand completions (templates and comments)
	private ShorthandCompletionCache shorthandCache;
	/**
	 * Constructor.
	 */
	public SourceCompletionProvider() {
		this(null);
	}


	/**
	 * Constructor.
	 *
	 * @param jarManager The jar manager for this provider.
	 */
	public SourceCompletionProvider(JarManager jarManager) {
		if (jarManager==null) {
			jarManager = new JarManager();
		}
		this.jarManager = jarManager;
		setParameterizedCompletionParams('(', ", ", ')');
		setAutoActivationRules(false, "."); // Default - only activate after '.'
		setParameterChoicesProvider(new SourceParamChoicesProvider());
	}


	private void addCompletionsForStaticMembers(Set set,
						CompilationUnit cu, ClassFile cf, String pkg) {

		// Check us first, so if we override anything, we get the "newest"
		// version.
		int methodCount = cf.getMethodCount();
		for (int i=0; i<methodCount; i++) {
			MethodInfo info = cf.getMethodInfo(i);
			if (isAccessible(info, pkg) && info.isStatic()) {
				MethodCompletion mc = new MethodCompletion(this, info);
				set.add(mc);
			}
		}

		int fieldCount = cf.getFieldCount();
		for (int i=0; i<fieldCount; i++) {
			FieldInfo info = cf.getFieldInfo(i);
			if (isAccessible(info, pkg) && info.isStatic()) {
				FieldCompletion fc = new FieldCompletion(this, info);
				set.add(fc);
			}
		}

		ClassFile superClass = getClassFileFor(cu, cf.getSuperClassName(true));
		if (superClass!=null) {
			addCompletionsForStaticMembers(set, cu, superClass, pkg);
		}

	}


	/**
	 * Adds completions for accessible methods and fields of super classes.
	 * This is only called when the caret is inside of a class.
	 * TODO: Handle accessibility correctly!
	 *
	 * @param set
	 * @param cu The compilation unit.
	 * @param cf A class in the chain of classes that a type being parsed
	 *        inherits from.
	 * @param pkg The package of the source being parsed.
	 * @param typeParamMap A mapping of type parameters to type arguments
	 *        for the object whose fields/methods/etc. are currently being
	 *        code-completed.
	 */
	private void addCompletionsForExtendedClass(Set set,
						CompilationUnit cu, ClassFile cf, String pkg,
						Map typeParamMap) {

		// Reset this class's type-arguments-to-type-parameters map, so that
		// when methods and fields need to know type arguments, they can query
		// for them.
		cf.setTypeParamsToTypeArgs(typeParamMap);

		// Check us first, so if we override anything, we get the "newest"
		// version.
		int methodCount = cf.getMethodCount();
		for (int i=0; i<methodCount; i++) {
			MethodInfo info = cf.getMethodInfo(i);
			// Don't show constructors
			if (isAccessible(info, pkg) && !info.isConstructor()) {
				MethodCompletion mc = new MethodCompletion(this, info);
				set.add(mc);
			}
		}

		int fieldCount = cf.getFieldCount();
		for (int i=0; i<fieldCount; i++) {
			FieldInfo info = cf.getFieldInfo(i);
			if (isAccessible(info, pkg)) {
				FieldCompletion fc = new FieldCompletion(this, info);
				set.add(fc);
			}
		}

		// Add completions for any non-overridden super-class methods.
		ClassFile superClass = getClassFileFor(cu, cf.getSuperClassName(true));
		if (superClass!=null) {
			addCompletionsForExtendedClass(set, cu, superClass, pkg, typeParamMap);
		}

		// Add completions for any interface methods, in case this class is
		// abstract and hasn't implemented some of them yet.
		// TODO: Do this only if "top-level" class is declared abstract
		for (int i=0; i<cf.getImplementedInterfaceCount(); i++) {
			String inter = cf.getImplementedInterfaceName(i, true);
			cf = getClassFileFor(cu, inter);
			addCompletionsForExtendedClass(set, cu, cf, pkg, typeParamMap);
		}

	}


	/**
	 * Adds completions for all methods and public fields of a local variable.
	 * This will add nothing if the local variable is a primitive type.
	 *
	 * @param cu The compilation unit being parsed.
	 * @param var The local variable.
	 * @param retVal The set to add completions to.
	 */
	private void addCompletionsForLocalVarsMethods(CompilationUnit cu,
			LocalVariable var, Set retVal) {

		Type type = var.getType();
		String pkg = cu.getPackageName();

		if (type.isArray()) {
			ClassFile cf = getClassFileFor(cu, "java.lang.Object");
			addCompletionsForExtendedClass(retVal, cu, cf, pkg, null);
			FieldCompletion fc = FieldCompletion.
				createLengthCompletion(this, type);
			retVal.add(fc);
		}

		else if (!type.isBasicType()) {
			String typeStr = type.getName(true, false);
			ClassFile cf = getClassFileFor(cu, typeStr);
			if (cf!=null) {
				Map typeParamMap = createTypeParamMap(type, cf);
				addCompletionsForExtendedClass(retVal, cu, cf, pkg, typeParamMap);
			}
		}

	}


	/**
	 * Adds simple shorthand completions relevant to Java.
	 *
	 * @param set The set to add to.
	 */
	private void addShorthandCompletions(Set set) {
		if(shorthandCache != null) {
			set.addAll(shorthandCache.getShorthandCompletions());
		}
	}
	
	/**
	 * Set template completion cache for source completion provider
	 * @param templateCache
	 */
	public void setShorthandCache(ShorthandCompletionCache shorthandCache) {
		this.shorthandCache = shorthandCache;
	}


	/**
	 * Gets the {@link ClassFile} for a class.
	 *
	 * @param cu The compilation unit being parsed.
	 * @param className The name of the class (fully qualified or not).
	 * @return The {@link ClassFile} for the class, or <code>null</code> if
	 *         <code>cf</code> represents <code>java.lang.Object</code> (or
	 *         if the super class could not be determined).
	 */
	private ClassFile getClassFileFor(CompilationUnit cu, String className) {

		//System.err.println(">>> Getting class file for: " + className);
		if (className==null) {
			return null;
		}

		ClassFile superClass = null;

		// Determine the fully qualified class to grab
		if (!Util.isFullyQualified(className)) {

			// Check in this source file's package first
			String pkg = cu.getPackageName();
			if (pkg!=null) {
				String temp = pkg + "." + className;
				superClass = jarManager.getClassEntry(temp);
			}

			// Next, go through the imports (order is important)
			if (superClass==null) {
				for (Iterator i=cu.getImportIterator(); i.hasNext(); ) {
					ImportDeclaration id = (ImportDeclaration)i.next();
					String imported = id.getName();
					if (imported.endsWith(".*")) {
						String temp = imported.substring(
								0, imported.length()-1) + className;
						superClass = jarManager.getClassEntry(temp);
						if (superClass!=null) {
							break;
						}
					}
					else if (imported.endsWith("." + className)) {
						superClass = jarManager.getClassEntry(imported);
						break;
					}
				}
			}

			// Finally, try java.lang
			if (superClass==null) {
				String temp = "java.lang." + className;
				superClass = jarManager.getClassEntry(temp);
			}

		}

		else {
			superClass = jarManager.getClassEntry(className);
		}

		return superClass;

	}


	/**
	 * Adds completions for local variables in a method.
	 *
	 * @param set
	 * @param method
	 * @param offs The caret's offset into the source.  This should be inside
	 *        of <code>method</code>.
	 */
	private void addLocalVarCompletions(Set set, Method method, int offs) {

		for (int i=0; i<method.getParameterCount(); i++) {
			FormalParameter param = method.getParameter(i);
			set.add(new LocalVariableCompletion(this, param));
		}

		CodeBlock body = method.getBody();
		if (body!=null) {
			addLocalVarCompletions(set, body, offs);
		}

	}


	/**
	 * Adds completions for local variables in a code block inside of a method.
	 *
	 * @param set
	 * @param block The code block.
	 * @param offs The caret's offset into the source. This should be inside
	 *        of <code>block</code>.
	 */
	private void addLocalVarCompletions(Set set, CodeBlock block, int offs) {

		for (int i=0; i<block.getLocalVarCount(); i++) {
			LocalVariable var = block.getLocalVar(i);
			if (var.getNameEndOffset()<=offs) {
				set.add(new LocalVariableCompletion(this, var));
			}
			else { // This and all following declared after offs
				break;
			}
		}

		for (int i=0; i<block.getChildBlockCount(); i++) {
			CodeBlock child = block.getChildBlock(i);
			if (child.containsOffset(offs)) {
				addLocalVarCompletions(set, child, offs);
				break; // All other blocks are past this one
			}
			// If we've reached a block that's past the offset we're
			// searching for...
			else if (child.getNameStartOffset()>offs) {
				break;
			}
		}

	}


	/**
	 * Adds a jar to read from.
	 *
	 * @param info The jar to add.  If this is <code>null</code>, then
	 *        the current JVM's main JRE jar (rt.jar, or classes.jar on OS X)
	 *        will be added.  If this jar has already been added, adding it
	 *        again will do nothing (except possibly update its attached source
	 *        location).
	 * @throws IOException If an IO error occurs.
	 * @see #getJars()
	 * @see #removeJar(File)
	 */
	public void addJar(LibraryInfo info) throws IOException {
		jarManager.addClassFileSource(info);
	}


	/**
	 * Checks whether the user is typing a completion for a String member after
	 * a String literal.
	 *
	 * @param comp The text component.
	 * @param alreadyEntered The text already entered.
	 * @param cu The compilation unit being parsed.
	 * @param set The set to add possible completions to.
	 * @return Whether the user is indeed typing a completion for a String
	 *         literal member.
	 */
	private boolean checkStringLiteralMember(JTextComponent comp,
											String alreadyEntered,
											CompilationUnit cu, Set set) {

		boolean stringLiteralMember = false;

		int offs = comp.getCaretPosition() - alreadyEntered.length() - 1;
		if (offs>1) {
			RSyntaxTextArea textArea = (RSyntaxTextArea)comp;
			RSyntaxDocument doc = (RSyntaxDocument)textArea.getDocument();
			try {
				//System.out.println(doc.charAt(offs) + ", " + doc.charAt(offs+1));
				if (doc.charAt(offs)=='"' && doc.charAt(offs+1)=='.') {
					int curLine = textArea.getLineOfOffset(offs);
					Token list = textArea.getTokenListForLine(curLine);
					Token prevToken = RSyntaxUtilities.getTokenAtOffset(list, offs);
					if (prevToken!=null &&
							prevToken.type==Token.LITERAL_STRING_DOUBLE_QUOTE) {
						ClassFile cf = getClassFileFor(cu, "java.lang.String");
						addCompletionsForExtendedClass(set, cu, cf,
													cu.getPackageName(), null);
						stringLiteralMember = true;
					}
					else {
						System.out.println(prevToken);
					}
				}
			} catch (BadLocationException ble) { // Never happens
				ble.printStackTrace();
			}
		}

		return stringLiteralMember;

	}


	/**
	 * Removes all jars from the "build path."
	 *
	 * @see #removeJar(File)
	 * @see #addClassFileSource(JarInfo)
	 * @see #getJars()
	 */
	public void clearJars() {
		jarManager.clearClassFileSources();
		// The memory used by the completions can be quite large, so go ahead
		// and clear out the completions list so no-longer-needed ones are
		// eligible for GC.
		clear();
	}


	/**
	 * Creates and returns a mapping of type parameters to type arguments.
	 *
	 * @param type The type of a variable/field/etc. whose fields/methods/etc.
	 *        are being code completed, as declared in the source.  This
	 *        includes type arguments.
	 * @param cf The <code>ClassFile</code> representing the actual type of
	 *        the variable/field/etc. being code completed
	 * @return A mapping of type parameter names to type arguments (both
	 *         Strings).
	 */
	private Map createTypeParamMap(Type type, ClassFile cf) {
		Map typeParamMap = null;
		List typeArgs = type.getTypeArguments(type.getIdentifierCount()-1);
		if (typeArgs!=null) {
			typeParamMap = new HashMap();
			List paramTypes = cf.getParamTypes();
			// Should be the same size!  Otherwise, the source code has
			// too many/too few type arguments listed for this type.
			int min = Math.min(paramTypes==null ? 0 : paramTypes.size(),
									typeArgs.size());
			for (int i=0; i<min; i++) {
				TypeArgument typeArg = (TypeArgument)typeArgs.get(i);
				typeParamMap.put(paramTypes.get(i), typeArg.toString());
			}
		}
		return typeParamMap;
	}


	/**
	 * {@inheritDoc}
	 */
	protected List getCompletionsImpl(JTextComponent comp) {

		comp.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		try {

		completions.clear();

		CompilationUnit cu = javaProvider.getCompilationUnit();
		if (cu==null) {
			return completions; // empty
		}

		Set set = new TreeSet();

		// Cut down the list to just those matching what we've typed.
		// Note: getAlreadyEnteredText() never returns null
		String text = getAlreadyEnteredText(comp);

		// Special case - end of a String literal
		boolean stringLiteralMember = checkStringLiteralMember(comp, text, cu,
																set);

		// Not after a String literal - regular code completion
		if (!stringLiteralMember) {

			// Don't add shorthand completions if they're typing something
			// qualified
			if (text.indexOf('.')==-1) {
				addShorthandCompletions(set);
			}

			loadImportCompletions(set, text, cu);

			// Add completions for fully-qualified stuff (e.g. "com.sun.jav")
			//long startTime = System.currentTimeMillis();
			jarManager.addCompletions(this, text, set);
			//long time = System.currentTimeMillis() - startTime;
			//System.out.println("jar completions loaded in: " + time);

			// Loop through all types declared in this source, and provide
			// completions depending on in what type/method/etc. the caret's in.
			loadCompletionsForCaretPosition(cu, comp, text, set);

		}

		// Do a final sort of all of our completions and we're good to go!
		completions = new ArrayList(set);
		Collections.sort(completions);

		// Only match based on stuff after the final '.', since that's what is
		// displayed for all of our completions.
		text = text.substring(text.lastIndexOf('.')+1);

		int start = Collections.binarySearch(completions, text, comparator);
		if (start<0) {
			start = -(start+1);
		}
		else {
			// There might be multiple entries with the same input text.
			while (start>0 &&
					comparator.compare(completions.get(start-1), text)==0) {
				start--;
			}
		}

		int end = Collections.binarySearch(completions, text+'{', comparator);
		end = -(end+1);

		return completions.subList(start, end);

		} finally {
			comp.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		}

	}


	/**
	 * Returns the jars on the "build path."
	 *
	 * @return A list of {@link JarInfo}s.  Modifying a <tt>JarInfo</tt> in
	 *         this list will have no effect on this completion provider; in
	 *         order to do that, you must re-add the jar via
	 *         {@link #addClassFileSource(JarInfo)}. If there are no jars on the
	 *         "build path," this will be an empty list.
	 * @see #addClassFileSource(JarInfo)
	 */
	public List getJars() {
		return jarManager.getClassFileSources();
	}



public SourceLocation  getSourceLocForClass(String className) {
	return jarManager.getSourceLocForClass(className);
}

	/**
	 * Returns whether a method defined by a super class is accessible to
	 * this class.
	 *
	 * @param info Information about the member.
	 * @param pkg The package of the source currently being parsed.
	 * @return Whether or not the method is accessible.
	 */
	private boolean isAccessible(MemberInfo info, String pkg) {

		boolean accessible = false;
		int access = info.getAccessFlags();

		if (org.fife.rsta.ac.java.classreader.Util.isPublic(access) ||
				org.fife.rsta.ac.java.classreader.Util.isProtected(access)) {
			accessible = true;
		}
		else if (org.fife.rsta.ac.java.classreader.Util.isDefault(access)) {
			String pkg2 = info.getClassFile().getPackageName();
			accessible = (pkg==null && pkg2==null) ||
						(pkg!=null && pkg.equals(pkg2));
		}

		return accessible;

	}


	/**
	 * {@inheritDoc}
	 */
	protected boolean isValidChar(char ch) {
		return Character.isJavaIdentifierPart(ch) || ch=='.';
	}


	/**
	 * Loads completions based on the current caret location in the source.  In
	 * other words:
	 * 
	 * <ul>
	 *   <li>If the caret is anywhere in a class, the names of all methods and
	 *       fields in the class are loaded.  Methods and fields in super
	 *       classes are also loaded.  TODO: Get super methods/fields added
	 *       correctly by access!
	 *   <li>If the caret is in a field, local variables currently accessible
	 *       are loaded.
	 * </ul>
	 *
	 * @param cu
	 * @param comp
	 * @param alreadyEntered
	 * @param retVal
	 */
	private void loadCompletionsForCaretPosition(CompilationUnit cu,
			JTextComponent comp, String alreadyEntered, Set retVal) {

		// Get completions for all fields and methods of all type declarations.

		//long startTime = System.currentTimeMillis();
		int caret = comp.getCaretPosition();
		//List temp = new ArrayList();
		int start, end;

		int lastDot = alreadyEntered.lastIndexOf('.');
		boolean qualified = lastDot>-1;
		String prefix = qualified ? alreadyEntered.substring(0, lastDot) : null;

		for (Iterator i=cu.getTypeDeclarationIterator(); i.hasNext(); ) {

			TypeDeclaration td = (TypeDeclaration)i.next();
			start = td.getBodyStartOffset();
			end = td.getBodyEndOffset();

			if (caret>start && caret<=end) {
				loadCompletionsForCaretPosition(cu, comp, alreadyEntered,
									retVal, td, prefix, caret);
			}

			else if (caret<start) {
				break; // We've passed any type declarations we could be in
			}

		}

		//long time = System.currentTimeMillis() - startTime;
		//System.out.println("methods/fields/localvars loaded in: " + time);

	}


	/**
	 * Loads completions based on the current caret location in the source.
	 * This method is called when the caret is found to be in a specific type
	 * declaration.  This method checks if the caret is in a child type
	 * declaration first, then adds completions for itself next.
	 * 
	 * <ul>
	 *   <li>If the caret is anywhere in a class, the names of all methods and
	 *       fields in the class are loaded.  Methods and fields in super
	 *       classes are also loaded.  TODO: Get super methods/fields added
	 *       correctly by access!
	 *   <li>If the caret is in a field, local variables currently accessible
	 *       are loaded.
	 * </ul>
	 *
	 * @param cu
	 * @param comp
	 * @param alreadyEntered
	 * @param retVal
	 */
	private void loadCompletionsForCaretPosition(CompilationUnit cu,
			JTextComponent comp, String alreadyEntered, Set retVal,
			TypeDeclaration td, String prefix, int caret) {

		// Do any child types first, so if any vars, etc. have duplicate names,
		// we pick up the one "closest" to us first.
		for (int i=0; i<td.getChildTypeCount(); i++) {
			TypeDeclaration childType = td.getChildType(i);
			loadCompletionsForCaretPosition(cu, comp, alreadyEntered, retVal,
					childType, prefix, caret);
		}

		Method currentMethod = null;

		Map typeParamMap = new HashMap();
		if (td instanceof NormalClassDeclaration) {
			NormalClassDeclaration ncd = (NormalClassDeclaration)td;
			List typeParams = ncd.getTypeParameters();
			if (typeParams!=null) {
				for (int i=0; i<typeParams.size(); i++) {
					TypeParameter typeParam = (TypeParameter)typeParams.get(i);
					String typeVar = typeParam.getName();
					// For non-qualified completions, use type var name.
					typeParamMap.put(typeVar, typeVar);
				}
			}
		}

		// Get completions for this class's methods, fields and local
		// vars.  Do this before checking super classes so that, if
		// we overrode anything, we get the "newest" version.
		String pkg = cu.getPackageName();
		for (Iterator j=td.getMemberIterator(); j.hasNext(); ) {
			Member m = (Member)j.next();
			if (m instanceof Method) {
				Method method = (Method)m;
				if (prefix==null || THIS.equals(prefix)) {
					retVal.add(new MethodCompletion(this, method));
				}
				if (caret>=method.getBodyStartOffset() && caret<method.getBodyEndOffset()) {
					currentMethod = method;
					// Don't add completions for local vars if there is
					// a prefix, even "this".
					if (prefix==null) {
						addLocalVarCompletions(retVal, method, caret);
					}
				}
			}
			else if (m instanceof Field) {
				if (prefix==null || THIS.equals(prefix)) {
					Field field = (Field)m;
					retVal.add(new FieldCompletion(this, field));
				}
			}
		}

		// Completions for superclass methods.
		// TODO: Implement me better
		if (prefix==null || THIS.equals(prefix)) {
			if (td instanceof NormalClassDeclaration) {
				NormalClassDeclaration ncd = (NormalClassDeclaration)td;
				Type extended = ncd.getExtendedType();
				if (extended!=null) { // e.g., not java.lang.Object
					String superClassName = extended.toString();
					ClassFile cf = getClassFileFor(cu, superClassName);
					if (cf!=null) {
						addCompletionsForExtendedClass(retVal, cu, cf, pkg, null);
					}
					else {
						System.out.println("[DEBUG]: Couldn't find ClassFile for: " + superClassName);
					}
				}
			}
		}

		// Completions for methods of fields, return values of methods,
		// static fields/methods, etc.
		if (prefix!=null && !THIS.equals(prefix)) {
			loadCompletionsForCaretPositionQualified(cu,
					alreadyEntered, retVal,
					td, currentMethod, prefix, caret);
		}

	}


	/**
	 * Loads completions for the text at the current caret position, if there
	 * is a "prefix" of chars and at least one '.' character in the text up to
	 * the caret.  This is currently very limited and needs to be improved.
	 *
	 * @param cu
	 * @param alreadyEntered
	 * @param retVal
	 * @param td The type declaration the caret is in.
	 * @param currentMethod The method the caret is in, or <code>null</code> if
	 *        none.
	 * @param prefix The text up to the current caret position.  This is
	 *        guaranteed to be non-<code>null</code> not equal to
	 *        "<tt>this</tt>".
	 * @param offs The offset of the caret in the document.
	 */
	private void loadCompletionsForCaretPositionQualified(CompilationUnit cu,
			String alreadyEntered, Set retVal,
			TypeDeclaration td, Method currentMethod, String prefix, int offs) {

		// TODO: Remove this restriction.
		int dot = prefix.indexOf('.');
		if (dot>-1) {
			System.out.println("[DEBUG]: Qualified non-this completions currently only go 1 level deep");
			return;
		}

		// TODO: Remove this restriction.
		else if (!prefix.matches("[A-Za-z_][A-Za-z0-9_\\$]*")) {
			System.out.println("[DEBUG]: Only identifier non-this completions are currently supported");
			return;
		}

		String pkg = cu.getPackageName();
		boolean matched = false;

		for (Iterator j=td.getMemberIterator(); j.hasNext(); ) {

			Member m = (Member)j.next();

			// The prefix might be a field in the local class.
			if (m instanceof Field) {

				Field field = (Field)m;

				if (field.getName().equals(prefix)) {
					System.out.println("FOUND: " + prefix + " (" + pkg + ")");
					Type type = field.getType();
					if (type.isArray()) {
						ClassFile cf = getClassFileFor(cu, "java.lang.Object");
						addCompletionsForExtendedClass(retVal, cu, cf, pkg, null);
						FieldCompletion fc = FieldCompletion.
							createLengthCompletion(this, type);
						retVal.add(fc);
					}
					else if (!type.isBasicType()) {
						String typeStr = type.getName(true, false);
						ClassFile cf = getClassFileFor(cu, typeStr);
						// Add completions for extended class type chain
						if (cf!=null) {
							Map typeParamMap = createTypeParamMap(type, cf);
							addCompletionsForExtendedClass(retVal, cu, cf, pkg, typeParamMap);
							// Add completions for all implemented interfaces
							// TODO: Only do this if type is abstract!
							for (int i=0; i<cf.getImplementedInterfaceCount(); i++) {
								String inter = cf.getImplementedInterfaceName(i, true);
								cf = getClassFileFor(cu, inter);
								System.out.println(cf);
							}
						}
					}
					matched = true;
					break;
				}

			}

		}

		// The prefix might be for a local variable in the current method.
		if (currentMethod!=null) {

			boolean found = false;

			// Check parameters to the current method
			for (int i=0; i<currentMethod.getParameterCount(); i++) {
				FormalParameter param = currentMethod.getParameter(i);
				String name = param.getName();
				// Assuming prefix is "one level deep" and contains no '.'...
				if (prefix.equals(name)) {
					addCompletionsForLocalVarsMethods(cu, param, retVal);
					found = true;
					break;
				}
			}

			// If a formal param wasn't matched, check local variables.
			if (!found) {
				CodeBlock body = currentMethod.getBody();
				if (body!=null) {
					loadCompletionsForCaretPositionQualifiedCodeBlock(cu,
											retVal, td, body, prefix, offs);
				}
			}

			matched |= found;

		}

		// Could be a class name, in which case we'll need to add completions
		// for static fields and methods.
		if (!matched) {
			List imports = cu.getImports();
			List matches = jarManager.getClassesWithUnqualifiedName(
															prefix, imports);
			if (matches!=null) {
				for (int i=0; i<matches.size(); i++) {
					ClassFile cf = (ClassFile)matches.get(i);
					addCompletionsForStaticMembers(retVal, cu, cf, pkg);
				}
			}
		}

	}


	private void loadCompletionsForCaretPositionQualifiedCodeBlock(
			CompilationUnit cu, Set retVal,
			TypeDeclaration td, CodeBlock block, String prefix, int offs) {

		boolean found = false;

		for (int i=0; i<block.getLocalVarCount(); i++) {
			LocalVariable var = block.getLocalVar(i);
			if (var.getNameEndOffset()<=offs) {
				// TODO: This assumes prefix is "1 level deep"
				if (prefix.equals(var.getName())) {
					addCompletionsForLocalVarsMethods(cu, var, retVal);
					found = true;
					break;
				}
			}
			else { // This and all following declared after offs
				break;
			}
		}

		if (found) {
			return;
		}

		for (int i=0; i<block.getChildBlockCount(); i++) {
			CodeBlock child = block.getChildBlock(i);
			if (child.containsOffset(offs)) {
				loadCompletionsForCaretPositionQualifiedCodeBlock(cu, retVal,
						td, child, prefix, offs);
				break; // All other blocks are past this one
			}
			// If we've reached a block that's past the offset we're
			// searching for...
			else if (child.getNameStartOffset()>offs) {
				break;
			}
		}

	}


	/**
	 * Loads completions for a single import statement.
	 *
	 * @param importStr The import statement.
	 * @param pkgName The package of the source currently being parsed.
	 */
	private void loadCompletionsForImport(Set set, String importStr, String pkgName) {

		if (importStr.endsWith(".*")) {
			String pkg = importStr.substring(0, importStr.length()-2);
			boolean inPkg = pkg.equals(pkgName);
			List classes = jarManager.getClassesInPackage(pkg, inPkg);
			for (Iterator i=classes.iterator(); i.hasNext(); ) {
				ClassFile cf = (ClassFile)i.next();
				set.add(new ClassCompletion(this, cf));
			}
		}

		else {
			ClassFile cf = jarManager.getClassEntry(importStr);
			if (cf!=null) {
				set.add(new ClassCompletion(this, cf));
			}
		}

	}


	/**
	 * Loads completions for all import statements.
	 *
	 * @param cu The compilation unit being parsed.
	 */
	private void loadImportCompletions(Set set, String text,
									CompilationUnit cu) {

		// Fully-qualified completions are handled elsewhere, so no need to
		// duplicate the work here
		if (text.indexOf('.')>-1) {
			return;
		}

		//long startTime = System.currentTimeMillis();

		String pkgName = cu.getPackageName();
		loadCompletionsForImport(set, JAVA_LANG_PACKAGE, pkgName);
		for (Iterator i=cu.getImportIterator(); i.hasNext(); ) {
			ImportDeclaration id = (ImportDeclaration)i.next();
			String name = id.getName();
			if (!JAVA_LANG_PACKAGE.equals(name)) {
				loadCompletionsForImport(set, name, pkgName);
			}
		}
//		Collections.sort(completions);

		//long time = System.currentTimeMillis() - startTime;
		//System.out.println("imports loaded in: " + time);

	}


	/**
	 * Removes a jar from the "build path."
	 *
	 * @param jar The jar to remove.
	 * @return Whether the jar was removed.  This will be <code>false</code>
	 *         if the jar was not on the build path.
	 * @see #addClassFileSource(JarInfo)
	 * @see #getJars()
	 * @see #clearJars()
	 */
	public boolean removeJar(File jar) {
		boolean removed = jarManager.removeClassFileSource(jar);
		// The memory used by the completions can be quite large, so go ahead
		// and clear out the completions list so no-longer-needed ones are
		// eligible for GC.
		if (removed) {
			clear();
		}
		return removed;
	}


	/**
	 * Sets the parent Java provider.
	 * 
	 * @param javaProvider The parent completion provider.
	 */
	void setJavaProvider(JavaCompletionProvider javaProvider) {
		this.javaProvider = javaProvider;
	}


}