import preprocessing.PreprocessingStage;
import preprocessing.PureWordFilter;
import preprocessing.Stemming;
import preprocessing.StopListFilter;
import controller.InvalidPath;
import tools.ConfigReader;
import tools.ContentReader;

import java.io.*;

public class Preprocess {
    
    static String name = "SAMPLE";
            
    public static void main(String[] args){
        
        if( args.length != 2 ){
            guide();
        }
        
        ConfigReader configReader = new ConfigReader(args[0]);

        
        File configFile = new File(args[0]);
        if( !configFile.isFile() ){
            System.out.println( "ERROR: " + args[0] + " isn't a file." );
            guide();
            return;
        }
        
        File dataFolder = new File(args[1]);
        if( !dataFolder.isDirectory() ){
            System.out.println( "ERROR: " + args[1] + " isn't a folder." );
            guide();
            return;
        }
        
        String parentPath = dataFolder.getParent();
        String folderName = dataFolder.getName();
        
        String datasetDir  = dataFolder.getAbsolutePath();
        String pureWordDir = dataFolder + "_words";
        String stemmedDir  = dataFolder + "_words_stem";
        String stoppedDir  = dataFolder + "_words_stem_stop";
        
        mkdir( pureWordDir );
        mkdir( stemmedDir );
        mkdir( stoppedDir );
        
        
        try{
            PreprocessingStage pureWordStage   = new PureWordFilter();
            PreprocessingStage stemStage       = new Stemming( configReader.getStemPath() );
            PreprocessingStage stoplistStage   = new StopListFilter( configReader.getStopPath() );
            
            pureWordStage.pipe(datasetDir, pureWordDir);
            stemStage.pipe(pureWordDir, stemmedDir);
            stoplistStage.pipe(stemmedDir, stoppedDir);
     
        }catch(InvalidPath e){ System.out.println( e.getMessage() ); }
     }

    public static void guide(){
        System.out.println("java Preprocess configPath datasetFolderPath");
        System.out.println("  Example: java Preprocess /home/nlp/config/config.txt  /home/nlp/datasetFolderPath");
    }
    
    public static void mkdir(String path){
        File f = new File( path );
        if( f.exists() ){
            delete( path );
        }
        
        f.mkdir();
    }
    
    public static void delete(String path){
        File f = new File(path);
        if( f.isFile() ){
            f.delete();
        }else{
            File[] subFiles = f.listFiles();
            for( int i = 0 ; i < subFiles.length ; i++ ){
                delete( subFiles[i].getAbsolutePath() );
            }
            f.delete();
        }
    }

}
