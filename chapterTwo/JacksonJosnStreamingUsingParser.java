package jackson.chapter.two;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonToken;
import model.EmployeeForJson;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JacksonJosnStreamingUsingParser class
 *
 * The exercise is follow from the book RESTFull Java Web Services (second edition)
 * Chapter 2 Java IPAs for JSON Processing page 50 to 51.
 *
 * @author David Sajdl
 *
 */
public class JacksonJosnStreamingUsingParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(JacksonJosnStreamingUsingParser.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			LOGGER.info("Class name: {}", JacksonJosnStreamingUsingParser.class.getName());
			String fileName = "/my_second_json-array.json";
			List<EmployeeForJson> list = new JacksonJosnStreamingUsingParser().buildEmployeeListByUsingParser(fileName);
			LOGGER.info("\nEmloyee list: {}", list);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public List<EmployeeForJson> buildEmployeeListByUsingParser(String fileName) throws IOException{

		// read json sream
		InputStream inputStream = JacksonJosnStreamingUsingParser.class.getResourceAsStream(fileName);
		// create Streaming parser
		JsonParser jsonParser = new JsonFactory().createParser(inputStream);

		// using binding features from ObjectMaper for populating employee Object
		ObjectMapper objMapper = new ObjectMapper();
		List<EmployeeForJson> empList = new ArrayList<EmployeeForJson>();
	        EmployeeForJson empl = null;

		// loop until the last token or steam is opened
		while(!jsonParser.isClosed())
		{
			JsonToken jsonToken = jsonParser.nextToken();
			//if last token break the loop
			if(jsonToken == null) break;
			//start object
			if(jsonToken.equals(JsonToken.START_OBJECT))
			{
				//Use the objectMapper to bind the current object
        	        	//to Employee object
               			 empl = objMapper.readValue(jsonParser, EmployeeForJson.class);
          			      empList.add(empl);
			}
		}
		// close input stream
		if(inputStream != null) inputStream.close();
		if(jsonParser != null) jsonParser.close();
		return empList;
	}
}
