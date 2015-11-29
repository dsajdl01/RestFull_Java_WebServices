package gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.EmployeeForJson;


/**
 * GsonReaderFromJsonToJavaObject class
 *
 * The exercise is follow from the book RESTFull Java Web Services (second edition)
 * Chapter 2 Java IPAs for JSON Processing page 53 to 55.
 *
 * @author David Sajdl
 *
 */
public class GsonReaderFromJsonToJavaObject {

	 private static final Logger LOGGER = LoggerFactory.getLogger(GsonReaderFromJsonToJavaObject.class);

	 public static void main(String[] args) {

	        try
	        {
	        	LOGGER.info("Class name: {}", GsonReaderFromJsonToJavaObject.class.getName());
	        	String fileName = "/my_second_json-array.json";
	        	List<EmployeeForJson> empList = new GsonReaderFromJsonToJavaObject().buildEmployeeList(fileName);
	        	//    logger.log(Level.INFO,"{}" + employees);
	        	LOGGER.info("\nList of Employees: {}", empList);
	        } catch (IOException e)
	        {
	        	e.printStackTrace();
	        }
	    }

	    public List<EmployeeForJson> buildEmployeeList(String fileName) throws IOException {
	        // get GsonBuilder object
	        GsonBuilder gsonBuilder = new GsonBuilder();
	        //set data format
	        gsonBuilder.setDateFormat("yyyy-MM-dd");
	        // get gson object
	        Gson gson = gsonBuilder.create();

	        //Define the ArrayList<Employee> to store the JSON representation from input file
	        Type listType = new TypeToken<ArrayList<EmployeeForJson>>()  {}.getType();

	        // read JSON file
	        InputStream inputStream = GsonReaderFromJsonToJavaObject.class.getResourceAsStream(fileName);
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

	        //Converts JSON string to employee
	        List<EmployeeForJson> empl = gson.fromJson(reader, listType);

	        return empl;
	    }
}
