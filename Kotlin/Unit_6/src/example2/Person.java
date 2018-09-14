package example2;

import java.time.LocalDate;

public final class Person {
    private String givenName;
    private String familyName;
    private LocalDate dateOfBirth;
    public String getGivenName() {
        return this.givenName;
    }
    public String getFamilyName() {
        return this.familyName;
    }
    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }
    public Person(String givenName, String familyName, LocalDate dateOfBirth) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
    }
}