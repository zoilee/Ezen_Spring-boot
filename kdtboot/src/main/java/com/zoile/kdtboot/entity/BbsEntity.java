package com.zoile.kdtboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import com.zoile.kdtboot.dto.BbsDto;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="bbs_bbs")
public class BbsEntity extends BbsTimeEntity{
    @Id //primary ket
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동증가
    private Long num;

    @Column(length = 255)
    private String title;

    @Column(columnDefinition = "TEXT") //db에서 text타입으로 셋팅
    private String content;

    @Column(length = 40)
    private String writer;

    @Column(length = 40)
    private String userid;

    @Column(length = 40)
    private String password;

    @Column
    private int count;

    // @Column
    // private Timestamp wdate;

    public static BbsEntity toSaveEntity(BbsDto dto){
        BbsEntity bbsEntity = new BbsEntity();
        bbsEntity.setTitle(dto.getTitle());
        bbsEntity.setContent(dto.getContents());
        bbsEntity.setWriter(dto.getWriter());
        bbsEntity.setUserid(dto.getUserid());
        bbsEntity.setPassword(dto.getPassword());
        bbsEntity.setCount(dto.getCount());

        return bbsEntity;
    }



}
