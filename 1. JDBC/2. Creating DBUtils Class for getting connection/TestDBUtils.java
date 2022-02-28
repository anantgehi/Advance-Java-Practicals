//Creating a DBUtils(common for all) class to get connection everytime we need it

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBUtils {

  //ensure SINGLETON DB connection instance
	
  private static Connection connection;
	
  public static Connection getConnection() throws SQLException
  {
    
    if(connection == null)
    {
      String url = "jdbc:mysql://localhost:3306/dac22?useSSL=false&allowPublicKeyRetrieval=true";
      connection=DriverManager.getConnection(url, "root", "123");
		}
    
    return connection;
	
  }
	
}

//------------------------------------------------------------------------------------------------

//Tester to test connection

import static utils.DBUtils.getConnection;
import java.sql.Connection;

public class TestDBUtils {

	public static void main(String[] args) {
    
		try(Connection cn=getConnection())
		{
      
			System.out.println("connected to db "+cn);
		
    }catch (Exception e) {
			
      e.printStackTrace();
		
    }

	}

}

