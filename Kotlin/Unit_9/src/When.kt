import com.makotogo.learn.kotlin.model.Person
import com.makotogo.learn.kotlin.model.Guest
import com.makotogo.learn.kotlin.model.Employee

fun formatName(person: Person) = "${person.familyName}, ${person.givenName}"

private fun purposeGrantsEntry(purpose: String) : Boolean {
    return purpose == "Maintenance"
    // Other purposes would go here
}

fun admitEntrance(person: Person) : Boolean {
    var ret: Boolean = false
    if (person is Employee) {
        ret = true
        println("Employee access granted for ${person.title}: ${formatName(person)}.")
    } else if (person is Guest) {
        if (purposeGrantsEntry(person.purpose)) {
            ret = true
            println("Guest access granted for the purpose of ${person.purpose}: ${formatName(person)}.")
        } else {
            println("Access Denied, purpose: ${person.purpose}: ${formatName(person)}.")
        }
    } else {
        println("Access Denied, ${formatName(person)}, you are but a mere Person.")
    }
    return ret
}

fun admitEntranceWithExpression(person: Person) : Boolean {
    return when(person) {
        is Employee -> {
            println("Employee access granted for ${person.title}: ${formatName(person)}.")
            true
        }
        is Guest -> {
            if (purposeGrantsEntry(person.purpose)) {
                println("Guest access granted for the purpose of ${person.purpose}: ${formatName(person)}.")
                true
            } else {
                println("Access Denied, purpose: ${person.purpose}: ${formatName(person)}.")
                false
            }
        }
        else -> {
            println("Access Denied, ${formatName(person)}, you are but a mere Person.")
            false
        }
    }
}

fun main(args: Array<String>) {
    val joeSmith = Person(givenName = "Joe", familyName = "Smith")
    val janeAnderson = Guest(purpose = "Maintenance", familyName = "Anderson", givenName = "Jane")
    val jackDavis = Guest(purpose = "Unknown", familyName = "Davis", givenName = "Jack")
    val valerieJones = Employee(title = "CEO", familyName = "Jones", givenName = "Valerie", employeeId = 1)

    admitEntrance(joeSmith)
    admitEntranceWithExpression(joeSmith)

    admitEntrance(janeAnderson)
    admitEntranceWithExpression(janeAnderson)

    admitEntrance(jackDavis)
    admitEntranceWithExpression(jackDavis)

    admitEntrance(valerieJones)
    admitEntranceWithExpression(valerieJones)
}