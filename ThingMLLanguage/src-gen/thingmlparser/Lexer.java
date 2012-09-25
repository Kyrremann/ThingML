/* The following code was generated by JFlex 1.4.3 on 9/5/12 4:32 PM */

package thingmlparser;
import java_cup.runtime.*;
import java.util.ArrayList;

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 9/5/12 4:32 PM from the specification file
 * <tt>/home/kyrremann/workspace/ThingML-Editor/ThingMLLanguage/grammars/thingml.lex</tt>
 */
public class Lexer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int STRING = 2;
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\5\1\3\1\2\1\0\1\3\1\1\16\5\4\0\1\3\3\0"+
    "\1\4\3\0\1\30\1\31\3\0\1\24\1\0\1\6\1\7\11\10"+
    "\4\0\1\25\2\0\14\4\1\15\5\4\1\11\7\4\4\0\1\4"+
    "\1\0\1\13\1\4\1\16\1\4\1\14\1\22\1\4\1\17\1\20"+
    "\2\4\1\23\1\4\1\21\5\4\1\12\6\4\1\26\1\0\1\27"+
    "\1\0\41\5\2\0\4\4\4\0\1\4\2\0\1\5\7\0\1\4"+
    "\4\0\1\4\5\0\27\4\1\0\37\4\1\0\u013f\4\31\0\162\4"+
    "\4\0\14\4\16\0\5\4\11\0\1\4\21\0\130\5\5\0\23\5"+
    "\12\0\1\4\13\0\1\4\1\0\3\4\1\0\1\4\1\0\24\4"+
    "\1\0\54\4\1\0\46\4\1\0\5\4\4\0\202\4\1\0\4\5"+
    "\3\0\105\4\1\0\46\4\2\0\2\4\6\0\20\4\41\0\46\4"+
    "\2\0\1\4\7\0\47\4\11\0\21\5\1\0\27\5\1\0\3\5"+
    "\1\0\1\5\1\0\2\5\1\0\1\5\13\0\33\4\5\0\3\4"+
    "\15\0\4\5\14\0\6\5\13\0\32\4\5\0\13\4\16\5\7\0"+
    "\12\5\4\0\2\4\1\5\143\4\1\0\1\4\10\5\1\0\6\5"+
    "\2\4\2\5\1\0\4\5\2\4\12\5\3\4\2\0\1\4\17\0"+
    "\1\5\1\4\1\5\36\4\33\5\2\0\3\4\60\0\46\4\13\5"+
    "\1\4\u014f\0\3\5\66\4\2\0\1\5\1\4\20\5\2\0\1\4"+
    "\4\5\3\0\12\4\2\5\2\0\12\5\21\0\3\5\1\0\10\4"+
    "\2\0\2\4\2\0\26\4\1\0\7\4\1\0\1\4\3\0\4\4"+
    "\2\0\1\5\1\4\7\5\2\0\2\5\2\0\3\5\11\0\1\5"+
    "\4\0\2\4\1\0\3\4\2\5\2\0\12\5\4\4\15\0\3\5"+
    "\1\0\6\4\4\0\2\4\2\0\26\4\1\0\7\4\1\0\2\4"+
    "\1\0\2\4\1\0\2\4\2\0\1\5\1\0\5\5\4\0\2\5"+
    "\2\0\3\5\13\0\4\4\1\0\1\4\7\0\14\5\3\4\14\0"+
    "\3\5\1\0\11\4\1\0\3\4\1\0\26\4\1\0\7\4\1\0"+
    "\2\4\1\0\5\4\2\0\1\5\1\4\10\5\1\0\3\5\1\0"+
    "\3\5\2\0\1\4\17\0\2\4\2\5\2\0\12\5\1\0\1\4"+
    "\17\0\3\5\1\0\10\4\2\0\2\4\2\0\26\4\1\0\7\4"+
    "\1\0\2\4\1\0\5\4\2\0\1\5\1\4\6\5\3\0\2\5"+
    "\2\0\3\5\10\0\2\5\4\0\2\4\1\0\3\4\4\0\12\5"+
    "\1\0\1\4\20\0\1\5\1\4\1\0\6\4\3\0\3\4\1\0"+
    "\4\4\3\0\2\4\1\0\1\4\1\0\2\4\3\0\2\4\3\0"+
    "\3\4\3\0\10\4\1\0\3\4\4\0\5\5\3\0\3\5\1\0"+
    "\4\5\11\0\1\5\17\0\11\5\11\0\1\4\7\0\3\5\1\0"+
    "\10\4\1\0\3\4\1\0\27\4\1\0\12\4\1\0\5\4\4\0"+
    "\7\5\1\0\3\5\1\0\4\5\7\0\2\5\11\0\2\4\4\0"+
    "\12\5\22\0\2\5\1\0\10\4\1\0\3\4\1\0\27\4\1\0"+
    "\12\4\1\0\5\4\2\0\1\5\1\4\7\5\1\0\3\5\1\0"+
    "\4\5\7\0\2\5\7\0\1\4\1\0\2\4\4\0\12\5\22\0"+
    "\2\5\1\0\10\4\1\0\3\4\1\0\27\4\1\0\20\4\4\0"+
    "\6\5\2\0\3\5\1\0\4\5\11\0\1\5\10\0\2\4\4\0"+
    "\12\5\22\0\2\5\1\0\22\4\3\0\30\4\1\0\11\4\1\0"+
    "\1\4\2\0\7\4\3\0\1\5\4\0\6\5\1\0\1\5\1\0"+
    "\10\5\22\0\2\5\15\0\60\4\1\5\2\4\7\5\4\0\10\4"+
    "\10\5\1\0\12\5\47\0\2\4\1\0\1\4\2\0\2\4\1\0"+
    "\1\4\2\0\1\4\6\0\4\4\1\0\7\4\1\0\3\4\1\0"+
    "\1\4\1\0\1\4\2\0\2\4\1\0\4\4\1\5\2\4\6\5"+
    "\1\0\2\5\1\4\2\0\5\4\1\0\1\4\1\0\6\5\2\0"+
    "\12\5\2\0\2\4\42\0\1\4\27\0\2\5\6\0\12\5\13\0"+
    "\1\5\1\0\1\5\1\0\1\5\4\0\2\5\10\4\1\0\42\4"+
    "\6\0\24\5\1\0\2\5\4\4\4\0\10\5\1\0\44\5\11\0"+
    "\1\5\71\0\42\4\1\0\5\4\1\0\2\4\1\0\7\5\3\0"+
    "\4\5\6\0\12\5\6\0\6\4\4\5\106\0\46\4\12\0\51\4"+
    "\7\0\132\4\5\0\104\4\5\0\122\4\6\0\7\4\1\0\77\4"+
    "\1\0\1\4\1\0\4\4\2\0\7\4\1\0\1\4\1\0\4\4"+
    "\2\0\47\4\1\0\1\4\1\0\4\4\2\0\37\4\1\0\1\4"+
    "\1\0\4\4\2\0\7\4\1\0\1\4\1\0\4\4\2\0\7\4"+
    "\1\0\7\4\1\0\27\4\1\0\37\4\1\0\1\4\1\0\4\4"+
    "\2\0\7\4\1\0\47\4\1\0\23\4\16\0\11\5\56\0\125\4"+
    "\14\0\u026c\4\2\0\10\4\12\0\32\4\5\0\113\4\3\0\3\4"+
    "\17\0\15\4\1\0\4\4\3\5\13\0\22\4\3\5\13\0\22\4"+
    "\2\5\14\0\15\4\1\0\3\4\1\0\2\5\14\0\64\4\40\5"+
    "\3\0\1\4\3\0\2\4\1\5\2\0\12\5\41\0\3\5\2\0"+
    "\12\5\6\0\130\4\10\0\51\4\1\5\126\0\35\4\3\0\14\5"+
    "\4\0\14\5\12\0\12\5\36\4\2\0\5\4\u038b\0\154\4\224\0"+
    "\234\4\4\0\132\4\6\0\26\4\2\0\6\4\2\0\46\4\2\0"+
    "\6\4\2\0\10\4\1\0\1\4\1\0\1\4\1\0\1\4\1\0"+
    "\37\4\2\0\65\4\1\0\7\4\1\0\1\4\3\0\3\4\1\0"+
    "\7\4\3\0\4\4\2\0\6\4\4\0\15\4\5\0\3\4\1\0"+
    "\7\4\17\0\4\5\32\0\5\5\20\0\2\4\23\0\1\4\13\0"+
    "\4\5\6\0\6\5\1\0\1\4\15\0\1\4\40\0\22\4\36\0"+
    "\15\5\4\0\1\5\3\0\6\5\27\0\1\4\4\0\1\4\2\0"+
    "\12\4\1\0\1\4\3\0\5\4\6\0\1\4\1\0\1\4\1\0"+
    "\1\4\1\0\4\4\1\0\3\4\1\0\7\4\3\0\3\4\5\0"+
    "\5\4\26\0\44\4\u0e81\0\3\4\31\0\11\4\6\5\1\0\5\4"+
    "\2\0\5\4\4\0\126\4\2\0\2\5\2\0\3\4\1\0\137\4"+
    "\5\0\50\4\4\0\136\4\21\0\30\4\70\0\20\4\u0200\0\u19b6\4"+
    "\112\0\u51a6\4\132\0\u048d\4\u0773\0\u2ba4\4\u215c\0\u012e\4\2\0\73\4"+
    "\225\0\7\4\14\0\5\4\5\0\1\4\1\5\12\4\1\0\15\4"+
    "\1\0\5\4\1\0\1\4\1\0\2\4\1\0\2\4\1\0\154\4"+
    "\41\0\u016b\4\22\0\100\4\2\0\66\4\50\0\15\4\3\0\20\5"+
    "\20\0\4\5\17\0\2\4\30\0\3\4\31\0\1\4\6\0\5\4"+
    "\1\0\207\4\2\0\1\5\4\0\1\4\13\0\12\5\7\0\32\4"+
    "\4\0\1\4\1\0\32\4\12\0\132\4\3\0\6\4\2\0\6\4"+
    "\2\0\6\4\2\0\3\4\3\0\2\4\3\0\2\4\22\0\3\5"+
    "\4\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\2\2\1\3\1\4\1\1\2\5\3\4"+
    "\1\1\1\6\1\7\1\10\1\11\1\0\3\4\1\12"+
    "\1\3\4\4\1\13\1\4\1\14\1\15\6\4\1\16";

  private static int [] zzUnpackAction() {
    int [] result = new int[39];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\32\0\64\0\116\0\64\0\64\0\150\0\202"+
    "\0\64\0\234\0\266\0\320\0\352\0\u0104\0\64\0\64"+
    "\0\64\0\64\0\u011e\0\u0138\0\u0152\0\u016c\0\64\0\u0186"+
    "\0\u01a0\0\u01ba\0\u01d4\0\u01ee\0\150\0\u0208\0\u0222\0\150"+
    "\0\u023c\0\u0256\0\u0270\0\u028a\0\u02a4\0\u02be\0\150";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[39];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\1\5\1\6\1\7\1\3\1\10\1\11"+
    "\1\12\1\13\6\7\1\14\1\7\1\15\1\7\1\16"+
    "\1\3\1\17\1\20\1\21\1\22\2\3\1\0\27\3"+
    "\34\0\1\5\33\0\2\7\1\0\15\7\14\0\1\23"+
    "\32\0\2\12\25\0\2\7\1\0\3\7\1\24\11\7"+
    "\12\0\2\7\1\0\12\7\1\25\2\7\12\0\2\7"+
    "\1\0\11\7\1\26\3\7\33\0\1\27\4\0\1\23"+
    "\1\30\1\6\27\23\4\0\2\7\1\0\4\7\1\31"+
    "\10\7\12\0\2\7\1\0\11\7\1\32\3\7\12\0"+
    "\2\7\1\0\12\7\1\33\2\7\10\0\1\6\33\0"+
    "\2\7\1\0\3\7\1\34\11\7\12\0\2\7\1\0"+
    "\3\7\1\35\11\7\12\0\2\7\1\0\4\7\1\36"+
    "\10\7\12\0\2\7\1\0\5\7\1\37\7\7\12\0"+
    "\2\7\1\0\14\7\1\40\12\0\2\7\1\0\6\7"+
    "\1\41\6\7\12\0\2\7\1\0\4\7\1\42\10\7"+
    "\12\0\2\7\1\0\7\7\1\43\5\7\12\0\2\7"+
    "\1\0\10\7\1\44\4\7\12\0\2\7\1\0\11\7"+
    "\1\45\3\7\12\0\2\7\1\0\12\7\1\46\2\7"+
    "\12\0\2\7\1\0\5\7\1\47\7\7\6\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[728];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\1\1\2\11\2\1\1\11\5\1\4\11"+
    "\1\0\3\1\1\11\20\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[39];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  StringBuffer string = new StringBuffer();
  ArrayList<Integer> lineOffset = new ArrayList<Integer>();
  
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
  


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 1722) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 12: 
          { return symbol(sym.STATE);
          }
        case 15: break;
        case 9: 
          { return symbol(sym.RPAR);
          }
        case 16: break;
        case 7: 
          { return symbol(sym.RBRACK);
          }
        case 17: break;
        case 6: 
          { return symbol(sym.LBRACK);
          }
        case 18: break;
        case 8: 
          { return symbol(sym.LPAR);
          }
        case 19: break;
        case 10: 
          { return symbol(sym.TRANS);
          }
        case 20: break;
        case 5: 
          { return symbol(sym.INT_LITERAL, yytext());
          }
        case 21: break;
        case 1: 
          { throw new Error("Illegal character '" + yytext() + "' at line " + yyline + ", column " + yycolumn + ".");
          }
        case 22: break;
        case 2: 
          { lineOffset.add(yyline, yycolumn);
          }
        case 23: break;
        case 14: 
          { return symbol(sym.STATEMACHINE);
          }
        case 24: break;
        case 13: 
          { return symbol(sym.FINAL);
          }
        case 25: break;
        case 11: 
          { return symbol(sym.INIT);
          }
        case 26: break;
        case 3: 
          { 
          }
        case 27: break;
        case 4: 
          { return symbol(sym.ID, yytext());
          }
        case 28: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return new java_cup.runtime.Symbol(sym.EOF); }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
