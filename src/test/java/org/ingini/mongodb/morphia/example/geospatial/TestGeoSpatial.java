package org.ingini.mongodb.morphia.example.geospatial;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.ingini.mongodb.morphia.example.domain.zip.ZipData;
import org.ingini.mongodb.morphia.example.util.CollectionManager;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.net.UnknownHostException;

import static org.fest.assertions.Assertions.assertThat;

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
public class TestGeoSpatial {

    public static final String DB_NAME = "aggregation_test_db";

    private static Mongo mongo;
    private static DB mongoDB;
    private static Datastore ds;
    private static final Morphia morphia = new Morphia();

    @BeforeClass
    public static void beforeClass() throws UnknownHostException {
        mongo = new MongoClient("127.0.0.1", 27017);
        mongoDB = mongo.getDB(DB_NAME);
        ds = morphia.createDatastore(mongo, mongoDB.getName());
        CollectionManager.cleanAndFill(mongoDB, "zips.json", "zip_codes");
        ds.ensureIndexes(ZipData.class);
    }

    /**
     * Command line import: mongoimport --drop -d aggregation_test_db -c zipcodes zips.json
     * <p>
     * <p/>
     * db.zip_codes.find({loc: {$near : {$geometry : {type: 'Point', coordinates: [-122.252696, 37.900933] }},
     * $maxDistance: 10*1000 }});
     * </p>
     */
    @Test
    @Ignore //find out why is not working
    public void shouldFindAllTownsWithinRadius10km() {
        //GIVEN
        int earthRadiusInKm = 6371;

        //WHEN
        Query<ZipData> results = ds.find(ZipData.class).field(ZipData.LOCATION_COORDINATES)
                .near(-122.252696, 37.900933, 10 / earthRadiusInKm);

        //THEN
        assertThat(results).hasSize(19);
    }

    /**
     * Command line import: mongoimport --drop -d aggregation_test_db -c zipcodes zips.json
     * <p>
     * db.zip_codes.find({loc: {$near : {$geometry : {type: 'Point', coordinates: [-122.252696, 37.900933] }},
     * $maxDistance: 10*1000 }, pop : {$gt: 10*1000}});
     * </p>
     */
    @Test
    @Ignore //find out why is not working
    public void shouldFindAllNearbyTownsWithPopulationOver20Thousands() {
        //GIVEN
        int earthRadiusInKm = 6371;
        int populationLowerLimit = 20 * 1000;

        //WHEN
        Query<ZipData> results = ds.find(ZipData.class).field(ZipData.LOCATION_COORDINATES)
                .near(-122.252696, 37.900933, 10 / earthRadiusInKm).field(ZipData.POPULATION)
                .greaterThan(populationLowerLimit);

        //THEN
        assertThat(results).hasSize(8);
    }
}
