package junittest;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Aufgabe4Files.Aufgabe4;

 class Aufgabe4Test {

   @Test
	public void copyingContentsOfSourceToDestTest() throws IOException{

		String file1 = "testFolder\\testFolder1\\claims-automaticReminding-1.0.xsd";
		String  file2 = "testFolder\\testFolder1\\claims-clawback-1.0.xsd";

		// \\testdaten\\claims-automaticReminding-1.0.xsd;


		File sourceFile = new File(file2);
		File destFile = new File(file1);

		List<String> allLinesOld = Files.readAllLines(Paths.get(file1));

        Aufgabe4.copyingContentsOfSourceToDest(sourceFile, destFile);

        List<String>  allLinesNew = Files.readAllLines(Paths.get(file2));

        	 for(int i= 0;i< allLinesNew.size(); i++) {


        Assertions.assertTrue(allLinesOld.get(i).equals(allLinesNew.get(i)));

       }
	}


	@Test
	public void externalFileConfigurationToDesiredFileTest() throws IOException {

		String file1 = "testFolder\\testFolder2\\claims-automaticReminding-1.0.xsd";
		String tempFileName1 = "testFolder\\tempfile.xsd";

		File file2 = new File(file1);
		File tempFileName2 = new File(tempFileName1);

		//List<String> allLinesOld = Files.readAllLines(Paths.get(file1));


	    Aufgabe4.externalFileConfigurationToDesiredFile(file2, tempFileName2);

	    List<String> allLinesNew = Files.readAllLines(Paths.get(tempFileName1));

	    for(int i = 0; i<allLinesNew.size(); i++) {

	    	      Assertions.assertTrue(allLinesNew.get(12).contains("<xs:include schemaLocation=\"claims-automaticReminding-1.0.xsd\" />"));
	    	      Assertions.assertTrue(allLinesNew.get(4).contains("targetNamespace=\"http://www.innovas.de/xmlns/claims/v1.0/automaticReminding\""));
	    	      Assertions.assertTrue(allLinesNew.get(5).contains("xmlns=\"http://www.innovas.de/xmlns/claims/v1.0/automaticReminding\""));
	    }


	    }

	@Test
	public void creationOfDocumentXsdFileTest() throws IOException {

		String file1 = "testFolder\\testFolder3\\claims-expertise-1.0.xsd";

		String output = "testFolder\\testFolder4";
		File file11 = new File(file1);
		String filter = ".xsd";

		 File outputFile =new File(output);


		File dest = Aufgabe4.creationOfDocumentXsdFile(file11, output);

        Assertions.assertTrue(dest.getName().equals("claims-expertise-1.0-document.xsd"));

         File[] dirListing= Aufgabe4.filteringXsdFiles(outputFile, filter);

	     int numOfFiles = dirListing.length;
	     Assertions.assertTrue(numOfFiles == 1);

         Assertions.assertTrue(dirListing[0].getName().equals("claims-expertise-1.0.xsd"));

         List<String> allLinesNew = Files.readAllLines(Paths.get(dirListing[0].toString()));
		    for(int i = 0; i<allLinesNew.size(); i++) {

		    	Assertions.assertTrue(allLinesNew.get(2).contains("targetNamespace=\"http://www.innovas.de/xmlns/claims/v1.0/expertise\" xmlns=\"http://www.innovas.de/xmlns/claims/v1.0/expertise\""));
		    	Assertions.assertTrue(allLinesNew.get(4).contains("xmlns:p=\"http://www.innovas.de/xmlns/claims/v1.0/promise\""));
		    }
		}


	@Test
	public void copyingFilesWithNoElementNameDataLineTest() throws IOException {

		String file1 = "testFolder\\testFolder3\\claims-generalData-1.0.xsd";

		String output = "testFolder\\testFolder5";
		String filter = ".xsd";
		File outputFile = new File(output);

		File file11 = new File(file1);


		Files.readAllLines(Paths.get(file1));

	    Aufgabe4.creationOfDocumentXsdFile(file11, output);


	    File[] dirListing= Aufgabe4.filteringXsdFiles(outputFile, filter);
//		    for(File file: dirListing)
//			 {
	     int numOfFiles = dirListing.length;
	     Assertions.assertTrue(numOfFiles == 1);

		 Assertions.assertTrue(dirListing[0].getName().equals("claims-generalData-1.0.xsd"));


		 List<String> allLinesNew = Files.readAllLines(Paths.get(dirListing[0].toString()));
		    for(int i = 0; i<allLinesNew.size(); i++) {

		    	Assertions.assertTrue(allLinesNew.get(2).contains("targetNamespace=\"http://www.innovas.de/xmlns/claims/v1.0/generaldata\" xmlns=\"http://www.innovas.de/xmlns/claims/v1.0/generaldata\""));
		    	Assertions.assertTrue(allLinesNew.get(5).contains(" xmlns:pd=\"http://www.innovas.de/xmlns/claims/v1.0/policydata\">"));
			}
	}

	@Test
	public void copyFilesTest() throws IOException {

		String source = "testFolder\\testFile.txt";
		String output = "testFolder\\testFolder6";
		File outputFile =new File(output);
		String filter = ".xsd";

       // File sourceFile = new File(source);


		Files.readAllLines(Paths.get(source));

	    Aufgabe4.copyFiles( source, filter ,  output);


	     File[] dirListing= Aufgabe4.filteringXsdFiles(outputFile, filter);

	        int numOfFiles =  dirListing.length;
		    Assertions.assertTrue(numOfFiles == 3);

		    //for(File file: dirListing)
			 //{
				Assertions.assertTrue(dirListing[0].getName().equals("claims-clawback-1.0.xsd") ||dirListing[0].getName().equals("claims-clawback-1.0-document.xsd") || dirListing[0].getName().equals("claims-generalData-1.0.xsd"));

			//}

		    List<String> allLinesNew = Files.readAllLines(Paths.get(dirListing[0].toString()));
		    for(int i = 0; i<allLinesNew.size(); i++) {

		    	Assertions.assertTrue(allLinesNew.get(12).contains("<xs:include schemaLocation=\"claims-clawback-1.0.xsd\" />") ||allLinesNew.get(14).contains("<xs:element name=\"data\">") || allLinesNew.get(2).contains("targetNamespace=\"http://www.innovas.de/xmlns/claims/v1.0/generaldata\""));


		    }
			//}
	}

 }

