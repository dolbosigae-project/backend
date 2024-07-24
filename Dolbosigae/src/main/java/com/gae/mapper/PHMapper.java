package com.gae.mapper;

import com.gae.dto.PHDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PHMapper {
    List<PHDTO> getPhList(@Param("startRow") int startRow, @Param("endRow") int endRow);
    int getTotalCount();
    PHDTO selectPhInfo(@Param("phId") int phId);
    List<PHDTO> searchPh(@Param("phText") String phText, @Param("startRow") int startRow, @Param("endRow") int endRow);
    int getTotalCountBySearch(@Param("phText") String phText);
    int deletePh(int phId);
    int insertPh(PHDTO phDTO);
}
