package be.saouti.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import be.saouti.models.Copy;
import be.saouti.models.Loan;

public class LoanDAO implements ILoanDAO{
	protected Connection connect = null;
	
	public LoanDAO(Connection conn){
		this.connect = conn;
	}
	
	@Override
	public Loan find(int id) {
		try {
			Loan loan = null;
			PreparedStatement prepare = connect.prepareStatement(
	            "SELECT * FROM loan,copy WHERE loan_id = ? AND ongoing = 1"
	        );
			prepare.setInt(1, id);
			ResultSet result = prepare.executeQuery();
			if(result.next()){
				Copy copy = new CopyDAO(this.connect).find(result.getInt("copy_id"));
				loan = copy.getCurrentLoan();
			}
			return loan;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
