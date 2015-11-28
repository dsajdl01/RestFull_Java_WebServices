package jackson.chapter.two;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.chrono.IsoChronology;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import json.chapter.two.JsonRepresentationFromObjectModel;
import model.Employee;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;

/**
 * JacksonWriteJsonUsingGenerator class
 *
 * The exercise is follow from the book RESTFull Java Web Services (second edition)
 * Chapter 2 Java IPAs for JSON Processing page 52 to 53.
 *
 * @author David Sajdl
 *
 */
public class JacksonWriteJsonUsingGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(JacksonWriteJsonUsingGenerator.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			LOGGER.info("Class name: {}", JacksonWriteJsonUsingGenerator.class.getName());
			String outpotStream = "my_third_emp-json-array.json";
			new JacksonWriteJsonUsingGenerator().writeJsonEmloyeeList(outpotStream);
			LOGGER.info("JSON file is successfully created.");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void writeJsonEmloyeeList(String fileName) throws IOException {

		// to write file by using JsonGenerator
		OutputStream outputStream = new FileOutputStream(fileName);
		JsonGenerator jsongen = new JsonFactory().createGenerator(outputStream, JsonEncoding.UTF8);

		jsongen.writeStartArray();
		//get list Of Employee
		List<Employee> empList = new JsonRepresentationFromObjectModel().getListEployees();
		// adding extra employee
		empList.add( new Employee(3100, "Andy", "Grey", "ag@example.com", IsoChronology.INSTANCE.date(2009, 6,11)));
		LOGGER.info("\nEmployee list: {}", empList.toString());
		for(Employee e : empList)
		{
			jsongen.writeStartObject();
			jsongen.writeNumberField("employee", e.getEmployeeId());
			jsongen.writeStringField("firstName", e.getEmployeeFirstName());
			jsongen.writeStringField("lastName", e.getEmployeeLastName());
			jsongen.writeStringField("email", e.getEmployeeEmail());
			jsongen.writeStringField("hireDate", e.getEmployeeHireDate().toString());
			jsongen.writeEndObject();
		}
		jsongen.writeEndArray();

		// close
		jsongen.close();
		outputStream.close();
	}
}
