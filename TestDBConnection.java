// How to create connection with Database with JDBC ?

public class TestDBConnection {

	public static void main(String[] args) {
		try {
			
			// How to add jdbc driver's jar in classpath : Rclick Project ->  Build Path -> Configure Build Path -> Libraries -> select classpath -> add external jar -> apply and close
			
			// load JDBC Driver in JVM's method area
			
			// API : Class.forName(String F.Q cls Name) throws ClassNotFoundExc
			
			// Since Java 1.6, JDBC 4.0 API, it provides a new feature to discover java.sql.Driver automatically, it means the Class.forName is no longer required.
			
			// Class.forName("com.mysql.cj.jdbc.Driver");//Optional Step
			
			System.out.println("drvr cls loaded....");
			
			String url = "jdbc:mysql://localhost:3306/dac22?useSSL=false&allowPublicKeyRetrieval=true"; 
			
			//url -> url to reach DB through the driver
			
			//protocol//[hosts][/database][?properties]
			
			//protocol – jdbc:mysql:
			//host – localhost:3306
			//database – dac22
			//properties – useSSL=false&allowPublicKeyRetrieval=true    //useSSL : provide truststore for server certificate verification
			
			java.sql.Connection cn=java.sql.DriverManager.getConnection(url, "root", "root");
			
			System.out.println("connected to db "+cn);
			
			cn.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}

	}

}
