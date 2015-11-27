package json.chapter.two;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Employee;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.List;

/**
 * JsonRepresentationFromObjectModel class
 *
 * The exercise is follow from the book RESTFull Java Web Services (second edition)
 * Chapter 2 Java IPAs for JSON Processing page 39 to 40.
 *
 * @author David Sajdl
 *
 */
public class JsonRepresentationFromObjectModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonRepresentationFromObjectModel.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			LOGGER.info("Class name = {}\n ", JsonRepresentationFromObjectModel.class.getName());
			String outputFile = "my-employee-array.json";
			new JsonRepresentationFromObjectModel().convertObjectToJsonAndCreatFile(outputFile);
			LOGGER.info("\nFile {} is succesfully created!", outputFile);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public void convertObjectToJsonAndCreatFile(String fileName) throws IOException{

		// build json array
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		// get list of employees
		List<Employee> newEmployees = getListEployees();
		LOGGER.info("New employees:{}", newEmployees.toString());
		for(Employee e : newEmployees){
			/**
			 * add disired name-valu pair to the JSON object and push
			 * each object in the array */
			jsonArrayBuilder.add(
					Json.createObjectBuilder()
						.add("employeeId", e.getEmployeeId())
						.add("firstName", e.getEmployeeFirstName())
						.add("lastName", e.getEmployeeLastName())
						.add("email", e.getEmployeeEmail())
						.add("hierDate", e.getEmployeeHireDate().toString())
					);
		}
		// read json array holding employee details
		craeteJsonFile(jsonArrayBuilder.build(), fileName);

	}

	public void craeteJsonFile(JsonArray employeeArray, String fileName) throws IOException {

		// write array to the file
		OutputStream outputStream = new FileOutputStream(fileName);
		JsonWriter jsonWriter = Json.createWriter(outputStream);
		jsonWriter.writeArray(employeeArray);

		// close the stream and clean up the associated resources
		outputStream.close();
		jsonWriter.close();
	}

	public List<Employee> getListEployees() {
		List<Employee> empList = new ArrayList<Employee>();
		empList.add( new Employee(3001, "David", "Sajdl", "ds@example.com", IsoChronology.INSTANCE.date(2015, 11,26)));
		empList.add( new Employee(3002, "Fred", "Pitt", "fp@example.com", IsoChronology.INSTANCE.date(2013, 11,21)));
		empList.add( new Employee(3003, "Magda", "Best", "mb@example.com", IsoChronology.INSTANCE.date(2015, 01,31)));
		empList.add( new Employee(3004, "Yadira", "Deiz", "yd@example.com", IsoChronology.INSTANCE.date(2014, 10,15)));
		empList.add( new Employee(3005, "Will", "Smith", "ws@example.com", IsoChronology.INSTANCE.date(2012, 12,01)));
		return empList;
	}

}
