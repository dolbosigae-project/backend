
package com.gae.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gae.dto.PlDTO;

@Mapper
public interface PlMapper {

    List<PlDTO> selectCity(@Param("startRow") int startRow, @Param("endRow") int endRow);

    int getTotalCount();
    
    List<PlDTO> searchPlaygroundDetails(@Param("plId") String plId,
            @Param("plName") String plName,
            @Param("plHour") String plHour,
            @Param("plTel") String plTel,
            @Param("plAddress") String plAddress);
    
    List<PlDTO> searchCity(@Param("plCity") String plCity);

}
