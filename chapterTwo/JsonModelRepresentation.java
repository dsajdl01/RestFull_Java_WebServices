package json.chapter.two;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Employee;

/**
 * JsonModelRepresentation class 
 * 
 * The exercise is follow from the book RESTFull Java Web Services (second edition)
 * Chapter 2 Java IPAs for JSON Processing page 35 to 38.
 * 
 * @author David Sajdl
 *
 */

public class JsonModelRepresentation {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonModelRepresentation.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			LOGGER.info("Class name info={} \n", JsonModelRepresentation.class.getName() );
		try{
			JsonModelRepresentation jsonModelRep = new JsonModelRepresentation();
			
			// greats object model in the memory
			JsonArray jsonArray = jsonModelRep.GenereteJsonModel();
			List<Employee> emp = jsonModelRep.builtEmploeeList(jsonArray);
			// print list of employee
			emp.stream()
				.forEach( e -> System.out.println(e));
			System.out.println();
			LOGGER.info("ALL EMPLOYEES ={}\n", emp);
			
		} catch(IOException | ParseException e){
				e.printStackTrace();
		}

	}
	
	private List<Employee> builtEmploeeList(JsonArray jsonArray) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<Employee> emp = new ArrayList<Employee>();
		
		/**
		 * JSON data is holt n array of employee object. By calling readArray()
		 * method on JsonReader instance we read JSON Object data */
		for(JsonValue jsonObj : jsonArray){
			if(jsonObj.getValueType().equals(JsonValue.ValueType.OBJECT)){
				JsonObject obj = (JsonObject) jsonObj;
				Employee e = new Employee();
				e.setEmpFirstName(obj.getString("firstName"));
				e.setEmpLastName(obj.getString("lastName"));
				e.setEmpEmail(obj.getString("email"));
				e.setEmpID((Integer)obj.getInt("employeeId"));
				Date hireDate = dateFormat.parse(obj.getString("hireDate"));
				LocalDate date = hireDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				e.setEmpHireDate(date);
				emp.add(e);
			}
			
		}
		// return list of employees
		return emp;
	}

	public JsonArray GenereteJsonModel() throws IOException{
		//get input stream for reading and specify resource
		InputStream inputstream = JsonModelRepresentation.class.getResourceAsStream("/emp-array.json");
		
		// create json reader to read json data from a stream
		Reader reader = new InputStreamReader(inputstream, "UTF-8");
		JsonReader jsonReader = Json.createReader(reader);
		
		
		JsonArray jsonarray = jsonReader.readArray();
		
		if(inputstream  != null){
			inputstream.close();
		}
		if(jsonReader != null){
			jsonReader.close();
		}
		
		//return json object
		return jsonarray;
	}

}
