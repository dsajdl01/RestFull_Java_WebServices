package jackson.chapter.two;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.core.type.TypeReference;

import model.EmployeeForJson;

/**
 * JacksonMappingJSONGeneralizedObject class
 *
 * The exercise is follow from the book RESTFull Java Web Services (second edition)
 * Chapter 2 Java IPAs for JSON Processing page 48 to 49.
 *
 * @author David Sajdl
 *
 */
public class JacksonMappingJSONGeneralizedObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(JacksonMappingJSONGeneralizedObject.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			LOGGER.info("Class name: {}", JacksonMappingJSONGeneralizedObject.class.getName());
			String jsonStr = "{\"customerId\":123,\"firstName\":\"John\",\"lastName\":\"Chain\",\"email\":\"jc@example.com\",\"hireDate\": \"2008-10-16\"}";
			Map<String, String> mapPrp = new JacksonMappingJSONGeneralizedObject().mappingSimpleBinding(jsonStr);

			LOGGER.info("Map propertirs: {}", mapPrp);
			String fileName = "/my_second_json-array.json";
			List<EmployeeForJson> list = new JacksonMappingJSONGeneralizedObject().loadEmployeeList(fileName);
			LOGGER.info("Employee list {}", list);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<EmployeeForJson> loadEmployeeList(String fileName) throws IOException{

		System.out.println("in");
		//read json file data to String
	        InputStream inputStream = getClass().getResourceAsStream(fileName);

        	//create ObjectMapper instance
        	ObjectMapper objectMapper = new ObjectMapper();

        	//convert json string to object
        	CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, EmployeeForJson.class);

        	/** to map json string into Java object the setter need to have
        	 * the same methods name as JSON keys names otherwise the exception is thrown!!!
        	 * Therefore the EmployeeForJson class is used for this method */
	        List<EmployeeForJson> emps = objectMapper.readValue(inputStream, collectionType);
        	return emps;
	}
	/**
	 * This method enable to convert JSON string into Map by using Jackson data binding reader APIs */
	public Map<String, String> mappingSimpleBinding(String JSONDataStr) throws IOException{
		ObjectMapper objMapper = new ObjectMapper();
		// Properties will store name and value pairs read from Json String
		Map<String, String> properties = objMapper.readValue(JSONDataStr, new TypeReference<Map<String, String>>() {});
		return properties;
	}
}
