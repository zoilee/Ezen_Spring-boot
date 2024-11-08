package com.zoile.kdtboot.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
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
    
    public Page<BbsDto> findAll(Pageable pageable){
        Page<BbsEntity> bbsPage = bbsRepository.findAll(pageable);


        return bbsPage.map(BbsDto::toBbsDto);
    }

    public Page<BbsDto> search(String searchKey, String searchVal, Pageable pageable){
        Page<BbsEntity> bbsPage;
        switch (searchKey) {
            case "title":
                bbsPage = bbsRepository.findByTitleContaining(searchVal, pageable);
                break;
        
            case "writer":
                bbsPage = bbsRepository.findByWriterContaining(searchVal, pageable);
                break;
            case "contents":
                bbsPage = bbsRepository.findByContentContaining(searchVal, pageable);
                break;
            default:
                bbsPage = Page.empty();
                break;
        }
        return bbsPage.map(BbsDto::toBbsDto);

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
        // Optional <BbsEntity> opBbsEntity = bbsRepository.findById(num);
        // if(opBbsEntity.isPresent()){
        //     BbsEntity bbsEntity = opBbsEntity.get();
        //     BbsDto dto = BbsDto.toBbsDto(bbsEntity);
        //     return dto;
        // }else{
        //     return null;
        // }
        return bbsRepository.findById(num)
                            .map(BbsDto::toBbsDto)
                            .orElse(null);
            
        
    }


    public void deleteById(Long num){
        bbsRepository.deleteById(num);
    }

}
