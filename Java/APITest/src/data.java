import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class data {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public List<dataArray> readDataBase() throws Exception {
		try {
		      // this will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // setup the connection with the DB.
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/test?"
		              + "user=root&password=");

		      // statements allow to issue SQL queries to the database
		      statement = connect.createStatement();
		      // resultSet gets the result of the SQL query
		      resultSet = statement
		          .executeQuery("select * from data");
		      List<dataArray> data = writeResultSet(resultSet);
		      return data;
		} catch (Exception e) {
	      throw e;
	    }
	
	}
	
	private List<dataArray> writeResultSet(ResultSet resultSet) throws SQLException {
		List<dataArray> data = new ArrayList<dataArray>();
		System.out.println("Database data -----------------------------");
	    while (resultSet.next()) {
	    	String typeofwork = resultSet.getString("typeofwork");
		  	String creation_date = resultSet.getString("creation_date");
		  	String communication_date = resultSet.getString("communication_date");
		  	String publication_date = resultSet.getString("publication_date");
		  	System.out.println("typeofwork: " + typeofwork);
		  	System.out.println("creation_date: " + creation_date);
		  	System.out.println("communication_date: " + communication_date);
		  	System.out.println("publication_date: " + publication_date);
		  	dataArray da = new dataArray();
		  	da.typeOfWork = typeofwork;
		  	da.creationDate = creation_date;
		  	da.communicationDate = communication_date;
		  	da.publicationDate = publication_date;
		  	data.add(da);
	    }
	    System.out.println("End data -----------------------------");
	    return data;
	    
	  }
}
