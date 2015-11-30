package gson;

import com.google.gson.stream.JsonWriter;
import model.EmployeeForJson;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JsonStreamApiWriterByGson class
 *
 * The exercise is follow from the book RESTFull Java Web Services (second edition)
 * Chapter 2 Java IPAs for JSON Processing page 59 to 60.
 *
 * @author David Sajdl
 *
 */
 public class JsonStreamApiWriterByGson {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonStreamApiWriterByGson.class);

	    public static void main(String[] args) {
	    	try
	    	{
	    		LOGGER.info("Class name: {}\n", JsonStreamApiWriterByGson.class.getName() );
	    		String nameFile = "/my_second_json-array.json";
	    		String outFile = "my_fifth_json-array_gson.json";;
	    		new JsonStreamApiWriterByGson().writeEmployeeList(nameFile, outFile);
	    		LOGGER.info("\nFile is successfully written.");
	    	}
	    	catch (IOException e)
	    	{
	    		e.printStackTrace();
	    	}
	    }

	    public void writeEmployeeList(String nameFile,String outFile) throws IOException {
	        // create output file
	    	OutputStream outputStream = new FileOutputStream(outFile);
	        BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

	        //create JsonWriter instance
	        JsonWriter writer = new JsonWriter(bufWriter);

	        // get List of Employees
	        List<EmployeeForJson> empList = new GsonReaderFromJsonToJavaObject().buildEmployeeList(nameFile);
	        // add one employee
	        empList.add( new EmployeeForJson("Dido", "Angel", "da@example.com",  3301, Date.valueOf("2015-04-11")));
	        LOGGER.info("\nEmployee List: {}", empList.toString());

	        // starting writing JSON content by start Array
	        writer.beginArray();
	        for (EmployeeForJson emp : empList)
	        {
	        	writer.beginObject();
		        writer.name("employeeId").value(emp.getEmployeeId());
		        writer.name("firstName").value(emp.getFirstName());
		        writer.name("lastName").value(emp.getLastName());
		        writer.name("email").value(emp.getEmail());
		        writer.name("hireDate").value(emp.getHireDate().toString());
		        writer.endObject();
	        }
	        // finish encoding of the Array
	        writer.endArray();
	        writer.flush();

	        //close writer
	        writer.close();
	    }
 }
