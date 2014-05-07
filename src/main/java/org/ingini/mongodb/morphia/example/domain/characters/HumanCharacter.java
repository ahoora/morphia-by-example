package org.ingini.mongodb.morphia.example.domain.characters;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.types.ObjectId;
import org.ingini.mongodb.morphia.example.domain.beasts.Beast;
import org.mongodb.morphia.annotations.*;

import java.util.Set;

import static org.ingini.mongodb.morphia.example.domain.characters.HumanCharacter.COLLECTION_NAME;

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
@Entity(COLLECTION_NAME)
@Polymorphic
public abstract class HumanCharacter {

    public static final String COLLECTION_NAME = "characters";

    @Id
    private ObjectId id;

    @Property("first_name")
    private String firstName;

    @Property("last_name")
    private String lastName;

    private Gender gender;

    @Embedded
    private Address address;

    @Embedded
    private Set<HumanCharacter> children;

    @Embedded
    private Set<Beast> beasts;

    protected HumanCharacter() {

    }

    protected HumanCharacter(String firstName, String lastName, Gender gender, Address address,
                             Set<HumanCharacter> children, Set<Beast> beasts) {
       this(null, firstName, lastName, gender, address, children, beasts);
    }

    protected HumanCharacter(ObjectId id, String firstName, String lastName, Gender gender, Address address,
                             Set<HumanCharacter> children, Set<Beast> beasts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.children = children;
        this.beasts = beasts;
    }

    public ObjectId getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public Address getAddress() {
        return address;
    }

    public Set<HumanCharacter> getChildren() {
        return children;
    }

    public Set<Beast> getBeasts() {
        return beasts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HumanCharacter humanCharacter = (HumanCharacter) o;

        return new EqualsBuilder().append(this.firstName, humanCharacter.firstName).append(this.lastName, humanCharacter.lastName)
                .append(this.gender, humanCharacter.gender).append(this.address, this.address)
                .append(this.beasts, humanCharacter.beasts).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(7, 61).append(firstName).append(lastName)
                .append(gender).append(address).append(beasts).toHashCode();
    }
}
