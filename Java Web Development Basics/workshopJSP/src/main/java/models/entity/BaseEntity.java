package models.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public class BaseEntity {

    private Integer id;

    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public int hashCode() {
        return getId();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BaseEntity)) return false;

        return equals((BaseEntity)obj);
    }

    public boolean equals(BaseEntity obj){
        return getId() == obj.getId();
    }
}
