package preprocessing;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PureWordFilter implements PreprocessingStage{

    public void pipe(String fromDir, String toDir) {
        String inputPath  = fromDir;
        String outputPath = toDir;
        
        File inputDir = new File( inputPath );
        File[] songFiles = inputDir.listFiles();
        
        for( int i = 0 ; i < songFiles.length ; i++ ){
            if( i % 1000 == 0 || i == songFiles.length-1 ){
                System.out.println( "extracting words from file " + i + "/" + (songFiles.length-1) );
            }
            
            StringBuilder inputSb  = new StringBuilder();
            StringBuilder outputSb = new StringBuilder();
            
            try{
                File oneSongFile = songFiles[i];
                BufferedReader br = new BufferedReader( new FileReader( oneSongFile ) );
                
                String line = "";
                while( ( line=br.readLine() ) != null ){
                    inputSb.append( line + " " );
                }
                
                // 1. remove all but digits and letters
                Pattern p = Pattern.compile("\\w+");
                Matcher m = p.matcher( inputSb );
                
                while( m.find() ){
                    outputSb.append( m.group() + " " );
                }
                
                
                File outputFile = new File( outputPath + "/" + oneSongFile.getName() );
                BufferedWriter bw = new BufferedWriter( new FileWriter( outputFile , true ) );
                
                // 2. remove all digits
                bw.write( outputSb.toString().replaceAll("\\d+", " ").replaceAll("\\s+", " ").toLowerCase() );
                bw.close();
                
            } catch( IOException e ){e.printStackTrace();}
            
        }
        
    }

}
