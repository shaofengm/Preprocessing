package preprocessing;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import controller.InvalidPath;

public class Stemming implements PreprocessingStage{
    
    private File folder;
    
    public Stemming( String stemmingPath ) throws InvalidPath{
        folder = new File( stemmingPath );
        if( !folder.isDirectory() ){
            throw new InvalidPath( "ERROR: invalid path for the stemming folder" );
        }
        else if( folder.listFiles().length == 0 ){
            System.out.println( "WARNING: empty stemming folder." );
        }
    }
    
    private Map<String,String> getStemDict(){
      //** Load the stemming dict
        Map<String,String> stemDict = new HashMap<String,String>();
        File[] stemFiles = folder.listFiles();
        for( int i = 0 ; i < stemFiles.length ; i++ ){
            File stemFile = stemFiles[i];
            try{
                BufferedReader dictReader = new BufferedReader( new FileReader( stemFile ) );
                String dictLine = "";
                while( ( dictLine=dictReader.readLine() ) != null ){
                    String[] dictTokens = dictLine.split("\\s+");
                    if( dictTokens.length != 2 ){
                        return null;// it's a sign of error.
                    }
                    stemDict.put( dictTokens[0] , dictTokens[1] );
                }
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }

        return stemDict;
    }

    @Override
    public void pipe(String fromDir, String toDir) {
        
        String inputDirStr  = fromDir;
        String outputPath   = toDir;
        
        Map<String,String> stemDict = getStemDict();
        
        File inputDir = new File( inputDirStr );
        File[] songFiles = inputDir.listFiles();
        
        
        for( int i = 0 ; i < songFiles.length ; i++ ){
            if( i % 1000 == 0 || i == songFiles.length-1 ){
                System.out.println( "stemming file " + i + "/" + (songFiles.length-1) );
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
                    if( !stemDict.containsKey( thisWord ) ){
                        outputSb.append( thisWord + " ");
                    }else{
                        outputSb.append( stemDict.get(thisWord) + " " );
                    }
                }
                
                File outputFile = new File( outputPath + "/" + oneSongFile.getName() );
                BufferedWriter bw = new BufferedWriter( new FileWriter( outputFile , true ) );
                bw.write( outputSb.toString() );
                bw.close();
                
            } catch( IOException e ){e.printStackTrace();}
            
        }
    }
    
}
