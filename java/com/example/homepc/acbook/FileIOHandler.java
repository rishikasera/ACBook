package com.example.homepc.acbook;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by home pc on 04/02/2018.
 */

public class FileIOHandler {


    File file;
    FileOutputStream outputStream;

    public FileIOHandler() {
    }

    public FileIOHandler(String fName) {
        file  = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fName);
        try {
            if(isFileExists(file)){
                file.delete();
                file.createNewFile();
            }else{
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isFileExists(File file){
        if(file.exists())
            return true;
        return false;
    }


     public void writeFile(String data){

         try {

             outputStream = new FileOutputStream(file);
             outputStream.write(data.getBytes());
             outputStream.close();

         } catch (IOException e) {
             e.printStackTrace();
         }
     }

}
