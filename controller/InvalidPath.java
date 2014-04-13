package controller;

import java.io.IOException;

public class InvalidPath extends IOException{
    public InvalidPath(){
        this( "ERROR: received an invalid path." );
    }
    
    public InvalidPath( String info ){
        super( info );
    }
}
