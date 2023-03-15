package edu.ap.spring.infraction.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Infraction {
    @Id
    public Long id;
    @Column
    public String year;
    @Column
    public String month;
    @Column
    public String date;
    @Column
    public String street;
    @Column
    public String driving_direction;
    @Column
    public String speed_limit;
    @Column
    public String passersby;
    @Column
    public String infractions_speed;
    @Column
    public String infractions_red_light;
}
