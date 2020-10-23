package tr.com.khg.criteria.domain;

import java.io.Serializable;

public class CarStatistic implements Serializable {

    private Long totalCar;
    private Long totalDistinctCar;
    private Integer maxYear;
    private Double avgYear;
    private Long sumOfYears;

    public CarStatistic(Long totalCar, Long totalDistinctCar, Integer maxYear, Double avgYear, Long sumOfYears) {
        this.totalCar = totalCar;
        this.totalDistinctCar = totalDistinctCar;
        this.maxYear = maxYear;
        this.avgYear = avgYear;
        this.sumOfYears = sumOfYears;
    }

    public Long getTotalCar() {
        return totalCar;
    }

    public void setTotalCar(Long totalCar) {
        this.totalCar = totalCar;
    }

    public Long getTotalDistinctCar() {
        return totalDistinctCar;
    }

    public void setTotalDistinctCar(Long totalDistinctCar) {
        this.totalDistinctCar = totalDistinctCar;
    }

    public Integer getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(Integer maxYear) {
        this.maxYear = maxYear;
    }

    public Double getAvgYear() {
        return avgYear;
    }

    public void setAvgYear(Double avgYear) {
        this.avgYear = avgYear;
    }

    public Long getSumOfYears() {
        return sumOfYears;
    }

    public void setSumOfYears(Long sumOfYears) {
        this.sumOfYears = sumOfYears;
    }
}
