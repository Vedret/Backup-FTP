package ftp.com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Upload {
	
	
	
	
	public static void ftpupload(String server, int port, String user, String pass,String dirSaDokumentima,String dirNaServeru) throws IOException {
		 
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		
		File folder = new File(dirSaDokumentima);
		File[] listOfFiles = folder.listFiles();
		System.out.println(listOfFiles);

		    for (int i = 0; i < listOfFiles.length; i++) {
		    	 System.out.println("File " + listOfFiles[i].getName());
		   
		
		FTPClient ftpClient = new FTPClient();
		try{
			
		
		ftpClient.connect(server,port);
		ftpClient.login(user, pass);
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		
		File firstLocalFile = new File("test.rar");
		System.out.println(firstLocalFile);
		String firstRemoteFile="/test/test.rar";
		System.out.println(firstRemoteFile);
		InputStream inputStream = new FileInputStream(firstLocalFile);
		
		boolean done= ftpClient.storeFile(firstRemoteFile, inputStream);
		
		
		inputStream.close();
		if(done){
			 System.out.println("The first file is uploaded successfully.");
			 FileWriter fstream=new FileWriter("uploadLOG.txt",true);
	         BufferedWriter out=new BufferedWriter(fstream);
	         out.write("Poslano " + dateFormat.format(date) +'\n' );
	         out.close();
	         listOfFiles[i].delete(); 
		}else {
			
			FileWriter fstream=new FileWriter("uploadLOG.txt",true);
	         BufferedWriter out=new BufferedWriter(fstream);
	         out.write("Greska u slanju : " + dateFormat.format(date)+'\n');
	         out.close();
		}
		
		}catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }finally{
        	if(ftpClient.isConnected()){
        		ftpClient.logout();
                ftpClient.disconnect();
        		
        	}
        }
		
	 } 
		       
	}
}
	


