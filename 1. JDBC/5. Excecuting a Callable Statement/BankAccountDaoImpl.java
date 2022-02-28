//Callable Statement I/F is used for calling database stored procedures/functions

//---------------------------------------------------------------------------------------------------
// Interface
//import java.sql.SQLException;

//public interface IBankAccountDao {
//add a method for funds transfer
//	String transferFunds(int srcId, int destId, double amount) throws SQLException;
//}
//---------------------------------------------------------------------------------------------------

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import static utils.DBUtils.getConnection;

class BankAccountDaoImpl implements IBankAccountDao {
	
  //state
	private Connection cn;
	
  //CST : for exec of stored proc.
	private CallableStatement cst1;
	private CallableStatement cst2;
	
	//ctor
	public BankAccountDaoImpl() throws SQLException{
		
    // get cn from DBUtils
		cn=getConnection();
		
    //Stored Procecdure Call
		//cst : sql : proc : "{call procName(?,?,?,?....)}"
		
    cst1=cn.prepareCall("{call transfer_funds(?,?,?,?,?)}");
		
    //can i set IN params in the ctor? NO
		//can i register OUT params (=specify JDBC data type of the OUT params) in the ctor ? YES
		cst1.registerOutParameter(4, Types.DOUBLE);
		cst1.registerOutParameter(5, Types.DOUBLE);
		
    //Stored Function Call
		cst2=cn.prepareCall("{?=call update_account_fn(?,?,?)}");
		cst2.registerOutParameter(1, Types.DOUBLE);
		
    System.out.println("acct dao created....");
		
	}
	
//	@Override
//	public String transferFunds(int srcId, int destId, double amount) throws SQLException {
//		// set IN params
//		cst1.setInt(1,srcId);
//		cst1.setInt(2, destId);
//		cst1.setDouble(3, amount);
//		//exec the proc : execute()
//		cst1.execute();
//		//how to get results from OUT param ? getter
//		return "Updated src balance "+cst1.getDouble(4)+" n dest balance"+cst1.getDouble(5);
//	}
	
	@Override
	public String transferFunds(int srcId, int destId, double amount) throws SQLException {
		
    // set IN params
		cst2.setInt(2,srcId);
		cst2.setInt(3, destId);
		cst2.setDouble(4, amount);
		
    //exec the proc : execute()
		cst2.execute();
		
    //how to get results from OUT param ? getter
		return "Updated src balance "+cst2.getDouble(1);
	}
	
	public void cleanUp() throws SQLException
	{
		if( cst1 != null)
			cst1.close();
		if( cn != null)
			cn.close();
		
	}

}

//--------------------------------------------------------------------------------------------------
//Tester

public class TestStoredProcExecution {

	public static void main(String[] args) {
		try(Scanner sc=new Scanner(System.in))
		{
			//create acct dao instance
			BankAccountDaoImpl dao=new BankAccountDaoImpl();
			System.out.println("Enter src n dest acct ids n amount");
			System.out.println(dao.transferFunds(sc.nextInt(), sc.nextInt(),sc.nextDouble()));
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
//-----------------------------------------------------------------------------------------------------
//stored procedure in database

/*
drop procedure transfer_funds;
DELIMITER $$
create
 procedure transfer_funds(
in sid int ,
in did int,
in amt double ,
out sbal double,
out dbal double
)
begin
update accounts set bal=bal-amt where id=sid;
update accounts set bal=bal+amt where id=did;
select bal into sbal from accounts where id=sid;
select bal into dbal from accounts where id=did;
end$$
DELIMITER ;
*/

