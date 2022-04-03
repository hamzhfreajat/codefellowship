package com.example.codefellowship.domain;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Posts {
    @Setter(value = AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String textContent ;
    @CreationTimestamp
    private Timestamp regdate;

    @ManyToOne
    ApplicationUser user;

}