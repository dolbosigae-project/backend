package com.gae.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.gae.dto.ABDTO;

@Mapper
public interface ABMapper {
	List<ABDTO> getABList(@Param("startRow") int startRow, @Param("endRow") int endRow);
    List<ABDTO> searchAB(@Param("region") String region, 
            @Param("breed") String breed, 
            @Param("startRow") int startRow, @Param("endRow") int endRow);
    int getTotalCount();
    int getTotalCountBySearch(@Param("region") String region, @Param("breed") String breed);
    ABDTO selectABDetail(@Param("id") String id);
    void insertAB(ABDTO abDTO);
    void deleteAB(@Param("id") String id);
}
