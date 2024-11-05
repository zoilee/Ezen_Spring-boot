package com.zoile.kdtboot.dto;

import lombok.Data;
import com.zoile.kdtboot.entity.BbsEntity;

import java.time.LocalDateTime;

@Data
public class BbsDto {
   private long num;
   private String title;
   private String contents;
   private String writer;
   private String userid;
   private String password;
   private int count;
   private LocalDateTime wdate;

   public static BbsDto toBbsDto(BbsEntity bbsEntity){
      BbsDto dto = new BbsDto();
      dto.setNum(bbsEntity.getNum());
      dto.setTitle(bbsEntity.getTitle());
      dto.setContents(bbsEntity.getContent());
      dto.setWriter(bbsEntity.getWriter());
      dto.setUserid(bbsEntity.getUserid());
      dto.setPassword(bbsEntity.getPassword());
      dto.setCount(bbsEntity.getCount());
      dto.setWdate(bbsEntity.getWdate());

      return dto;
   }

}
