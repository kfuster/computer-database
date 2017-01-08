package com.excilys.formation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Created by Ookami on 07/01/2017.
 */
public class ServiceTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ServiceTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ServiceTest.class );
    }

    /**
     * Rigourous Test
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
