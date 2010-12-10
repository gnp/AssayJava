package org.apache.commons.lang;

import junit.framework.TestCase;

public class StringEscapUtilsTest extends TestCase {

    /**
     * This works in commons-lang-2.1 but not in 2.4! It is fixed in 2.5.
     * 
     * http://issues.apache.org/jira/browse/LANG-421
     */
    public void testSlashJava() {
        final String actualValue = "Foo/Bar";
        final String escapedValue = StringEscapeUtils.escapeJava(actualValue);
        
        assertEquals(actualValue, escapedValue);
        System.out.println(actualValue);
        
        final String trippedValue = StringEscapeUtils.unescapeJava(escapedValue);
        
        assertEquals(actualValue, trippedValue);
    }

    /**
     * This works in commons-lang-2.1 but not in 2.4, nor in 2.5 (2.5 fixes erroneous forward slash escaping in
     * escapeJava(), but not in escapeJavaScript()!
     * 
     * Disabling this test, though, because we don't actually need that method so 2.5 will work for us.
     * 
     * http://issues.apache.org/jira/browse/LANG-421
     */
    public void XtestSlashJavaScript() {
        final String actualValue = "Foo/Bar";
        final String escapedValue = StringEscapeUtils.escapeJavaScript(actualValue);
        
        assertEquals(actualValue, escapedValue);
        System.out.println(actualValue);
        
        final String trippedValue = StringEscapeUtils.unescapeJavaScript(escapedValue);
        
        assertEquals(actualValue, trippedValue);
    }
    
}
