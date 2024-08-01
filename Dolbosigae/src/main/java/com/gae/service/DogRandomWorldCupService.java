package com.gae.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gae.dto.DogRandomWorldCupDTO;
import com.gae.mapper.DogRandomWorldCupMapper;

@Service
public class DogRandomWorldCupService {
    private final DogRandomWorldCupMapper DRWCmapper;

    public DogRandomWorldCupService(DogRandomWorldCupMapper dRWCmapper) {
        DRWCmapper = dRWCmapper;
    }

    public List<DogRandomWorldCupDTO> selectRandomDog() {
        return DRWCmapper.selectRandomDog();
    }

    public DogRandomWorldCupDTO selectWinnerDog(int dogId) {
        return DRWCmapper.selectWinnerDog(dogId);
    }

    @Transactional
    public void incrementWinCount(int dogId) {
        int affectedRows = DRWCmapper.incrementWinCount(dogId);
        if (affectedRows > 0) {
            System.out.println("Win count updated successfully. Affected rows: " + affectedRows);
        } else {
            System.out.println("No rows updated. Affected rows: " + affectedRows);
        }
    }

	public List<DogRandomWorldCupDTO> selectAllRanking() {
		return DRWCmapper.selectAllRanking();
	}
}
