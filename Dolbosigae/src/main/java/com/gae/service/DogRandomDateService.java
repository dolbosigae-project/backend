package com.gae.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gae.dto.BoardMemberDTO;
import com.gae.mapper.DogRandomDateMapper;

@Service
public class DogRandomDateService {
    @Autowired
    private DogRandomDateMapper dogRandomDateMapper;

    public DogRandomDateService(DogRandomDateMapper dogRandomDateMapper) {
        this.dogRandomDateMapper = dogRandomDateMapper;
    }

    public List<BoardMemberDTO> searchDogMate(BoardMemberDTO dogRequest) {
        Map<String, Object> paramMap = new HashMap<>();
        String dogBirth = dogRequest.getPetBirth();
        String petSize = dogRequest.getPetSize();
        String petGender = String.valueOf(dogRequest.getPetGender());
        char petWalkProfile = 'T';

        int calculatedAge = 0;
        int thisYear = 0;

        if (!"n".equals(dogBirth)) {
            calculatedAge = calculateAge(dogBirth);
            thisYear = LocalDate.now().getYear();
        }

        paramMap.put("thisYear", thisYear);
        paramMap.put("calculatedAge", calculatedAge);
        paramMap.put("petWalkProfile", petWalkProfile);

        List<BoardMemberDTO> result = null;

        if (petSize != null && !"n".equals(petSize) && petGender != null && !"n".equals(petGender) && thisYear != 0 && calculatedAge != 0) {
            paramMap.put("petSize", petSize);
            paramMap.put("petGender", petGender);
            result = dogRandomDateMapper.searchDogMateByAllConditions(paramMap);
        } else if (petSize != null && !"n".equals(petSize) && petGender != null && !"n".equals(petGender)) {
            paramMap.put("petSize", petSize);
            paramMap.put("petGender", petGender);
            result = dogRandomDateMapper.searchDogMateByWalkProfileSizeAndGender(paramMap);
        } else if (petSize != null && !"n".equals(petSize) && thisYear != 0 && calculatedAge != 0) {
            paramMap.put("petSize", petSize);
            result = dogRandomDateMapper.searchDogMateByWalkProfileSizeAndAgeRange(paramMap);
        } else if (petGender != null && !"n".equals(petGender) && thisYear != 0 && calculatedAge != 0) {
            paramMap.put("petGender", petGender);
            result = dogRandomDateMapper.searchDogMateByWalkProfileGenderAndAgeRange(paramMap);
        } else if (petSize != null && !"n".equals(petSize)) {
            paramMap.put("petSize", petSize);
            result = dogRandomDateMapper.searchDogMateByWalkProfileAndSize(paramMap);
        } else if (petGender != null && !"n".equals(petGender)) {
            paramMap.put("petGender", petGender);
            result = dogRandomDateMapper.searchDogMateByWalkProfileAndGender(paramMap);
        } else if (thisYear != 0 && calculatedAge != 0) {
            result = dogRandomDateMapper.searchDogMateByWalkProfileAndAgeRange(paramMap);
        } else {
            result = dogRandomDateMapper.searchDogMateByWalkProfile(paramMap);
        }

        //System.out.println("paramMap : " + paramMap);
        //System.out.println("result : " + result);
        return result;
    }

    public int calculateAge(String birthDate) {
        int birthYear = 0;

        switch (birthDate) {
            case "1살이하":
                birthYear = -1;
                break;
            case "2~4살":
                birthYear = -5; // 2~4살
                break;
            case "5~7살":
                birthYear = -8; // 5~7살
                break;
            case "8~10살":
                birthYear = -11; // 8~10살
                break;
            case "11살이상":
                birthYear = -20; // 11살 이상 (최대 20살로 가정)
                break;
            default:
                birthYear = 0;
        }

        return LocalDate.now().getYear() + birthYear;
    }
}

