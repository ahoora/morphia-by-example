package org.ingini.mongodb.morphia.example.domain.characters;

import com.google.common.collect.Sets;
import org.ingini.mongodb.morphia.example.domain.beasts.Beast;

import java.util.Set;

import static org.ingini.mongodb.morphia.example.domain.characters.Gender.FEMALE;

/**
 * Copyright (c) 2013 Ivan Hristov
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Heroine extends HumanCharacter {

    protected Heroine() {
      super();
    }

    public Heroine(String firstName,
                   String lastName,
                   Address address,
                   Set<HumanCharacter> children,
                   Set<Beast> beasts) {
        super(firstName, lastName, FEMALE, address, children, beasts);
    }

    public static Heroine createHeroineWithoutChildrenAndNoBeasts(String firstName, String lastName,
                                                                  Address address) {
        return new Heroine(firstName, lastName, address, null, null);
    }

    public static Heroine addBeast(Heroine heroine, Beast beast) {
        return new Heroine(heroine.getFirstName(), heroine.getLastName(), heroine.getAddress(),
                heroine.getChildren(), Sets.newHashSet(beast));
    }

}
