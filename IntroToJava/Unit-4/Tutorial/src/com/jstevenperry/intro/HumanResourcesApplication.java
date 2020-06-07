package com.jstevenperry.intro;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HumanResourcesApplication {

	private static final Logger log = Logger.getLogger(HumanResourcesApplication.class.getName());

	public interface StockOptionProcessingCallback {
		public void process(StockOptionEligible stockOptionEligible);
	}

	public static void main(String[] args) {
		Employee e = new Employee();
		e.setName("J Smith");
		e.setEmployeeNumber("0001");
		e.setTaxpayerIdNumber("123‑45‑6789");
		e.setSalary(BigDecimal.valueOf(45000.0));
		e.printAudit(log);
	}
	
	public void handleStockOptions(final Person person, StockOptionProcessingCallback callback) {
		if (person instanceof StockOptionEligible) {
			// Eligible Person, invoke the callback straight up
			callback.process((StockOptionEligible)person);
			} else if (person instanceof Employee) {
			// Not eligible, but still an Employee. Let's create an
			/// anonymous inner class implementation for this
			callback.process(new StockOptionEligible() {
				@Override
				public void processStockOptions(int number, BigDecimal price) {
				// This employee is not eligible
				log.warning("It would be nice to award " + number + " of shares at $" +
					price.setScale(2, RoundingMode.HALF_UP).toPlainString() +
					", but unfortunately, Employee " + person.getName() + 
					" is not eligible for Stock Options!");
				}
			});
		} else {
			callback.process(new StockOptionEligible() {
				@Override
				public void processStockOptions(int number, BigDecimal price) {
				log.severe("Cannot consider awarding " + number + " of shares at $" +
					price.setScale(2, RoundingMode.HALF_UP).toPlainString() +
					", because " + person.getName() + 
					" does not even work here!");
				}
			});
		}
	}

	  public static List<Person> createPeople() {
		    List<Person> ret = new ArrayList<>();
		    // First create and add some people
		    Person p = new Person("Jane Doe", 24, 150, 50, "BLUE", "FEMALE");
		    ret.add(p);
		    //
		    p = new Person("Peter Parker", 34, 175, 76, "GREEN", "MALE");
		    ret.add(p);
		    // Now create some Employees
		    ret.addAll(createEmployees());
		    // Now create some Managers
		    ret.addAll(createManagers());
		    return ret;
		  }

		  /**
		   * Canned method to create Employees that will be part of the
		   * Human Resources "database". For purely teaching purposes only.
		   * Once we create a List of Employees we can do lots of different
		   * things with it: write out to disk (then read back in), use for
		   * a search feature, etc.
		   * 
		   * @return List<Employee> - List of Employee objects
		   */
		  public static List<Employee> createEmployees() {
		    List<Employee> ret = new ArrayList<>();
		    Employee e = new Employee("Jon Smith", 45, 175, 75, "BLUE", "MALE", "123-45-9999", "0001",
		        BigDecimal.valueOf(100000.0));
		    ret.add(e);
		    //
		    e = new Employee("Jon Jones", 40, 185, 85, "BROWN", "MALE", "223-45-9999", "0002",
		        BigDecimal.valueOf(110000.0));
		    ret.add(e);
		    //
		    e = new Employee("Mary Smith", 35, 155, 55, "GREEN", "FEMALE", "323-45-9999", "0003",
		        BigDecimal.valueOf(120000.0));
		    ret.add(e);
		    //
		    e = new Employee("Chris Johnson", 38, 165, 65, "HAZEL", "PNTS", "423-45-9999", "0004",
		        BigDecimal.valueOf(90000.0));
		    ret.add(e);
		    //
		    // e = new Employee("Christine Johnson", 33, 160, 60, "BROWN", Gender.FEMALE, "424-45-9999", "0005",
		    // BigDecimal.valueOf(190000.0));
		    // ret.add(e);
		    // Return list of Employees
		    return ret;
		  }

		  public static List<Manager> createManagers() {
		    List<Manager> ret = new ArrayList<>();
		    Manager m = new Manager("Clancy Snodgrass", 45, 180, 90, "BLUE", "MALE", "456-45-9999", "0100",
		        BigDecimal.valueOf(245000.0));
		    ret.add(m);
		    //
		    m = new Manager("Mary Snodgrass", 44, 170, 55, "BLUE", "FEMALE", "567-45-9999", "0101",
		        BigDecimal.valueOf(250000.0));
		    ret.add(m);
		    return ret;
		  }

}
