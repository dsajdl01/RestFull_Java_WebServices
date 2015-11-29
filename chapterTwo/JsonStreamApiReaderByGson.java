package gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.stream.JsonReader;
import model.Employee;

/**
 * JsonStreamApiReaderByGson class
 *
 * The exercise is follow from the book RESTFull Java Web Services (second edition)
 * Chapter 2 Java IPAs for JSON Processing page 57 to 59.
 *
 * @author David Sajdl
 *
 */
public class JsonStreamApiReaderByGson {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonStreamApiReaderByGson.class);

	public static void main(String[] args) {	// TODO Auto-generated method stub
		try
		{
			LOGGER.info("Class name: {}\n", JsonStreamApiReaderByGson.class.getName());
			String fileName = "/my_second_json-array.json";
			List<Employee> empList = new JsonStreamApiReaderByGson().buildEmployeeList(fileName);
			LOGGER.info("\nJava Employee Objects are successfully created.");
			System.out.println("\n");
			empList.stream()
				.forEach( e -> System.out.println(e));
		}
		catch (ParseException | IOException e)
		{
			e.printStackTrace();
		}
	}

	public List<Employee> buildEmployeeList(String fileName) throws IOException, ParseException {
		// read .json file
		InputStream inputStream = JsonStreamApiReaderByGson.class.getResourceAsStream(fileName);
		InputStreamReader streamReader = new InputStreamReader(inputStream);

		List<Employee> empList = new ArrayList<Employee>();
		JsonReader reader = new JsonReader(streamReader);
		reader.beginArray();
		while(reader.hasNext())
		{
			Employee emp = readEmployee(reader);
			empList.add(emp);
		}
		reader.endArray();
		reader.close();
		return empList;
	}

	private Employee readEmployee(JsonReader reader) throws IOException, ParseException {
		// this method create Employee Object by reading JsonReader instance
		Employee employee = new Employee();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		reader.beginObject();
		while(reader.hasNext())
		{
			String keyName = reader.nextName();
			switch (keyName)
			{
				case "employeeId":
					employee.setEmpID(reader.nextInt());
					break;
				case "firstName":
					employee.setEmpFirstName(reader.nextString());
					break;
				case "lastName":
					employee.setEmpLastName(reader.nextString());
					break;
				case "email":
					employee.setEmpEmail(reader.nextString());
					break;
				case "hireDate":
					Date hireDate = dateFormat.parse(reader.nextString());
					LocalDate date = hireDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					employee.setEmpHireDate(date);
				default:
			}
		}

		reader.endObject();
		LOGGER.info("\nEMPLOYEE: {}", employee.toString());
		return employee;
	}
}
