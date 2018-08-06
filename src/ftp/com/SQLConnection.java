package ftp.com;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;



import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.tools.bzip2.CBZip2OutputStream;

import net.lingala.zip4j.core.*;
import net.lingala.zip4j.model.*;
import net.lingala.zip4j.util.*;




import com.microsoft.sqlserver.jdbc.SQLServerDriver;



public class SQLConnection {
	
	
public static Connection getConnection(String SQLserver, String username,String password) throws Exception{
	

	
	try{
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://"+SQLserver+":1433;DatabaseName=master";
		
		Class.forName(driver); Connection conn = DriverManager.getConnection(url,username,password);
		System.out.println("Connected");
		return conn;
		} catch(Exception e){	
	
			e.printStackTrace();
			 FileWriter fstream=new FileWriter("SQLConnectionLog.txt",true);
		     BufferedWriter out=new BufferedWriter(fstream);
		     out.write( e.toString());
		     out.newLine();
		     out.close();
		     
		}
	
	
	return null;
	
		

}

	public static void uradiBackup () throws Exception {
		
		
		String SQLserver=null;
		String username=null;
		String password=null;
		String dba=null;
		String putanja=null;
		
		
		Properties prop = new Properties();
		InputStream input=null;
						
						try{
							input=new FileInputStream("config.properties.SQLconnection");
							prop.load(input);	
							 SQLserver=prop.getProperty("SQLServer");
							 password=prop.getProperty("SQLPass");
							 username=prop.getProperty("SQLUserName");
							 dba=prop.getProperty("SQlDba");
							 putanja=prop.getProperty("putanja");
							
						
						
						}catch (IOException ex){
							 ex.printStackTrace();
							 FileWriter fstream=new FileWriter("log.txt",true);
					         BufferedWriter out=new BufferedWriter(fstream);
					         out.write(ex.toString());	         
					         out.close();
						}
		
			try{
	
				Connection con = getConnection(SQLserver,username,password);
				System.out.print(putanja+dba);
				
				PreparedStatement posted=con.prepareStatement("BACKUP DATABASE "+dba+" TO  DISK = '"+putanja+dba+".bak"+"' WITH init,FORMAT ");
				posted.execute();
				
//				ZIPUJ *.BAK
				
				/*try {
				FileInputStream fis = new FileInputStream(putanja+dba+".bak");
	            FileOutputStream fos = new FileOutputStream(putanja+dba+".zip");
	           
	            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
	            byte[] buffer = new byte[1024];
	            int len;
	            while((len=fis.read(buffer)) != -1){
	                gzipOS.write(buffer, 0, len);
	            }
	            //close resources
	            gzipOS.close();
	            fos.close();
	            fis.close();
				} catch (IOException e) {
					 e.printStackTrace();
					 FileWriter fstream2=new FileWriter("Zip_error.txt",true);
			         BufferedWriter out2=new BufferedWriter(fstream2);
			         out2.write( e.toString());
			         out2.newLine();
			         out2.close();   
		             e.printStackTrace();
		        }
				
				
				ZipFile zipFile = new ZipFile(putanja+dba+".zip");
				 ZipParameters parameters = new ZipParameters();
				 File file = new File(putanja+dba+".bak");
				 parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
				 parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
				
				 zipFile.addFile(file, parameters);*/
				
				/*String toCompress = "Compress me!";

				OutputStream os = new FileOutputStream(putanja+dba+".zip");
				try
				{
				  // Use the default compression settings (maximum compression)
				  OutputStream bzos = new BZip2OutputStream(os);
				  try
				  {
				    bzos.write(toCompress.getBytes());
				  }
				  finally
				  {
				    bzos.close();
				  }      
				}
				finally
				{
				  // Calling close here may mean that close will be called several times on the
				  // same stream. That is safe.
				  os.close();
				}
*/
				
				 
		    

				
				
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
			    Date trenutnidate = new Date();
				
				FileWriter fstream1=new FileWriter("OK_Backup.txt",true);
		        BufferedWriter out1=new BufferedWriter(fstream1);
		        out1.newLine();
		        out1.write(dateFormat.format(trenutnidate)+" Uspjesan backup");
		        out1.close();
		        
		         
			} catch(Exception e){
				 e.printStackTrace();
				 FileWriter fstream2=new FileWriter("Restorelog_error.txt",true);
		         BufferedWriter out2=new BufferedWriter(fstream2);
		         out2.write( e.toString());
		         out2.newLine();
		         out2.close();      
			}
		
		
	}

}
	
