package json.chapter.two;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

/**
 * JsonProcessByJSRstreamming class
 *
 * The exercise is follow from the book RESTFull Java Web Services (second edition)
 * Chapter 2 Java IPAs for JSON Processing page 41 to 43.
 *
 * @author David Sajdl
 *
 */
public class JsonProcessByJSRstreamming {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonRepresentationFromObjectModel.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			LOGGER.info("Class name: {}", JsonProcessByJSRstreamming.class.getName());
			String fileName = "/emp-array.json";
			List<Employee> listEmp = new JsonProcessByJSRstreamming().builtEmploeeList(fileName);
			LOGGER.info("\nList of employees: {}", listEmp.toString());
		}
		catch(IOException | ParseException e)
		{
			e.printStackTrace();
		}

	}

	private List<Employee> builtEmploeeList(String fileName) throws IOException, ParseException {

		/**
		 * Read .json file that contains JSON array of employees */
		InputStream inputStream = JsonProcessByJSRstreamming.class.getResourceAsStream(fileName);
		JsonParser jsonParser = Json.createParser(inputStream);

		Employee empl = null;
		List<Employee> list = new ArrayList<Employee>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		// return true in there is next parse
		while(jsonParser.hasNext()){

			//is returns Event for the next parser
			Event event = jsonParser.next();

			// start of JSON object, the position of the parser is after '{'
			if(event.equals(JsonParser.Event.START_OBJECT))
			{
				empl = new Employee();
				list.add(empl);
			}
			else if (event.equals(JsonParser.Event.KEY_NAME))
			{
				String keyName =jsonParser.getString();

				switch (keyName) {
					case "firstName":
						jsonParser.next();
						empl.setEmpFirstName(jsonParser.getString());
						break;
					case "lastName":
						jsonParser.next();
						empl.setEmpLastName(jsonParser.getString());
						break;
					case "email":
						jsonParser.next();
						empl.setEmpEmail(jsonParser.getString());
						break;
					case "employeeId":
						jsonParser.next();
						empl.setEmpID( ((Integer) jsonParser.getInt()));
						break;
					case "hireDate":
						jsonParser.next();
						Date hireDate = dateFormat.parse(jsonParser.getString());
						LocalDate date = hireDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						empl.setEmpHireDate(date);
						break;
					default:
				}
			}
		}

		 if (jsonParser != null) {
             jsonParser.close();
         }

         if (inputStream != null) {
             inputStream.close();
         }
		// return employee list
		return list;

	}

}
