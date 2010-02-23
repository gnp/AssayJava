package org.apache.commons.lang;

import junit.framework.TestCase;

public class StringEscapUtilsTest extends TestCase {

    /**
     * This works in commons-lang-2.1 but not in 2.4!
     * 
     * http://issues.apache.org/jira/browse/LANG-421
     */
    public void testSlashJava() {
        final String actualValue = "Foo/Bar";
        final String escapedValue = StringEscapeUtilsPatched.escapeJava(actualValue);
        
        assertEquals(actualValue, escapedValue);
        System.out.println(actualValue);
        
        final String trippedValue = StringEscapeUtilsPatched.unescapeJava(escapedValue);
        
        assertEquals(actualValue, trippedValue);
    }

    /**
     * This works in commons-lang-2.1 but not in 2.4!
     * 
     * http://issues.apache.org/jira/browse/LANG-421
     */
    public void testSlashJavaScript() {
        final String actualValue = "Foo/Bar";
        final String escapedValue = StringEscapeUtilsPatched.escapeJavaScript(actualValue);
        
        assertEquals(actualValue, escapedValue);
        System.out.println(actualValue);
        
        final String trippedValue = StringEscapeUtilsPatched.unescapeJavaScript(escapedValue);
        
        assertEquals(actualValue, trippedValue);
    }
    
}
