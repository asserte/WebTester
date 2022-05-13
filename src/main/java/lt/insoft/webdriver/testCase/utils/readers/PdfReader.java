package lt.insoft.webdriver.testCase.utils.readers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.test.commons.Constants;

public class PdfReader {

	private static final Log LOG = LogFactory.getLog(PdfReader.class);

	/**
	 * Takes last modified pdf from downloads directory and checks if provided text
	 * by parameters exists in pdf file. Return Boolean value
	 * 
	 * @param reqTextInPDF
	 * @return Boolean
	 * @throws IOException
	 */
	public Boolean checkIfExistsInPDF(String reqTextInPDF) throws IOException {
		return checkIfExistsInPDF(getLastModified(Constants.DOWNLOAD_DIRECTORY), reqTextInPDF);
	}

	/**
	 * Takes file from the parameters and checks if provided text by parameters
	 * exists in that file. Return Boolean value
	 * 
	 * @param file
	 * @param reqTextInPDF
	 * @return Boolean
	 * @throws IOException
	 */
	public Boolean checkIfExistsInPDF(File file, String reqTextInPDF) throws IOException {
		boolean flag = false;
		try (PDDocument document = PDDocument.load(file)) {

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
		return flag;
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
