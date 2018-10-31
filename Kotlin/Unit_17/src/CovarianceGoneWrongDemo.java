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

import com.makotogo.learn.kotlin.model.Person;
import com.makotogo.learn.kotlin.model.Worker;

import static com.makotogo.learn.kotlin.util.ObjectGeneratorKt.createEmployee;
import static com.makotogo.learn.kotlin.util.ObjectGeneratorKt.createGuest;
import static com.makotogo.learn.kotlin.util.ObjectGeneratorKt.createWorker;
import static com.makotogo.learn.kotlin.util.ObjectGeneratorKt.generateRandomInt;

/**
 * Static methods to demonstrate how Java array covariance can go
 * horribly wrong if you don't know what you're doing.
 */
public class CovarianceGoneWrongDemo {

    /**
     * Create a Worker array and fill it with Worker
     * objects (or a subclass thereof)
     *
     * @return - Worker[]
     */
    private static Worker[] createWorkerArray() {
        return new Worker[]{
            null,
            createWorker(),
            null,
            createEmployee(),
            null,
            createEmployee(),
            null,
            null,
            createWorker(),
            null,
            createWorker()
        };
    }

    /**
     * Add a Guest everywhere there is a null in the source
     * Person array. Process the source array in place.
     * <p>
     * Think this can't happen? Think again.
     *
     * @param source The source array
     */
    private static void eliminateNulls(Person[] source) {
        //
        // Loop through the array
        for (int index = 0; index < source.length; index++) {
            Person person = source[index];
            if (person == null) {
                //
                // And here's where things start to go horribly wrong
                source[index] = createRandomWorker();
            }
        }
    }

    /**
     * Mystery method - return a Worker, supposedly.
     * <p>
     * Sometimes, just like life, it doesn't quite work out.
     * <p>
     * Unlike life, it goes horribly wrong in a predictable way,
     * roughly ten percent of the time in this case.
     *
     * @return Person - a random Person object
     */
    private static Person createRandomWorker() {
        //
        // Generate a pseudo-random int
        int randomInt = generateRandomInt(100);
        //
        // If it's between 40 and 49, return a Guest object.
        // Bad method! Bad method!
        if (randomInt >= 40 && randomInt < 50) {
            return createGuest();
        }
        //
        // Well-behaved... return a Worker as expected
        return createWorker();
    }

    /**
     * A more realistic demo of this particular problem
     */
    private static void realisticDemo() {
        //
        // Use method to create worker array
        Worker[] workers = createWorkerArray();
        //
        // Eliminate nulls by replacing with (supposedly)
        // valid Worker objects
        eliminateNulls(workers);
        //
        // Now print out the Worker objects.
        // What could possibly go wrong? There's no
        // compile error.
        for (Worker worker : workers) {
            System.out.println("Worker: " + worker);
        }
    }

    /**
     * The ubiquitous main method. We meet again.
     *
     * @param args - Not used
     */
    public static void main(String[] args) {
        //
        // Run the demo
        demo();
        //
        // Run the realistic demo
        // realisticDemo();
    }

    /**
     * This is for the source that is shown in the unit.
     * In this short method, the problem is so obvious, nobody would
     * ever let this happen.
     * <p>
     * Unfortunately, the way this kind of thing happens for real
     * is when methods are spread out all over the place, making the
     * problem harder to see. For that, see the realisticDemo()
     * method.
     */
    private static void demo() {
        Worker[] workers = new Worker[]{
            createWorker(), createEmployee(), createEmployee(),
            null, createWorker(), null, createWorker()
        };
        Person[] people = workers;
        for (int index = 0; index < people.length; index++) {
            Person person = people[index];
            if (person == null) {
                //
                // And here's where things start to go horribly wrong
                // This will behave most of the time (like realistic buggy code does)
                people[index] = (generateRandomInt(100) > 90) ? createGuest() : createWorker();
            }
        }
        System.out.println("Processing complete. All is well, and your code is perfect!");
    }

}
