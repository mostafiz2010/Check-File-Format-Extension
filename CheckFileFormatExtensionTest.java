package test.CheckFileForatExtenstion;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import file.check.content.CheckFileFormatExtension;

@RunWith(Parameterized.class)
public class CheckFileFormatExtensionTest {
	
	private String inputFile;
	private String expected;
	private String actual;
	
	private String resources = "src/test/resources";
	private String dipFolder = "dip_storage";
	private String dipID = "dip_ID";
	private String dataFolder = "data";
	
	
	private String dataPath = this.resources + "/"+ "accessRestrictionTestFiles" +"/" + this.dipFolder + "/" + this.dipID + "/" + this.dataFolder + "/" ;
	private CheckFileFormatExtension checker;
	private Map<String, List<String>> result;
	

	
	public CheckFileFormatExtensionTest(String input, String output){
		this.inputFile = input;
		this.expected = output;
	}
	
	@BeforeClass
	public static void startTheTest(){
		System.out.println("File Format Extenstion Test start");
		System.out.println();
	}
	
	@Parameters
	public static Collection<String[]> testCollections(){
		
		String expectedOutputs [][] = {	{"free_doc_File.doc", "ACCEPTED"},
										{"Protected_doc_File.doc", "ACCEPTED"},
										{"free_pdf_File.pdf", "ACCEPTED"},
										{"Protected_pdf_File.pdf", "ACCEPTED"},
										{"free_xls_File.xls", "ACCEPTED"},
										{"Protected_xls_File.xls", "ACCEPTED"},
										{"image1.jpeg", "ACCEPTED"},
										
										{"free_docx_File.docx", "PREFERED"},
										{"Protected_docx_File.docx", "PREFERED"},
										{"free_ODS_File.ods", "PREFERED"},
										{"Protected_ODS_File.ods", "PREFERED"},
										{"free_ODT_File.odt", "PREFERED"},
										{"Protected_ODT_File.odt", "PREFERED"},
										{"free_xlsx_File.xlsx", "PREFERED"},
										{"Protected_xlsx_File.xlsx", "PREFERED"},
										
										{"free_Zip_File.zip", "NOT ACCEPTED"},
										{"Protected_Zip_File.zip", "NOT ACCEPTED"}
										
									}; 
										
		return Arrays.asList(expectedOutputs);  
		
	}

	@Before
	public void setup() throws Exception{
		
		checker = new CheckFileFormatExtension(dataPath + inputFile);
		
		result = checker.scanFileList();
		
		if(!result.isEmpty()){
			
			for(String fileList : result.keySet()){
				
				actual = fileList;
			}
		}
	}
	
	@Test
	public void testAccessRestrictionRecognitionFile() throws Exception{
		
		assertEquals(getExpected(),getActual());
	}
	
	@AfterClass
	public static void endTheTest(){
		System.out.println("File Format Extenstion Test Done");
	}
	
	public String getExpected() {
		return expected;
	}

	public void setExpected(String expected) {
		this.expected = expected;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}
	

}
