package com.waiter.server.services.location.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import javax.persistence.*;
import java.util.Arrays;

/**
 * @author shahenpoghosyan
 */
@Entity
@Table(name = "city")
public class City extends AbstractEntityModel {

    @Column(name = "name")
    private String name;

    @Column(name = "population")
    private int population;

    @Column(name = "coordinates")
    private double[] coordinates;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (population != city.population) return false;
        if (name != null ? !name.equals(city.name) : city.name != null) return false;
        if (!Arrays.equals(coordinates, city.coordinates)) return false;
        return !(country != null ? !country.equals(city.country) : city.country != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + population;
        result = 31 * result + (coordinates != null ? Arrays.hashCode(coordinates) : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", population=" + population +
                ", coordinates=" + Arrays.toString(coordinates) +
                ", country=" + country +
                '}';
    }
}
