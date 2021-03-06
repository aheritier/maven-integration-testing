package org.apache.maven.plugin.coreit;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * "Catch" a parameter "thrown" by the ThrowMojo through the plugin context, and
 * write a file based on it's value to the build output directory.
 * 
 * @goal catch
 */
public class CatchMojo
    extends AbstractMojo
{

    /**
     * @parameter expression="${project.build.directory}"
     * @required
     * @readonly
     */
    private File outDir;
    
    public File getOutDir()
    {
        return outDir;
    }
    
    public void setOutDir( File outDir )
    {
        this.outDir = outDir;
    }

    public void execute()
        throws MojoExecutionException
    {
        String value = (String) getPluginContext().get( ThrowMojo.THROWN_PARAMETER );

        if ( !outDir.exists() )
        {
            outDir.mkdirs();
        }
        
        File outfile = new File( outDir, value );

        Writer writer = null;
        try
        {
            writer = new FileWriter( outfile );
            
            writer.write( value );
            
            writer.flush();
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Cannot write output file: " + outfile, e );
        }
        finally
        {
            if ( writer != null )
            {
                try
                {
                    writer.close();
                }
                catch ( IOException e )
                {
                }
            }
        }
    }

}
