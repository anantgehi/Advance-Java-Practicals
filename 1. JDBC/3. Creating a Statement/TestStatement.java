// Create, Excecute and Get Results from SQL Query using Statement

//Prerequisites : DBUtils, Employee table in database 

import static utils.DBUtils.getConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestStatement {

	public static void main(String[] args) {

		// Connection object creates a Statement object for sending SQL statements to
		// the database.
		// SQL statements without parameters are normally executed using Statement
		// objects.

		try (Connection cn = getConnection(); Statement st = cn.createStatement(); ResultSet rst = st.executeQuery("select * from my_emp order by salary desc")) {

			// executeQuery executes the given SQL statement, which returns a single ResultSet object.

			while (rst.next())

				System.out.printf("ID %d Name %s Address %s Salary %.1f Dept %s  Joined on %s%n", rst.getInt(1),
						rst.getString(2), rst.getString(3), rst.getDouble(4), rst.getString(5), rst.getDate(6));

		} // rst.close, st.close , cn.close
		
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
