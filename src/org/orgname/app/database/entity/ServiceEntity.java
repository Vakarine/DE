package org.orgname.app.database.entity;

public class ServiceEntity
{
    private int id;
    private String title;
    private double cost;
    private int duration;
    private double discount;
    private String imgPath;

    public ServiceEntity(int id, String title, double cost, int duration, double discount, String imgPath) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.duration = duration;
        this.discount = discount;
        this.imgPath = imgPath;
    }

    public ServiceEntity(String title, double cost, int duration, double discount, String imgPath) {
        this(-1, title, cost, duration, discount, imgPath);
    }

    @Override
    public String toString() {
        return "ServiceEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", duration=" + duration +
                ", discount=" + discount +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public ServiceEntity setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ServiceEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public double getCost() {
        return cost;
    }

    public ServiceEntity setCost(double cost) {
        this.cost = cost;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public ServiceEntity setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public double getDiscount() {
        return discount;
    }

    public ServiceEntity setDiscount(double discount) {
        this.discount = discount;
        return this;
    }

    public String getImgPath() {
        return imgPath;
    }

    public ServiceEntity setImgPath(String imgPath) {
        this.imgPath = imgPath;
        return this;
    }
}

