package com.example.api_techforb.Modules.plants.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String countryCode;
    private String name;
    private int readings;
    private int mediumAlerts;
    private int redAlerts;
    private int disabledSensors;

    @Embedded
    private Indicators indicators;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReadings() {
        return readings;
    }

    public void setReadings(int readings) {
        this.readings = readings;
    }

    public int getMediumAlerts() {
        return mediumAlerts;
    }

    public void setMediumAlerts(int mediumAlerts) {
        this.mediumAlerts = mediumAlerts;
    }

    public int getRedAlerts() {
        return redAlerts;
    }

    public void setRedAlerts(int redAlerts) {
        this.redAlerts = redAlerts;
    }

    public int getDisabledSensors() {
        return disabledSensors;
    }

    public void setDisabledSensors(int disabledSensors) {
        this.disabledSensors = disabledSensors;
    }

    public Indicators getIndicators() {
        return indicators;
    }

    public void setIndicators(Indicators indicators) {
        this.indicators = indicators;
    }
}

@Embeddable
class Indicators {
    @AttributeOverrides({
        @AttributeOverride(name = "unit1", column = @Column(name = "temperatura_unit1")),
        @AttributeOverride(name = "unit2", column = @Column(name = "temperatura_unit2")),
        @AttributeOverride(name = "unit3", column = @Column(name = "temperatura_unit3")),
    })
    private Measurement temperatura;

    @AttributeOverrides({
        @AttributeOverride(name = "unit1", column = @Column(name = "presion_unit1")),
        @AttributeOverride(name = "unit2", column = @Column(name = "presion_unit2")),
        @AttributeOverride(name = "unit3", column = @Column(name = "presion_unit3")),
    })
    private Measurement presion;

    @AttributeOverrides({
        @AttributeOverride(name = "unit1", column = @Column(name = "viento_unit1")),
        @AttributeOverride(name = "unit2", column = @Column(name = "viento_unit2")),
        @AttributeOverride(name = "unit3", column = @Column(name = "viento_unit3")),
    })
    private Measurement viento;

    @AttributeOverrides({
        @AttributeOverride(name = "unit1", column = @Column(name = "niveles_unit1")),
        @AttributeOverride(name = "unit2", column = @Column(name = "niveles_unit2")),
        @AttributeOverride(name = "unit3", column = @Column(name = "niveles_unit3")),
    })
    private Measurement niveles;

    @AttributeOverrides({
        @AttributeOverride(name = "unit1", column = @Column(name = "energia_unit1")),
        @AttributeOverride(name = "unit2", column = @Column(name = "energia_unit2")),
        @AttributeOverride(name = "unit3", column = @Column(name = "energia_unit3")),
    })
    private Measurement energia;

    @AttributeOverrides({
        @AttributeOverride(name = "unit1", column = @Column(name = "tension_unit1")),
        @AttributeOverride(name = "unit2", column = @Column(name = "tension_unit2")),
        @AttributeOverride(name = "unit3", column = @Column(name = "tension_unit3")),
    })
    private Measurement tension;

    @AttributeOverrides({
        @AttributeOverride(name = "unit1", column = @Column(name = "monoxido_carbono_unit1")),
        @AttributeOverride(name = "unit2", column = @Column(name = "monoxido_carbono_unit2")),
        @AttributeOverride(name = "unit3", column = @Column(name = "monoxido_carbono_unit3")),
    })
    private Measurement monoxidoCarbono;

    @AttributeOverrides({
        @AttributeOverride(name = "unit1", column = @Column(name = "otros_gases_unit1")),
        @AttributeOverride(name = "unit2", column = @Column(name = "otros_gases_unit2")),
        @AttributeOverride(name = "unit3", column = @Column(name = "otros_gases_unit3")),
    })
    private Measurement otrosGases;

    // Getters y Setters
    public Measurement getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Measurement temperatura) {
        this.temperatura = temperatura;
    }

    public Measurement getPresion() {
        return presion;
    }

    public void setPresion(Measurement presion) {
        this.presion = presion;
    }

    public Measurement getViento() {
        return viento;
    }

    public void setViento(Measurement viento) {
        this.viento = viento;
    }

    public Measurement getNiveles() {
        return niveles;
    }

    public void setNiveles(Measurement niveles) {
        this.niveles = niveles;
    }

    public Measurement getEnergia() {
        return energia;
    }

    public void setEnergia(Measurement energia) {
        this.energia = energia;
    }

    public Measurement getTension() {
        return tension;
    }

    public void setTension(Measurement tension) {
        this.tension = tension;
    }

    public Measurement getMonoxidoCarbono() {
        return monoxidoCarbono;
    }

    public void setMonoxidoCarbono(Measurement monoxidoCarbono) {
        this.monoxidoCarbono = monoxidoCarbono;
    }

    public Measurement getOtrosGases() {
        return otrosGases;
    }

    public void setOtrosGases(Measurement otrosGases) {
        this.otrosGases = otrosGases;
    }
}

@Embeddable
class Measurement {
    private double unit1;
    private double unit2;
    private double unit3;

    // Getters y Setters
    public double getUnit1() {
        return unit1;
    }

    public void setUnit1(double unit1) {
        this.unit1 = unit1;
    }

    public double getUnit2() {
        return unit2;
    }

    public void setUnit2(double unit2) {
        this.unit2 = unit2;
    }

    public double getUnit3() {
        return unit3;
    }

    public void setUnit3(double unit3) {
        this.unit3 = unit3;
    }
}
