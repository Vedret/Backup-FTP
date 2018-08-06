package ftp.com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;


public class FTP {

	

	public static void main(String[] args) throws Exception {
		int downloadUpload =0;
		String server=null;
		int port=21;
		String user=null;
		String pass=null;
		String dirNaServeru=null;
		String dirSaDokumentima=null;
		String extenzija=null;
		int ActivePasive=0;
		boolean BackupDAiliNE=false ;
		
		
		
		Properties prop = new Properties();
		InputStream input=null;
		
		try{
			input=new FileInputStream("config.properties");
			prop.load(input);
			downloadUpload=Integer.parseInt(prop.getProperty("downloadUpload"));
			server=prop.getProperty("server");
			user=prop.getProperty("user");
			pass=prop.getProperty("pass");
			dirNaServeru=prop.getProperty("dirNaServeru");
			dirSaDokumentima=prop.getProperty("dirSaDokumentima");
			extenzija=prop.getProperty("extenzija");
			BackupDAiliNE= Boolean.parseBoolean(prop.getProperty("BackupDAiliNE"));
			ActivePasive=Integer.parseInt(prop.getProperty("ActivePasive"));
			
			
			
			
			
			

			
			
		}catch (IOException ex){
			 ex.printStackTrace();
			 FileWriter fstream=new FileWriter("log.txt",true);
	         BufferedWriter out=new BufferedWriter(fstream);
	         out.write(ex.toString());
	         out.close();
		}finally {
			if (input!=null){
				try{
					input.close();
				}catch(IOException e){
					e.printStackTrace();
					 e.printStackTrace();
					 FileWriter fstream=new FileWriter("log.txt",true);
			         BufferedWriter out=new BufferedWriter(fstream);
			         out.write(e.toString());
			         out.close();
	
				
			}
		}
			
			if (BackupDAiliNE){
				SQLConnection.uradiBackup();
				
			}
			if(downloadUpload==1){
				try{
					
		            FTPFunctions ftpobj = new FTPFunctions(server, port, user, pass, ActivePasive);
		            
		            
					
					
		            File folder = new File(dirSaDokumentima);
		    		File[] listOfFiles = folder.listFiles();
		    		

		    	    for (int i = 0; i < listOfFiles.length; i++) {
		            ftpobj.uploadFTPFile( dirSaDokumentima+listOfFiles[i].getName(),listOfFiles[i].getName(),dirNaServeru /* "C:\\saljiTest\\09045.txt"*, "09045.txt", "test/"*/);
		            System.out.println("FTP File uploaded successfully");
		    	     
		            
		    	    File delete = new File (dirSaDokumentima+listOfFiles[i].getName());
					try{
						FileDeleteStrategy.FORCE.delete(delete);
						System.out.println("force delet");
					}catch  (Exception e) {
			            e.printStackTrace();
			        }
		    	    }   
		    	    ftpobj.disconnect();
		    	    
		    	    
		            
		                                        
		            
		    	    
				}catch  (Exception e) {
		            e.printStackTrace();
		        }
				}
			
			
    		                

			}
		
		
      
	}

}
