package org.ingini.mongodb.morphia.example.domain.zip;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.utils.IndexDirection;

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
@Entity("zip_codes")
public class ZipData {

    public static final String LOCATION_COORDINATES = "loc";
    public static final String POPULATION = "pop";
    @Id
    private String id;

    private String city;

    @Indexed(IndexDirection.GEO2D)
    @Property(LOCATION_COORDINATES)
    private double[] location;

    @Property(POPULATION)
    private int population;
    private String state;

    public ZipData() {
    }

    public ZipData(String id, String city, double[] location,
                   int population, String state) {
        this.id = id;
        this.city = city;
        this.location = location;
        this.population = population;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public double[] getLocation() {
        return location;
    }

    public int getPopulation() {
        return population;
    }

    public String getState() {
        return state;
    }

}
