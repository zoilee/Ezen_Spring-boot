package com.zoile.kdtboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zoile.kdtboot.dto.BbsDto;
import com.zoile.kdtboot.entity.BbsEntity;
import com.zoile.kdtboot.repository.BbsRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BbsService {
    
    private final BbsRepository bbsRepository;
    
    public List<BbsDto> findAll(Pageable pageable){
        List<BbsEntity> bbsEntities = bbsRepository.findAll(pageable).getContent();
        //entity 에서 가져온 리스트를  dto로 변환해줘야 리턴한다.
        List<BbsDto> dtoList = new ArrayList<>();
        for(BbsEntity bbsEntity : bbsEntities){
            dtoList.add(BbsDto.toBbsDto(bbsEntity));
        }

        return dtoList;
    }

    public void save(BbsDto dto){
        BbsEntity bbsEntity = BbsEntity.toSaveEntity(dto);
        bbsRepository.save(bbsEntity);
        

    }

    @Transactional
    public void updateCount(Long num){
        bbsRepository.updateCount(num);
    }

    public BbsDto findById(Long num){
        Optional <BbsEntity> opBbsEntity = bbsRepository.findById(num);
        if(opBbsEntity.isPresent()){
            BbsEntity bbsEntity = opBbsEntity.get();
            BbsDto dto = BbsDto.toBbsDto(bbsEntity);
            return dto;
        }else{
            return null;
        }

    }


    public void deleteById(Long num){
        bbsRepository.deleteById(num);
    }

}
