package be.saouti.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
	//Members
	private int id;
	private Player lender;
	private Player borrower;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean ongoing;
	private Copy copy;
	
	public Loan() {}
	public Loan(int id, Player lender, Player borrower, LocalDate startDate, LocalDate endDate,
			Copy copy) {
		super();
		this.id = id;
		this.lender = lender;
		this.borrower = borrower;
		this.startDate = startDate;
		this.endDate = endDate;
		ongoing = true;
		this.copy = copy;
	}
	
	public int getId() {return id;}
	public Player getLender() { return lender; }
	public Player getBorrower() { return borrower; }
	public LocalDate getStartDate() { return startDate; }
	public LocalDate getEndDate() { return endDate; }
	public boolean isOngoing() { return ongoing; }
	public Copy getCopy() { return copy; }
	
	public void setId(int id) { this.id = id; }
	public void setLender(Player lender) { this.lender = lender; }
	public void setBorrower(Player borrower) { this.borrower = borrower; }
	public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
	public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
	public void setOngoing( boolean ongoing ) { this.ongoing = ongoing; }
	public void setCopy(Copy copy) { this.copy = copy; }
	
	public void CalculateBalance() {
		int toCharge = 0;
		int creditLender = lender.getCredit();
		int creditBorrower = borrower.getCredit();
		int price = copy.getVideogame().getCreditCost();
		
		LocalDate today = LocalDate.now();
		
		long totalDaysToCharge = ChronoUnit.DAYS.between(startDate,today);
		toCharge += (int) Math.ceil(totalDaysToCharge / 7) * price;
		
		long daysLate =  ChronoUnit.DAYS.between(endDate,today);
		toCharge += 5 * daysLate;
		
		creditLender += toCharge;
		creditBorrower -= toCharge; 
		
		lender.setCredit(creditLender);
		borrower.setCredit(creditBorrower);
	}
	
	public void endLoan() {
		CalculateBalance();
		ongoing = false;
	}
}
