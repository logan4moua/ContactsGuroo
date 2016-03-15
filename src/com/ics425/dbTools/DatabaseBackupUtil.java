package com.ics425.dbTools;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import com.ics425.config.Config;

public class DatabaseBackupUtil {
//	public static boolean backupDatabase(){
//		return backupDataWithDatabase(
//				Config.DB_dumpExePath, Config.DB_HOST, Config.DB_PORT, 
//				Config.DB_USERNAME, Config.DB_PASSWORD, Config.DB_TABLE,
//				Config.DB_backupPath);
//	}
	
	/**
	 * 
	 * @param dumpExePath
	 * @param host
	 * @param port
	 * @param user
	 * @param password
	 * @param database
	 * @param backupPath
	 * @return
	 */
	public static boolean backupDataWithDatabase(String dumpExePath, String host, String port, String user, String password, String database, String backupPath) {
		
        boolean status = false;
        try {
            Process p = null;
 
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String filepath = "backup(with_DB)-" + database + "-" + host + "-(" + dateFormat.format(date) + ").sql";
 
            String batchCommand = "";
            if (password != "") {
                //Backup with database
                batchCommand = "mysqldump -h " + host + " --port " + port + " -u " + user + " --password=" + password + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
            } else {
                batchCommand = "mysqldump -h " + host + " --port " + port + " -u " + user + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
            }
 
            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(batchCommand);
 
            if (p.waitFor()==0) {
   
                System.out.println("Backup created successfully for with DB " + database + " in " + host + ":" + port);
                return true;
            } else {
                System.out.println("Could not create the backup for with DB " + database + " in " + host + ":" + port);
                return false;
            }
 
        } catch (IOException ioe) {
        	System.out.println(ioe.getMessage());
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        return status;
    }
	
	public static boolean backupDatabase() {
	    try {

	        /*NOTE: Getting path to the Jar file being executed*/
	        /*NOTE: YourImplementingClass-> replace with the class executing the code*/
//	        CodeSource codeSource = DatabaseBackupUtil.class.getProtectionDomain().getCodeSource();
//	        File jarFile = new File(codeSource.getLocation().toURI().getPath());
//	        String jarDir = jarFile.getParentFile().getPath();
	    	DateFormat today = new SimpleDateFormat("dd-mm-yyyy");
	    	String jarDir = System.getProperty("user.home");


	        /*NOTE: Creating Database Constraints*/
	        String dbName = Config.DB_NAME;
	        String dbUser = Config.DB_USERNAME;
	        String dbPass = Config.DB_PASSWORD;

	        /*NOTE: Creating Path Constraints for folder saving*/
	        /*NOTE: Here the backup folder is created for saving inside it*/
	        String folderPath = Config.DB_backupPath;
	        
	        /*NOTE: Creating Folder if it does not exist*/
	        File f1 = new File(folderPath);
	       // if(f1.mkdir()){
	        	
	        	/*NOTE: Creating Path Constraints for backup saving*/
	        	/*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
	        	String savePath = "\"" + folderPath + "\\" + today.format(new Date())+"-backup.sql\"";
	        	//String savePath = "\"" + folderPath + "\\backup.sql\"";
	        	System.out.println("saving to: "+savePath);
	        	
	        	/*NOTE: Used to create a cmd command*/

	        	String executeCmd = "mysqldump -u" + dbUser + " -p" + dbPass + " --databases " + dbName + " -r " + savePath;
	        	System.out.println("command: "+executeCmd);
	        	
	        	/*NOTE: Executing the command here*/
	        	Runtime runtime = Runtime.getRuntime();
	        	Process runtimeProcess = runtime.exec(executeCmd);
	        	int processComplete = runtimeProcess.waitFor();

	        	/*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
	        	if (processComplete == 0) {
	        		System.out.println("Backup Complete");
	        		return true;
	        	} else {
	        		System.out.println("Backup Failure");
	        		return false;
	        	}
	       // }


	    } catch (IOException | InterruptedException ex) {
	        JOptionPane.showMessageDialog(null, "Error at Backuprestore" + ex.getMessage());
	    }
	    return false;
	}
}