package kr.co.labspace.lab_space_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    //파일 크기
    private Long fileSize;

    //MIME 타입
    private String contentType;

    //각 참조 Id
    private Long referenceId; //User id, Project id

}
