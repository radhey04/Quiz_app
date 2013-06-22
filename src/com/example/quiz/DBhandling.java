package com.example.quiz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import android.os.Environment;
import android.util.Log;

public class DBhandling{
	
	public String dirpath="";
	public String dirname="/NABquiz";
	public void chkdir()
	{
		dirpath=Environment.getExternalStorageDirectory() + dirname;
		File direct = new File(dirpath);
        if(!direct.exists())
         {
             if(direct.mkdir()) 
               {
            	 Log.d("Debug_dbhandling","Created the directory");
                //directory is created;
               }
         }
        else
        {
        	Log.d("Debug_dbhandling","Directory exists");
        }
        
	}

    @SuppressWarnings("resource")
    public boolean importDB(String DatabaseName) 
    {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();
            String PackageName="com.example.quiz";
            
            if (sd.canWrite()) {
                String currentDBPath = "//data//" + PackageName + "//databases//" + DatabaseName;
                String backupDBPath  = dirname +"/"+ DatabaseName;

                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(sd, backupDBPath);

				FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Log.d("Debug_dbhandling_import", backupDB.toString());
                return true;
            }
        } catch (Exception e) {
        	Log.d("Debug_dbhandling_import", "Exception => "+e.toString());
        }
        return false;
    }
//exporting database 
    @SuppressWarnings("resource")
	public void exportDB(String DatabaseName) 
    {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            String PackageName="com.example.quiz";
            chkdir();
            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + PackageName
                        + "//databases//" + DatabaseName;
                String backupDBPath  = dirname +"/"+ DatabaseName;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Log.d("Debug_dbhandling_export", "Exported the DB to "+backupDB.toString());                
            }
        } catch (Exception e) {
        	Log.d("Debug_dbhandling_export", "Exception => "+e.toString());        }
    }
}