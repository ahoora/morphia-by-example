package org.ingini.mongodb.morphia.example.model.heroes;

import com.google.code.morphia.annotations.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.types.ObjectId;
import org.ingini.mongodb.morphia.example.model.beasts.Beast;

import java.util.Set;

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
@Entity("heroes")
@Polymorphic
public abstract class Human {

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
    private Set<Human> children;

    @Embedded
    private Set<Beast> beasts;

    protected Human() {

    }

    protected Human(String firstName, String lastName, Gender gender, Address address,
                    Set<Human> children, Set<Beast> beasts) {
       this(null, firstName, lastName, gender, address, children, beasts);
    }

    protected Human(ObjectId id, String firstName, String lastName, Gender gender, Address address,
                    Set<Human> children, Set<Beast> beasts) {
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

    public Set<Human> getChildren() {
        return children;
    }

    public Set<Beast> getBeasts() {
        return beasts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Human human = (Human) o;

        return new EqualsBuilder().append(this.firstName, human.firstName).append(this.lastName, human.lastName)
                .append(this.gender, human.gender).append(this.address, this.address)
                .append(this.beasts, human.beasts).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(7, 61).append(firstName).append(lastName)
                .append(gender).append(address).append(beasts).toHashCode();
    }
}
