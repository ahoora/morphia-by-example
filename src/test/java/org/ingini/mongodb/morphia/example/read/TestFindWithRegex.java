package org.ingini.mongodb.morphia.example.read;

import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.ingini.mongodb.morphia.example.domain.weapons.Weapon;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.MappedClass;

import java.net.UnknownHostException;
import java.util.List;
import java.util.regex.Pattern;

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
public class TestFindWithRegex {

    public static final String DB_NAME = "db_for_morphia";
    private static Mongo mongo;
    private static DB db;
    private static Datastore ds;
    private static final Morphia morphia = new Morphia();

    @BeforeClass
    public static void beforeClass() throws UnknownHostException {
        mongo = new MongoClient("127.0.0.1", 27017);
        db = mongo.getDB(DB_NAME);
        ds = morphia.createDatastore(mongo, db.getName());
    }

    private static void cleanup() {
        for (final MappedClass mc : morphia.getMapper().getMappedClasses()) {
            db.getCollection(mc.getCollectionName()).drop();
        }

    }

    @AfterClass
    public static void afterClass() {
        cleanup();
        mongo.close();
    }

    @Test
    public void shouldFindWithRegexOperator() {
        //GIVEN
        ds.save(new Weapon("Lightbringer", null, null));

        ds.save(new Weapon("Longclaw", "Valyrian steel", null));
        ds.save(new Weapon("Dark Sister", "Valyrian steel", null));
        ds.save(new Weapon("Ice", "Valyrian steel", null));

        //WHEN
        List<Weapon> result = Lists.newArrayList(ds.createQuery(Weapon.class)
                .filter("material", Pattern.compile("steel.*")));

        //THEN
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(3);
    }
}
