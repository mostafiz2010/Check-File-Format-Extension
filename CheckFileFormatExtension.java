package file.check.content;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;


public class CheckFileFormatExtension {
	
	private String inputAddress;
	
	private static final String[] ACCEPTED_FILE_FORMAT = { "pdf", "doc", "xls", "csv", "rtf", "sxw", "png", "jpeg", "jpg","gif", "bmp", "psd", "cpt", "jp2", "jpx", "nef", "crw", "cgm",
													  "dxf", "dwg", "ps", "eps", "ai", "indd", "dwf", "sxc", "json", "mdb", "accdb", "fp5", "fp7", "fmp12", "dbf", "bak", "db", 
													  "dmp", "odb", "mj2", "mp4", "mxf", "mpeg", "mpg", "mov", "asf", "wmv", "ogg", "ogv", "ogx", "ogm", "spx", "flv", "f4v", "mkv",
													  "wav", "acc", "mp3", "aiff", "aif", "wma", "ogg", "oga", "opus", "vrml", "avi", "u3d", "stl", "dxf", "maff", "htm", "html"};
	
	private static final String[] PREFERED_FILE_FORMAT = { "odt", "docx" , "txt","xml", "sgml", "html", "dtd", "xsd", "tiff", "tif", "dng", "svg", "tsv", "ods", "xlsx", "siard", "sql",
													  "flac", "wav", "bwf", "x3d", "dae", "obj", "ply", "xhtml", "xht", "mhtml", "mth", "warc"};
	

	public CheckFileFormatExtension(String input){
		
		this.inputAddress = input;
	}
	

	public Map<String, List<String>> scanFileList() throws Exception{
		
		Map<String, List<String>> valueList = new HashMap<String, List<String>>();
		
		File dataFolder = new File(this.inputAddress);
		
		checkRecursively(dataFolder, valueList);
		
		return valueList;
		
	}
	
	
	private void checkRecursively(File data, Map<String, List<String>> fileListMap) throws Exception{
		
		String result = null;
		
		if(data.isFile()){
			
			result = checkFileExtenstionAndGiveTheDecision(data);
			
			mapWithDecision(result, fileListMap, data);
			
		}else{
		
			for(File child : data.listFiles()){
				
				if(child.isDirectory()){
					
					checkRecursively(child, fileListMap);
					
				}else{
					
					result = checkFileExtenstionAndGiveTheDecision(child);
					    
					mapWithDecision(result, fileListMap, child);
				}
			}
		}
	}

	private String checkFileExtenstionAndGiveTheDecision(File sourceFile) throws Exception{
		
		if (sourceFile == null || !sourceFile.isFile() || !sourceFile.exists()) {
			
			System.out.println("target file '" + sourceFile + "' cannot be found.");
			
	    }
		
		String file = sourceFile.getName().toLowerCase(); 
		
		String extension = FilenameUtils.getExtension(file);
		
		if(Arrays.asList(ACCEPTED_FILE_FORMAT).contains(extension))
			
			return "ACCEPTED";
		
		else if(Arrays.asList(PREFERED_FILE_FORMAT).contains(extension))
			
			return "PREFERED";

		else
			
			return "NOT ACCEPTED";
	}
	
	
	private void mapWithDecision(String resultValue, Map<String, List<String>> fileListMap, File file) throws Exception{
		
		String testResult = resultValue;
		
		List<String> fileList = fileListMap.get(testResult);
		
		if(null == fileList){
			
			fileList = new LinkedList<>();
		}
		
		fileList.add(file.getAbsolutePath());
		
		fileListMap.put(testResult, fileList);
	}
	

	public static void main(String[] args) throws Exception {
		
		String source = "/Users/mostafizur/Desktop/Test_Data/input";
		
		//String source = "/Users/mostafizur/Desktop/DKB_Bank.pdf";
		
		//String source = "src/test/resources/accessRestrictionTestFiles/dip_storage/dip_ID/data";
		
		//String source = "src/test/resources/fileFormatExtensionTestFiles/dip_storage/dip_ID/data/free_doc_File.doc";
		
		CheckFileFormatExtension generator = new CheckFileFormatExtension(source);
		
		Map<String, List<String>> valueList = generator.scanFileList();
		
		if(!valueList.isEmpty()){
		
			for(String fileList : valueList.keySet()){
				
				String key = fileList.toString();
				
				System.out.println(key);
				
				for(String value : valueList.get(key)){
					
					System.out.println(value);
					
				}
				System.out.println();	
			}
		}
	}
	
}
