package org.ingini.mongodb.morphia.example.read;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.ingini.mongodb.morphia.example.domain.characters.*;
import org.ingini.mongodb.morphia.example.util.CollectionManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.MappedClass;
import org.mongodb.morphia.query.Query;

import java.net.UnknownHostException;

import static org.fest.assertions.Assertions.assertThat;
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
public class TestFindOne {

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
        CollectionManager.cleanAndFill(db, "characters.json", COLLECTION_NAME);
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
    public void shouldFindSingleDocumentByObjectId() {
        //GIVEN
        Heroine aryaStark = Heroine.createHeroineWithoutChildrenAndNoBeasts("Arya", "Stark", //
                new Address("Winterfell", "Westeros", Region.THE_NORTH));
        Key<Heroine> documentKey = ds.save(aryaStark);

        //WHEN
        Query<HumanCharacter> result = ds.find(HumanCharacter.class).field("id").equal(documentKey.getId());

        //THEN
        assertThat(result.get()).isEqualTo(aryaStark);
    }

    @Test
    public void shouldFindOneEntryBasedOnGenderAndFirstName() {
        //GIVEN

        //WHEN
        Query<Heroine> result = ds.find(Heroine.class, "gender", Gender.FEMALE).field("first_name").equal("Arya");

        //THEN
        assertThat(result.get()).isNotNull();

    }

    @Test
    @Ignore //debug the runtime exception
    public void shouldFindOneArrayElement() {
        //GIVEN


        //WHEN
        Hero humanCharacter = ds.find(Hero.class).field("children")
                .hasThisElement(Heroine.createHeroineWithoutChildrenAndNoBeasts("Sansa", "Stark",
                        new Address("Winterfell", "Westeros", Region.THE_NORTH))).get();

        //THEN
        assertThat(humanCharacter).isNotNull();
        assertThat(humanCharacter.getFirstName()).isEqualTo("Eddard");

    }
}
