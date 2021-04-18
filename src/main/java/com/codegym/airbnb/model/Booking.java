package com.codegym.airbnb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bookings")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking extends AbstractEntity implements Serializable {
    @Column(name = "number_of_guests")
    private Byte numberOfGuests;

    @Column(name = "number_of_children")
    private Byte numberOfChildren;

    @Column(name = "number_of_infants")
    private Byte numberOfInfants;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15)
    private BookingStatus status;

    @Transient
    private int numNight;

    @Transient
    private double price;

    // fetch = FetchType.LAZY khi select đối tượng Booking thì mặc định không query các đối tượng User liên quan.
    // CascadeType.ALL Tương ứng với tất cả các loại cascade. cascade={DETACH, MERGE, PERSIST, REFRESH, REMOVE}
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
//    @JsonIgnore
    private UserModel user;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @OneToMany(mappedBy = "booking")
    @JsonIgnore
    private List<Review> reviews;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
//    @JsonIgnore
    private Room room;

    public Byte getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Byte numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Byte getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(Byte numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public Byte getNumberOfInfants() {
        return numberOfInfants;
    }

    public void setNumberOfInfants(Byte numberOfInfants) {
        this.numberOfInfants = numberOfInfants;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getNumNight() {
        return this.endDate.getDayOfMonth() - this.startDate.getDayOfMonth();
    }

    public void setNumNight(int numNight) {
        this.numNight = numNight;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return (this.getNumNight() * this.room.getPricePerNight());
    }
}
