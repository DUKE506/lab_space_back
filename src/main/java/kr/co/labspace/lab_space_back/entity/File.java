package kr.co.labspace.lab_space_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //파일 카테고리
    private FileCategory fileCategory;

    //파일 저장경로
    private String path;
    //원본파일명
    private String originalName;
    //저장명칭
    private String fileName;


}
