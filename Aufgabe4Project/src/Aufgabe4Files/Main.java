package Aufgabe4Files;

public class Main {

	public static void main(String[] args) {

		String dirInput = "C:/Users/nikzar/Desktop/Aufgabe4.txt";

		String outputDir = Aufgabe4.outDirectory;
		String filter= ".xsd";


		Aufgabe4.copyFiles(dirInput, filter,outputDir);


	}

}
