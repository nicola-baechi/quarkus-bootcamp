package ch.open.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;

@Entity
public class Fact extends PanacheEntity {

    public LocalDateTime timestamp;
    public String statement;


}
