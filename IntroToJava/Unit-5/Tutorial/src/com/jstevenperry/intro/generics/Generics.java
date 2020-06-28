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
package com.jstevenperry.intro.generics;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Generics {

    private static final Logger log = Logger.getLogger(Generics.class.getName());

    public void listing3() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("A String");
        arrayList.add(Integer.valueOf(10));
        arrayList.add("Another String");
        // So far, so good.
        log.info("Added " + arrayList.size() + " objects to arrayList");
    }

    public void listing4() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("A String");
        arrayList.add(Integer.valueOf(10));
        arrayList.add("Another String");
        log.info("Added " + arrayList.size() + " objects to arrayList");
        // So far, so good.
        for (int aa = 0; aa < arrayList.size(); aa++) {
            String s = (String) arrayList.get(aa);
            log.info("String from ArrayList" + s);
        }
    }

    public void listing5() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("A String");
        // TODO: Uncomment next line to see compile error
        // arrayList.add(Integer.valueOf(10));// compiler error!
        arrayList.add("Another String");
        log.info("Added " + arrayList.size() + " objects to arrayList");
        // So far, so good.
        // Process the ArrayList
        for (int aa = 0; aa < arrayList.size(); aa++) {
            String s = arrayList.get(aa); // No cast necessary
            log.info("String from ArrayList" + s);
        }
    }

    public void iteratingWithGenerics() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("A String");
        // TODO: Uncomment next line to see compile error
        // arrayList.add(Integer.valueOf(10));// compiler error!
        arrayList.add("Another String");
        log.info("Added " + arrayList.size() + " objects to arrayList");
        // So far, so good.
        // Process the ArrayList
        processArrayList(arrayList);
    }

    private void processArrayList(ArrayList<String> theList) {
        for (String s : theList) {
            log.info("String from ArrayList: " + s);
        }
    }

    public <E> String formatArray(E[] arrayToFormat) {
        StringBuilder sb = new StringBuilder();

        int index = 0;
        for (E element : arrayToFormat) {
            if (index > 0) sb.append(", ");
            sb.append("Element ");
            sb.append(index++);
            sb.append(" => ");
            sb.append(element);
        }

        return sb.toString();
    }

}