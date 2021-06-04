package Aufgabe4Files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;

public class Aufgabe4 {
	final protected static String outDirectory = "C:/Users/nikzar/Desktop/Xsd-generated";


	private static final Logger Log = Logger.getLogger(Aufgabe4.class);

	public static boolean copyFiles(String input, String filter, String output) {

	     Path source = Paths.get(input);

	     File pathAsFile = new File(output);


         if(!Files.exists(Paths.get(outDirectory))) {
		      pathAsFile.mkdir();
	             }

		 try {

		 List<String> data = Files.readAllLines(source);  //read source line by line


		 for(String string: data)
		 {

			 File directory = new File(string );
			 FileFilter fileFilter = new FileFilter() {         // using fileFilter to filter out only .xsd files

				@Override
				public boolean accept(File pathname) {
					return ! pathname.isDirectory() &&  pathname.getName().endsWith(filter);
				}
			};

			    File[] directoryListing = directory.listFiles(fileFilter);    //list all the files with .xsd extension in directoryListing

                if(!directory.canRead()) {
                	System.out.println("The following directories do not exist: "+  directory.getName());
                }


           loop:    for(File file: directoryListing) {


					 System.out.println("files are:" + file.toString());

					 File dest = creationOfDocumentXsdFile(file, output);

					         if(dest == null) {
					        	 continue loop;
					         }


				  	     Path tempfile =  Paths.get("C:\\Users\\nikzar\\Desktop\\tempfile.xsd");  // temporary file to store the contents of the external file
				  	     String  ss =  tempfile.toString();                                             // for further configuration
				  	     File tempFileName = new File(ss);
				  	     tempFileName.getAbsolutePath();

				  	     externalFileConfigurationToDesiredFile(file, tempFileName);

				  	     swapFilesContents(dest, tempFileName);

				             Copy.copy(dest ,new File(output + "\\" + dest.getName()));


                    // check if the files already exist in output folder if so, then delete them all

	 				     for(File existedfile: pathAsFile.listFiles()) {
	 			        	 if(!existedfile.isDirectory()) {
	 							existedfile.delete();
	 						}
	 			         }

	 				  	  Copy.copy(file,new File(output + "\\" + file.getName()));

				}
		 }

		  return true;

		 }catch(Exception e) {

			 Log.error("File can not be copied ");

			   return false;
		 }

	   }

	public static File creationOfDocumentXsdFile(File file, String output) {

		  File dest = null;
		  try {
		  List<String>  allLines = Files.readAllLines(Paths.get(file.toString()));
          for(String line : allLines) {
        	   System.out.println(line);

            		   if(line.contains("<xs:element name=\"data\">")){
                       // break;

                         String fileName = file.getName();
                         String[] splittedFileName = fileName.split("\\.xsd");
			   	         String desiredName = splittedFileName[0] +  "-document" + ".xsd" ;

				   	     File desiredFileName = new File(desiredName);
				   	     String pathdest = desiredFileName.getAbsolutePath();
				   	     dest = new File(pathdest);
				   	      System.out.println(desiredName);

				   	      BufferedReader br = new BufferedReader(new FileReader(file));
		     			  BufferedWriter bw = new BufferedWriter(new FileWriter(dest));

						   while ((pathdest = br.readLine())!=null) {

						     bw.write(pathdest);
						     bw.newLine();
						     bw.flush();
						     }

                        }else {

	    	              if(line.contains("</xs:schema>")) {

	    	               Copy.copy(file,new File(output + "\\" + file.getName()));

	    	              }
	    	   }
              }


		  }catch(Exception e ) {
			  System.out.println("File is not readable");
		  }
		return dest;

        }

	public static boolean externalFileConfigurationToDesiredFile(File file, File tempFileName) {


		 String filename = file.getName();
		 String[] splittedfileName = filename.split("\\-");


		 Path filesPath = Paths.get("C:\\Users\\nikzar\\Desktop\\configurationfile.xsd");
  	     String  s =  filesPath.toString();
  	     File fileName = new File(s);

  	     fileName.getAbsolutePath();
  	     fileName.getName();

         try {
  	     BufferedReader myfile = new BufferedReader(new FileReader(fileName));
  	     StringBuffer inputBuffer = new StringBuffer();
  	     String line;



  	       while ((line = myfile.readLine()) != null) {


	  	    	 if(line.contains("<xs:include schemaLocation=\"filename\" />")) {
		  	    	   line = line.replaceAll("filename", file.getName().toString());
	  	    	 }

	  	    	 if(line.contains("targetNamespace=\"http://www.innovas.de/xmlns/filename0/v1.0/filename1\"")) {
	  	    		 line = line.replaceAll("filename0", splittedfileName[0]);
	  	    		 line = line.replaceAll("filename1", splittedfileName[1]);
	  	    	 }

	  	    	 if(line.contains("xmlns=\"http://www.innovas.de/xmlns/filename0/v1.0/filename1\"")) {

	  	    		 line = line.replaceAll("filename0", splittedfileName[0]);
	  	    		 line = line.replaceAll("filename1", splittedfileName[1]);
	  	    	 }

  	    	   inputBuffer.append(line);
  	    	   inputBuffer.append('\n');

  	       }

  	       myfile.close();
  	       String inputstr = inputBuffer.toString();

  	       FileOutputStream fileOut = new FileOutputStream(tempFileName);
  	       fileOut.write(inputstr.getBytes());
  	       fileOut.close();

  	       return true;
         }catch(Exception e) {
        	 System.out.println("File ist not Found");
         }
		return false;

	}

	public static void swapFilesContents(File file1 , File file2 ) {

		  //copy the contents of source file  to temp file

	        String file2Path = file2.getAbsolutePath();
	  	  try {

	  	    String tempFile  = "temp.xsd";
	  	    File tempFileName =  new File(tempFile);
	  	    String r = tempFileName.getAbsolutePath();
	  	    File tempFileNameFinal = new File(r);


			BufferedReader br = new BufferedReader(new FileReader(file1));
			BufferedWriter bw = new BufferedWriter(new FileWriter(tempFileNameFinal));

			while ((r = br.readLine())!=null) {

			     bw.write(r);
			     bw.newLine();
			     bw.flush();

			}

			//copy the contents of destination file to source file
			 br = new BufferedReader(new FileReader(file2));
			 bw = new BufferedWriter(new FileWriter(file1));
			 while ((file2Path = br.readLine()) != null) {
			 bw.write(file2Path);
			 bw.newLine();
			 bw.flush();
			 }

	  	   }catch(Exception e) {
	  	    	 System.out.println(" File is not Found");
	  	     }

	        }

		}








































