package org.ingini.mongodb.morphia.example.model.weapons;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Copyright (c) 2013 Ivan Hristov
 * <p/>
 * Licensed under the Apa   che License, Version 2.0 (the "License");
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
@Entity("weapons")
public class Weapon {

    @Id
    private String id;

    private String material;

    @Embedded
    private WeaponDetails details;

    protected Weapon() {

    }

    public Weapon(String id, String material, WeaponDetails details) {
        this.id = id;
        this.material = material;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public String getMaterial() {
        return material;
    }

    public WeaponDetails getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weapon)) return false;

        Weapon weapon = (Weapon) o;

        return new EqualsBuilder().append(this.id, weapon.id).append(this.material, weapon.material)
                .append(this.details, weapon.details).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1151, 71).append(id).append(material).append(details).toHashCode();
    }
}
