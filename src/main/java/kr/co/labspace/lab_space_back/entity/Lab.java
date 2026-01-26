package kr.co.labspace.lab_space_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "labs")
@Getter
@Setter
public class Lab {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //연구실명
    private String name;

    //교수

}
