package json.chapter.two;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.chrono.IsoChronology;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import model.Employee;

/**
 * ProcessObjectToJsonByJSRstreamming class
 *
 * The exercise is follow from the book RESTFull Java Web Services (second edition)
 * Chapter 2 Java IPAs for JSON Processing page 45 to 46
 *
 * @author David Sajdl
 *
 */
public class ProcessObjectToJsonByJSRstreamming {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessObjectToJsonByJSRstreamming.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			LOGGER.info("Class name: {}\n", ProcessObjectToJsonByJSRstreamming.class.getName());
			String outputFile = "my_second_json-array.json";
			new ProcessObjectToJsonByJSRstreamming().convertObjectToJsonAndCreatFile(outputFile);
			LOGGER.info("\nProcess successfully.");
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	public void convertObjectToJsonAndCreatFile(String fileName) throws IOException{

		// get list of employees
		List<Employee> list = new JsonRepresentationFromObjectModel().getListEployees();
		// adding one more employee
		list.add( new Employee(3006, "Bob", "Marley", "bm@example.com", IsoChronology.INSTANCE.date(2011,9,20)));

		LOGGER.info("Employee list: {}", list.toString());

		// create file output stream for writing data to a File
		OutputStream outputStream = new FileOutputStream(fileName);

		// generates JsonGenerator which convert data to Json
		JsonGenerator jsonGenerator = Json.createGenerator(outputStream);

		// writes the JSON 'start array' character : [
		jsonGenerator.writeStartArray();

		for(Employee e : list)
		{
			// write JSON object for each employee object
			jsonGenerator.writeStartObject()
			 .write("employeeId", e.getEmployeeId())
			 .write("firstName", e.getEmployeeFirstName())
			 .write("lastName", e.getEmployeeLastName())
			 .write("email", e.getEmployeeEmail())
			 .write("hireDate", e.getEmployeeHireDate().toString())
			 .writeEnd();
		}

		// write end of the current context array : ]
		jsonGenerator.writeEnd();

		//close stream and resources associated with it.
		jsonGenerator.close();
        outputStream.close();
	}
}
