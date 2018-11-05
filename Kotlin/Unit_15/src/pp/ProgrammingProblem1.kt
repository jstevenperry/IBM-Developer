/*
 *    Copyright 2018 Makoto Consulting Group Inc
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package pp

import com.makotogo.learn.kotlin.model.Configurable
import com.makotogo.learn.kotlin.model.Human
import com.makotogo.learn.kotlin.model.Identifiable
import com.makotogo.learn.kotlin.model.Marked
import com.makotogo.learn.kotlin.model.Nameable
import com.makotogo.learn.kotlin.util.createPerson
import com.makotogo.learn.kotlin.util.createWorker

/**
 * Process the specified object:
 *
 * Compute the interface(s) it supports, and return a List of those
 * interfaces
 */
fun process(objectToProcess: Any?): List<String> {
    val ret = ArrayList<String>()
    //
    // Marked?
    if ((objectToProcess as? Marked) != null) {
        ret.add("Marked")
    }
    //
    // Configurable?
    if ((objectToProcess as? Configurable) != null) {
        ret.add("Configurable")
    }
    //
    // Identifiable?
    if ((objectToProcess as? Identifiable) != null) {
        ret.add("Identifiable")
    }
    //
    // Nameable?
    if ((objectToProcess as? Nameable) != null) {
        ret.add("Nameable")
    }
    //
    // Human?
    if ((objectToProcess as? Human) != null) {
        ret.add("Human")
    }
    return ret
}

/**
 * The ubiquitous main() function. Even in the solutions, there you are!
 */
fun main(args: Array<String>) {
    //
    // Create Person object
    val person = createPerson()
    //
    // Process the object into a list of its interfaces
    var interfacesImplemented = process(person as Any?)
    //
    // Print out the supported interfaces
    println("Person: interfaces implemented by this object: $interfacesImplemented")
    //
    // Create Worker object
    val worker = createWorker()
    //
    // Process the object into a list of its interfaces
    interfacesImplemented = process(worker as Any?)
    //
    // Print out the supported interfaces
    println("Worker: interfaces implemented by this object: $interfacesImplemented")
}
