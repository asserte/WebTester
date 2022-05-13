package com.test.commons.readers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.junit.Assert;

import com.test.commons.Constants;

public class PdfReader {

	private static final Log LOG = LogFactory.getLog(PdfReader.class);

	/**
	 * Takes last modified pdf from downloads directory and checks if provided text
	 * by parameters exists in pdf file. If text doesn't exist Assert exception will
	 * be thrown.
	 * 
	 * @param reqTextInPDF
	 * @throws IOException
	 */
	public void checkIfExistsInPDF(String reqTextInPDF) throws IOException {
		boolean flag = false;
		try (PDDocument document = PDDocument.load(getLastModified(Constants.DOWNLOAD_DIRECTORY))) {

			document.getClass();

			if (!document.isEncrypted()) {

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(document);

				// split by whitespace
				String lines[] = pdfFileInText.split("\\r?\\n");
				for (String line : lines) {
					if (line.contains(reqTextInPDF)) {
						LOG.debug("Search result found in this line: " + line);
						flag = true;
						break;
					}
				}
			}
		}
		Assert.assertTrue(reqTextInPDF + " not found in pdf file", flag);
	}

	/**
	 * Takes last modified pdf from downloads directory and checks if provided text
	 * by parameters doesn't exist in pdf file. If text exists Assert exception will
	 * be thrown.
	 * 
	 * @param reqTextInPDF
	 * @throws IOException
	 */
	public void checkIfDoesNotExistInPDF(String reqTextInPDF) throws IOException {
		boolean flag = false;
		try (PDDocument document = PDDocument.load(getLastModified(Constants.DOWNLOAD_DIRECTORY))) {

			document.getClass();

			if (!document.isEncrypted()) {

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(document);

				// split by whitespace
				String lines[] = pdfFileInText.split("\\r?\\n");
				for (String line : lines) {
					if (line.contains(reqTextInPDF)) {
						LOG.debug("Search result found in this line: " + line);
						flag = true;
						break;
					}
				}
			}
		}
		Assert.assertFalse(reqTextInPDF + " not found in pdf file", flag);
	}

	/**
	 * Returns last modified file in directory provided by parameter
	 * 
	 * @param directoryFilePath
	 * @return File
	 */
	public static File getLastModified(String directoryFilePath) {
		File directory = new File(directoryFilePath);
		File[] files = directory.listFiles(File::isFile);
		long lastModifiedTime = Long.MIN_VALUE;
		File chosenFile = null;

		if (files != null) {
			for (File file : files) {
				if (file.lastModified() > lastModifiedTime) {
					chosenFile = file;
					lastModifiedTime = file.lastModified();
				}
			}
		}
		return chosenFile;
	}
}
