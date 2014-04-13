package preprocessing;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import controller.InvalidPath;

public class StopListFilter implements PreprocessingStage{
    File folder;
    Set<String> stopSet;
    
    public StopListFilter( String stopListPath ) throws InvalidPath{
        folder = new File( stopListPath );
        if( !folder.isDirectory() ){
            throw new InvalidPath( "ERROR: invalid path for the stoplist folder." );
        }
        else if( folder.listFiles().length == 0 ){
            System.out.println( "WARNING: the stoplist folder is empty." );
        }
        
        stopSet = getStopWordSet(stopListPath);
    }
    
    public void pipe( String fromDir, String toDir ){
        
        String inputDirStr  = fromDir;
        String outputPath   = toDir;

        File songFolder = new File( inputDirStr );
        File[] songFiles = songFolder.listFiles();
        
        for( int i = 0 ; i < songFiles.length ; i++ ){
            if( i % 1000 == 0 || i == songFiles.length-1 ){
                System.out.println( "stoplist processing file " + i + "/" + (songFiles.length-1) );
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
                
                // 1. tokenize
                String[] tokens = inputSb.toString().split("\\s+");
                for( int j = 0 ; j < tokens.length ; j++ ){
                    String thisWord = tokens[j];
                    if( !stopSet.contains( thisWord ) ){
                        outputSb.append( thisWord.toLowerCase() + " ");
                    }
                }
                
                File outputFile = new File( outputPath + "/" + oneSongFile.getName() );
                BufferedWriter bw = new BufferedWriter( new FileWriter( outputFile ) );
                bw.write( outputSb.toString() );
                bw.close();
                
            } catch( IOException e ){e.printStackTrace();}
            
        }
    }
    
    
    private Set<String> getStopWordSet( String stopPath ){
      //** Load the stemming dict
        Set<String> stopSet = new HashSet<String>();
        File[] stopFiles = folder.listFiles();
        for( int i = 0 ; i < stopFiles.length ; i++ ){
            File stopFile = stopFiles[i];
            try{
                BufferedReader stopReader = new BufferedReader( new FileReader( stopFile ) );
                String stopWord = "";
                while( ( stopWord=stopReader.readLine() ) != null ){
                    stopSet.add(stopWord);
                }
                
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }
        return stopSet;
    }
    
}
