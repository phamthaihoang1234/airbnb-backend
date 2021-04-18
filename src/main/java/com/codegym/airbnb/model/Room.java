package com.codegym.airbnb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room extends AbstractEntity implements Serializable {
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String address;
    @Column(name = "price_per_night")
    private Double pricePerNight;
    @Column(name = "total_of_bedroom")
    private Byte totalOfBedroom;
    @Column(name = "total_of_bathroom")
    private Byte totalOfBathroom;
    @Column(name = "cancelled", nullable = false)
    private Boolean cancelled = false;
    private Boolean status = true;

    @OneToMany(orphanRemoval = true, mappedBy = "room")
    private List<RoomImage> roomImages;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<Booking> bookings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", nullable = false, updatable = false)
    private Province province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_type", nullable = false, updatable = false)
    private PropertyType propertyType;

    @Transient
    private  Object avgRatting;

    public Object getAvgRatting() {
        return avgRatting;
    }

    public void setAvgRatting(Object avgRatting) {
        this.avgRatting = avgRatting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Byte getTotalOfBedroom() {
        return totalOfBedroom;
    }

    public void setTotalOfBedroom(Byte totalOfBedroom) {
        this.totalOfBedroom = totalOfBedroom;
    }

    public Byte getTotalOfBathroom() {
        return totalOfBathroom;
    }

    public void setTotalOfBathroom(Byte totalOfBathroom) {
        this.totalOfBathroom = totalOfBathroom;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public List<RoomImage> getRoomImages() {
        return roomImages;
    }

    public void setRoomImages(List<RoomImage> roomImages) {
        this.roomImages = roomImages;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }
}
