//import org.springframework.web.client.*;
import org.json.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class main {

	public static void main(String[] args) throws IOException, JSONException{

		data dat = new data();
		List<dataArray> data = new ArrayList<dataArray>();
		try {
			//read data on database
			data = dat.readDataBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Send data to api for obtain a result
		for(int i =0; i < data.size(); i++){
			JSONObject json = readJsonFromUrl("http://api.outofcopyright.eu/Belgium/"+data.get(i).typeOfWork,data.get(i).creationDate,data.get(i).communicationDate,data.get(i).publicationDate);
			System.out.println("\nResult -----------------------------");
	    	System.out.println(json.get("result"));
		}
	    
	}
	
	private static String readAll(Reader rd) throws IOException {
	    	StringBuilder sb = new StringBuilder();
	    	int cp;
	    	while ((cp = rd.read()) != -1) {
	    		sb.append((char) cp);
	    	}
	    	return sb.toString();
	  }
	  //Send data to API and display result.
	  public static JSONObject readJsonFromUrl(String url, String creationDate, String communicationDate, String publicationDate) throws IOException, JSONException {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			//add request header
			con.setRequestMethod("POST");
	 
			String urlParameters = "PUBLICATION_DATE="+publicationDate+"&CREATION_DATE="+creationDate+"&COMMUNICATION_DATE="+communicationDate;
	 
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
	 
			int responseCode = con.getResponseCode();
			System.out.println("\nData send  -----------------------------");
			System.out.println("Sending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);
			System.out.println("End data send -----------------------------");
	    
	    try {
	    	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    	String jsonText = readAll(in);
	    	JSONObject json = new JSONObject(jsonText);
	    	return json;
	    } finally {
	    	wr.close();
	    }
	  }


}
