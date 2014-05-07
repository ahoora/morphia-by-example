package org.ingini.mongodb.morphia.example.delete;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.ingini.mongodb.morphia.example.domain.characters.Address;
import org.ingini.mongodb.morphia.example.domain.characters.Heroine;
import org.ingini.mongodb.morphia.example.domain.characters.HumanCharacter;
import org.ingini.mongodb.morphia.example.domain.characters.Region;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;

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
public class TestDelete {

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

    @AfterClass
    public static void afterClass() {
        mongo.close();
    }

    @Test
    public void shouldDeleteDocumentByObjectId() {
        //GIVEN
        Heroine aryaStark = Heroine.createHeroineWithoutChildrenAndNoBeasts("Arya", "Stark", //
                new Address("Winterfell", "Westeros", Region.THE_NORTH));

        Key<Heroine> documentKey = ds.save(aryaStark);

        //WHEN
        ds.delete(HumanCharacter.class, documentKey.getId());

        //THEN
        assertThat(ds.find(HumanCharacter.class, "id", documentKey.getId()).get()).isNull();
    }
}
