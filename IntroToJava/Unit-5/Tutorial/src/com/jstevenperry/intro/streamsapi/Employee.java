/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jstevenperry.intro.streamsapi;

import java.math.BigDecimal;

public class Employee extends Person {

    /**
     * 
     */
    private static final long serialVersionUID = -1003512860522752800L;

    private String taxpayerIdNumber;
    private String employeeNumber;
    private BigDecimal salary;

    public Employee() {
        super();
    }

    public Employee(String name, int age, int height, int weight, String eyeColor, Gender gender) {
        super(name, age, height, weight, eyeColor, gender);
    }

    public Employee(String name, int age, int height, int weight, String eyeColor, Gender gender,
            String taxpayerIdNumber, String employeeNumber, BigDecimal salary) {
        super(name, age, height, weight, eyeColor, gender);
        this.taxpayerIdNumber = taxpayerIdNumber;
        this.employeeNumber = employeeNumber;
        this.salary = salary;
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
        super.printAudit(buffer);

        buffer.append("TaxpayerIdentificatioNumber=");
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
        StringBuilder builder = new StringBuilder();
        builder.append("Employee [taxpayerIdNumber=").append(taxpayerIdNumber).append(", employeeNumber=")
                .append(employeeNumber).append(", salary=").append(salary).append(", getName()=").append(getName())
                .append(", getAge()=").append(getAge()).append(", getHeight()=").append(getHeight())
                .append(", getWeight()=").append(getWeight()).append(", getEyeColor()=").append(getEyeColor())
                .append(", getGender()=").append(getGender()).append("]");
        return builder.toString();
    }

}
