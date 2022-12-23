package com.jobcard.applicatoion.Entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String arabiceFirstName;
    private String lastName;
    private String arabicLastName;
    @Column(unique = true)
    private String cin;
    private String Profession;
    private Date birthdayDate;
    private Card type;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "image_id")
    private Image image;
}
