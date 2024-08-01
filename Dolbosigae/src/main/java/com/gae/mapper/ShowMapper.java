package com.gae.mapper;

import com.gae.dto.ShowDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ShowMapper {
    List<ShowDTO> getShowList(@Param("startRow") int startRow, @Param("endRow") int endRow);
    int getTotalCount();
    ShowDTO selectShowInfo(@Param("showNo") int showNo);
    List<ShowDTO> searchShow(@Param("showText") String showText, @Param("startRow") int startRow, @Param("endRow") int endRow);
    int getTotalCountBySearch(@Param("showText") String showText); // 추가된 메서드
    int deleteShow(int showNo);
    int insertShow(ShowDTO showDTO);
    int writeBoard(ShowDTO showDTO);
}



