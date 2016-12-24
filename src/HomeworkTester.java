import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.text.IsEqualIgnoringWhiteSpace;
import org.junit.Test;

public class HomeworkTester {
	
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	@Test
	public void testHomework() {
		
		long startTime = System.currentTimeMillis();
		int testCases = 100;
		int startTestCase = 0;
		for(int i = startTestCase; i < testCases; i++) {
			File testcaseInputFile = new File("C:\\GangWars\\bin\\" + i + ".in");
			File inputFile = new File("C:\\GangWars\\bin\\input.txt");
			if(testcaseInputFile.renameTo(inputFile)){
				homework.main(null);
				File testcaseOutputFile = new File("C:\\GangWars\\testing\\testcases1\\" + i + ".out");
				File outputFile = new File("C:\\GangWars\\bin\\output.txt");
				try {
					String expectedOutput = readFile(testcaseOutputFile.getAbsolutePath(), Charset.defaultCharset());
					String actualOutput = readFile(outputFile.getAbsolutePath(), Charset.defaultCharset());
					assertThat("TestCase "+ i + " failed!",expectedOutput, IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(actualOutput));
				} catch (IOException e) {
					assertTrue("Exception occured : " + e.getMessage(), false);
				}
				finally {
					inputFile.renameTo(testcaseInputFile);
				}
				
			} else {
				assertTrue("Failed to rename file!", false);
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds");
	}

}
