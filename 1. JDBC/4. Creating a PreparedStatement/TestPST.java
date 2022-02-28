//Prerequisites : DBUtils, Employee Table in Database

import java.sql.*;
import java.util.Scanner;

import static utils.DBUtils.getConnection;

public class TestPST {

	public static void main(String[] args) {
		
		String sql = "select empid , name,salary, join_date from my_emp where dept=? and join_date > ? order by salary";

		try (Scanner sc = new Scanner(System.in);
				
				// get cn
				Connection cn = getConnection();
				
				// create pre-parsed n pre-compiled statement , meant for repeatative queries ,
				// supporting IN params n avoids SQL injection attack
				PreparedStatement pst = cn.prepareStatement(sql);

		) {

			while (true) {
				// UI
				System.out.println("Enter dept n join date(yr-mon-day) or enter stop to exit");
				
				String dept = sc.next();
				
				if (dept.equalsIgnoreCase("stop"))
					return;
				
				// set IN params
				pst.setString(1, dept);// dept
				pst.setDate(2, Date.valueOf(sc.next()));// java.sql.Date : public static Date valueOf(String date)
														// throws
														// IllegalArgExc
				
				// exec method : select => executeQuery
				try (ResultSet rst = pst.executeQuery()) {
					while (rst.next())
						System.out.printf("Emp Id %d Name %s Salary %.2f Join Date %s %n", 
								rst.getInt(1),
								rst.getString(2), 
								rst.getDouble(3), 
								rst.getDate(4));
				} // rst close
			}

		} // pst , cn : close
		
		catch (Exception e) {
			
			e.printStackTrace();
		
		}

	}

}

