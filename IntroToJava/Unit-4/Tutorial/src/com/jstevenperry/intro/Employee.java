package com.jstevenperry.intro;

import java.math.BigDecimal;

public class Employee extends Person {
	
	private String taxpayerIdNumber;
	private String employeeNumber;
	private BigDecimal salary;


	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(String name, int age, int height, int weight, String eyeColor, String gender) {
		super(name, age, height, weight, eyeColor, gender);
		// TODO Auto-generated constructor stub
	}
	
	public String getTaxpayerIdNumber() {
		return taxpayerIdNumber;
	}

	public void setTaxpayerIdNumber(String taxpayerIdNumber) {
		this.taxpayerIdNumber = taxpayerIdNumber;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	
	@Override
	public void printAudit(StringBuilder buffer) {
		// TODO Auto-generated method stub
		super.printAudit(buffer);
		
		// Now format this instance's values
		buffer.append("TaxpayerIdentificationNumber=");
		buffer.append(getTaxpayerIdNumber());
		buffer.append(","); 
		buffer.append("EmployeeNumber=");
		buffer.append(getEmployeeNumber());
		buffer.append(","); 
		buffer.append("Salary=");
		buffer.append(getSalary().setScale(2).toPlainString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((employeeNumber == null) ? 0 : employeeNumber.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
		result = prime * result + ((taxpayerIdNumber == null) ? 0 : taxpayerIdNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (employeeNumber == null) {
			if (other.employeeNumber != null)
				return false;
		} else if (!employeeNumber.equals(other.employeeNumber))
			return false;
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (!salary.equals(other.salary))
			return false;
		if (taxpayerIdNumber == null) {
			if (other.taxpayerIdNumber != null)
				return false;
		} else if (!taxpayerIdNumber.equals(other.taxpayerIdNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + "Employee [taxpayerIdNumber=" + taxpayerIdNumber + ", employeeNumber=" + employeeNumber + ", salary="
				+ salary + "]";
	}

}
