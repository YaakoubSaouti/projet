package be.saouti.daos;

import be.saouti.models.Loan;

public interface ILoanDAO {
	Loan find(int id);
}
