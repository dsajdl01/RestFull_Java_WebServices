package gson;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.time.chrono.IsoChronology;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import json.chapter.two.JsonRepresentationFromObjectModel;
import model.Employee;

/**
 * GsonWriterFromJavaObjectToJson class
 *
 * The exercise is follow from the book RESTFull Java Web Services (second edition)
 * Chapter 2 Java IPAs for JSON Processing page 56 to 57.
 *
 * @author David Sajdl
 *
 */
public class GsonWriterFromJavaObjectToJson {

	private static final Logger LOGGER = LoggerFactory.getLogger(GsonWriterFromJavaObjectToJson.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			LOGGER.info("Class name: {}\n", GsonWriterFromJavaObjectToJson.class.getName());
			String fileName = "my_fourth_json-emp-arrayGson.json";
			new GsonWriterFromJavaObjectToJson().buildEmployeeList(fileName);
			LOGGER.info("File is successfully written.");
		} catch( IOException e)
		{
			e.printStackTrace();
		}
	}

	public void buildEmployeeList(String fileName) throws IOException {
		// get employee list
		List<Employee> empList = new JsonRepresentationFromObjectModel().getListEployees();
		// add employee
		empList.add( new Employee(3201, "Tony", "White", "tw@example.com", IsoChronology.INSTANCE.date(2015, 12, 26)));
		LOGGER.info("\nEmployee list: {}", empList.toString());

		OutputStream outputStream = new FileOutputStream(fileName);
	        BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

		Gson gson = new Gson();
		//specify collection type that you want to convert into JSON
		Type typeOfSource = new TypeToken<List<Employee>>() {}.getType();

		//create json string from Object
		String jsonEmpStr = gson.toJson(empList, typeOfSource);

		//write to file
		bufWriter.write(jsonEmpStr);
		bufWriter.flush();
		bufWriter.close();
		outputStream.close();
	}
}
