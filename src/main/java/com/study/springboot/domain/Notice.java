package com.study.springboot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "noticeNo")
@ToString
@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeNo;

    @Column(length = 200, nullable = false)
    private String title;

    @Lob
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    private LocalDateTime regDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private LocalDateTime updDate;
}
