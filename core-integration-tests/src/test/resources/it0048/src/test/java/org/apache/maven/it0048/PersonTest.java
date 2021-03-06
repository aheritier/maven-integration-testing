package org.apache.maven.it0048;

import junit.framework.TestCase;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PersonTest
    extends TestCase
{
    public void testPerson() throws IOException, URISyntaxException
    {
        ClassLoader cloader = getClass().getClassLoader();

        String path = getClass().getName().replace( '.', '/' ) + ".class";

        URL resource = cloader.getResource( path );

        File resourceFile = new File( new URI( resource.toString() ) );

        String dirPath = resourceFile.getAbsolutePath();

        dirPath = dirPath.substring( 0, dirPath.length() - path.length() );

        File dir = new File( dirPath );

        dir = dir.getParentFile();

        File testFile = new File( dir, "testFileOutput.txt" );

        FileWriter writer = new FileWriter( testFile );

        writer.write( "Test" );

        writer.flush();

        writer.close();
    }
}
