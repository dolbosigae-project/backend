package com.gae.service;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gae.dto.BoardMemberDTO;
import com.gae.mapper.DogRandomDateMapper;
import com.gae.mapper.MemberMapper;

@Service
public class DogRandomDateService {
	@Autowired
	private DogRandomDateMapper DogRandomDateMapper;
    public DogRandomDateService(DogRandomDateMapper DogRandomDateMapper) {
        this.DogRandomDateMapper = DogRandomDateMapper;
    }

	public void searchDogMate(BoardMemberDTO dogRequest) {
		//System.out.println(dogRequest);
		String dogAge = dogRequest.getPetBirth();
		int calculatedAge = dogAge(dogAge);
		dogRequest.setPetBirth(String.valueOf(calculatedAge)); // 계산된 나이 설정
		System.out.println(dogRequest);
		DogRandomDateMapper.searchDogMate(dogRequest);
	}
	
	//강아지 나이 계산하여 매칭하기
	public int dogAge(String dogAge) {
        int thisYear = LocalDate.now().getYear();
        Random random = new Random();
        
        switch(dogAge) {
            case "1살이하":
                return thisYear - 1;
            case "2~4살":
                return thisYear - (2 + random.nextInt(3)); // 2~4살의 랜덤 값
            case "5~7살":
                return thisYear - (5 + random.nextInt(3)); // 5~7살의 랜덤 값
            case "8~10살":
                return thisYear - (8 + random.nextInt(3)); // 8~10살의 랜덤 값
            case "11살이상":
                return thisYear - (11 + random.nextInt(10)); // 11살 이상의 랜덤 값 (최대 20살로 가정)
            default:
                return 0;
        }
	}

}
