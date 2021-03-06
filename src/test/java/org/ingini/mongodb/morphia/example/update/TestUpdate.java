package org.ingini.mongodb.morphia.example.update;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.ingini.mongodb.morphia.example.domain.weapons.Weapon;
import org.ingini.mongodb.morphia.example.domain.weapons.WeaponDetails;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.MappedClass;

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
public class TestUpdate {

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
    public void shouldAddFieldToTheLightbringer() {
        //GIVEN
        String id = "Lightbringer";
        ds.save(new Weapon(id, null, null));

        WeaponDetails details = new WeaponDetails("The one who pulls out this sword from fire will be named Lord's Chosen ...", "Azor Ahai");

        //WHEN
        ds.update(ds.createQuery(Weapon.class).field("id").equal(id),
                ds.createUpdateOperations(Weapon.class).set("details", details));

        //THEN
        assertThat(ds.find(Weapon.class, "id", id).get()).isEqualTo(new Weapon(id, null, details));
    }

}
