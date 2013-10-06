package org.ingini.mongodb.morphia.example.model.heroes;

import com.google.code.morphia.annotations.Entity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
@Entity
public class Address {

    private String castle;
    private String continent;
    private Region region;


    protected Address() {

    }

    public Address(String castle, String continent,
                   Region region) {
        this.castle = castle;
        this.continent = continent;
        this.region = region;
    }

    public String getCastle() {
        return castle;
    }

    public String getContinent() {
        return continent;
    }

    public Region getRegion() {
        return region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return new EqualsBuilder()
                .append(this.castle, address.castle)
                .append(this.continent, address.continent)
                .append(this.region, this.region).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 43).append(castle).append(continent).append(region).toHashCode();
    }
}
