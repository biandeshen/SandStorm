package top.biandeshen.sandstorm.entity;

import javax.persistence.*;

public class City {
    @Id
    @Column(name = "city_id")
    private Integer cityId;

    @Column(name = "city_level")
    private Integer cityLevel;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "city_size")
    private Integer citySize;

    /**
     * @return city_id
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * @param cityId
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * @return city_level
     */
    public Integer getCityLevel() {
        return cityLevel;
    }

    /**
     * @param cityLevel
     */
    public void setCityLevel(Integer cityLevel) {
        this.cityLevel = cityLevel;
    }

    /**
     * @return city_name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    /**
     * @return city_size
     */
    public Integer getCitySize() {
        return citySize;
    }

    /**
     * @param citySize
     */
    public void setCitySize(Integer citySize) {
        this.citySize = citySize;
    }
}