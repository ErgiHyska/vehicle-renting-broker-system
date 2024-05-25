//package com.vehicool.vehicool.persistence.entity;
//
//import com.vehicool.vehicool.security.user.User;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//
//@Entity
//@Getter
//@Setter
//@Slf4j
//@Table(name = "notification")
//public class Notification {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User corresponingUser;
//
//    @Column(name = "message")
//    private String message;
//}
