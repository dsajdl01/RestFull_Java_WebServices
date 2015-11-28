package jackson.chapter.two;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import json.chapter.two.JsonProcessByJSRstreamming;
import json.chapter.two.ProcessObjectToJsonByJSRstreamming;
import model.Employee;
/**
 * JacksonTreeModelReaderJSONFile class
 *
 * The exercise is follow from the book RESTFull Java Web Services (second edition)
 * Chapter 2 Java IPAs for JSON Processing page 46 to 48
 *
 * @author David Sajdl
 *
 */
public class JacksonTreeModelReaderJSONFile {

	private static final Logger LOGGER = LoggerFactory.getLogger(JacksonTreeModelReaderJSONFile.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			String fileName = "/my_second_json-array.json";
			String modifyFileName = "my_secondANDmodify_json-array.json";
			LOGGER.info("Class name: {}\n", JacksonTreeModelReaderJSONFile.class.getName());
			List<Employee> listEmpBeforeModification = new JsonProcessByJSRstreamming().builtEmploeeList(fileName);
			LOGGER.info("\nList of employees to be Modified: {}", listEmpBeforeModification.toString());
			new JacksonTreeModelReaderJSONFile().readJsonFileAndFindNote(fileName, modifyFileName, "email", "ds@example.com", "dsajdl01@example.com");
			LOGGER.info("\nList of employees has been successfully Modified and save as: {}", modifyFileName );
		}
		catch(ParseException | IOException e)
		{
			e.printStackTrace();
		}
	}

	public void readJsonFileAndFindNote(String fileName, String modifyFileName, String key,String oldValue, String newValue) throws JsonProcessingException, IOException{

		// Read json employee file which I created by using class ProcessObjectToJsonByJSRstreamming
		InputStream inputStream =  ProcessObjectToJsonByJSRstreamming.class.getResourceAsStream(fileName);

		// create ObjectMapper which contain tree structure for JSON instance
		ObjectMapper objMapper = new ObjectMapper();
		// read JSON content in to tree
		JsonNode rootNode = objMapper.readTree(inputStream);

		// checking if the JSON content is in the array:
		if(rootNode.isArray()){
			// then iterate over each element in the array
			for(JsonNode objN : rootNode){
				// find node contain  mail
				JsonNode emailNode = objN.path(key);
				if(emailNode.textValue().equals(oldValue)){
					((ObjectNode)objN).put(key, newValue);
				}
			}
		}

		// update and modify tree to aJSON file
		objMapper.writeValue(new File(modifyFileName), rootNode);

		if(inputStream != null){
			inputStream.close();
		}
	}

}
