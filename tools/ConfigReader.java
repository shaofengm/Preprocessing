package tools;
import java.io.*;
import java.util.*;

import controller.InvalidPath;

public class ConfigReader {
    
    private static String stemPath;
    private static String stopPath;
    
    public ConfigReader( String configPath ){
        ArrayList<String> configLines = new ArrayList<String>();
        
        try{
            BufferedReader br = new BufferedReader( new FileReader( new File( configPath ) ) );
            String line = "";
            while( ( line=br.readLine() ) != null ){
                configLines.add(line);
            }
            
            for( int i = 0 ; i < configLines.size() ; i++ ){
                String thisLine = configLines.get(i);
                if( thisLine.length() > "stemmingPath".length() ){
                    if( thisLine.substring(0, "stemmingPath".length()).equals("stemmingPath") ){
                        String[] tokens = thisLine.split("=");
                        stemPath = tokens[1].trim();
                    }
                    if( thisLine.substring(0, "stoplistPath".length()).equals("stoplistPath") ){
                        String[] tokens = thisLine.split("=");
                        stopPath = tokens[1].trim();
                    }
                }
            }
            
        }catch(IOException e){ e.printStackTrace(); }
    }
    
    public String getStemPath(){
        return stemPath;
    }
    
    public String getStopPath(){
        return stopPath;
    }
    
}
