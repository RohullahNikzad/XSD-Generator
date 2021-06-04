package Aufgabe4Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;


public class Copy {



	private static final Logger Log = Logger.getLogger(Copy.class);



	public static boolean copy(File source, File dest) throws Exception {




		FileInputStream instream = new FileInputStream(source);
		FileOutputStream outstream = new FileOutputStream(dest);


			// wenn die Quelldatei nicht lesbar ist Fehler werfen
			if (!source.canRead()) {
				throw new RuntimeException("source file is not readable");
			}
			// wenn das Verzeichnis für die Zieldatei nicht schreibbar ist Fehler werfen
            if (!dest.getParentFile().canWrite()) {
            	throw new RuntimeException("destination can not be found");

            }



			byte[] buffer = new byte[1024];

			int length;
			/*
			 * copying the contents from input stream to output stream using read and write
			 * methods
			 */
			while ((length = instream.read(buffer)) > 0) {
				outstream.write(buffer, 0, length);
			}

			 //System.out.println("File copied successfully!!");
		      Log.debug("File copied successfully");

			 return true;

		}
		}





































