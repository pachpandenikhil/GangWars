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
		
		Map<Integer, Integer> faultyTestCases = new HashMap<>();
		//faultyTestCases.put(0, 0);
		//aultyTestCases.put(449, 449);
		//faultyTestCases.put(792, 792);
		//faultyTestCases.put(821, 821);
		//faultyTestCases.put(1336, 1336);
		//faultyTestCases.put(1371, 1371);
		//faultyTestCases.put(2767, 2767);
		//faultyTestCases.put(2825, 2825);
		
		
		long startTime = System.currentTimeMillis();
		int testCases = 100;
		int startTestCase = 0;
		for(int i = startTestCase; i < testCases; i++) {
			if(faultyTestCases.containsKey(i)) {
				System.out.println("Skipping test case : " + i);
				continue;
			}
			File testcaseInputFile = new File("C:\\Users\\pachp\\Google Drive\\workspace\\AI_HW_2\\bin\\" + i + ".in");
			File inputFile = new File("C:\\Users\\pachp\\Google Drive\\workspace\\AI_HW_2\\bin\\input.txt");
			if(testcaseInputFile.renameTo(inputFile)){
				homework.main(null);
				File testcaseOutputFile = new File("D:\\AI\\Homeworks\\Homework2\\testcases\\" + i + ".out");
				File outputFile = new File("C:\\Users\\pachp\\Google Drive\\workspace\\AI_HW_2\\bin\\output.txt");
				try {
					String expectedOutput = readFile(testcaseOutputFile.getAbsolutePath(), Charset.defaultCharset());
					String actualOutput = readFile(outputFile.getAbsolutePath(), Charset.defaultCharset());
					assertThat("TestCase "+ i + " failed!",expectedOutput, IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(actualOutput));
					/*if(FileUtils.contentEquals(testcaseOutputFile, outputFile)) {
						
					}
					else {
						assertTrue("TestCase " + i + " failed!", false);
					}*/
					
				} catch (IOException e) {
					assertTrue("Exception occured : " + e.getMessage(), false);
				}
				finally {
					inputFile.renameTo(testcaseInputFile);
				}
				
				
			} else {
				assertTrue("Failed to rename file!", false);
			}
			//System.out.println("Done");
		}
		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds");
	}

}
