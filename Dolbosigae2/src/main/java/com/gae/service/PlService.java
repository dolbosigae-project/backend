package com.gae.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gae.mapper.PlMapper;
import com.gae.dto.PlDTO;
import com.gae.vo.PlPaggingVo;
import com.gae.vo.PlResponseVo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class PlService {

    private final PlMapper plMapper;

    public PlService(PlMapper plMapper) {
        this.plMapper = plMapper;
    }

    public PlResponseVo getCityList(int page, int limit) {
        int startRow = (page - 1) * limit + 1;
        int endRow = page * limit;
        List<PlDTO> list = plMapper.getCityList(startRow, endRow);
        PlPaggingVo pagination = getPagination(page, limit, null);
        return new PlResponseVo(list, pagination);
    }

    public PlResponseVo searchCity(String plText, int page, int limit) {
        int startRow = (page - 1) * limit + 1;
        int endRow = page * limit;
        List<PlDTO> list = plMapper.searchCity(plText, startRow, endRow);
        PlPaggingVo pagination = getPagination(page, limit, plText);
        return new PlResponseVo(list, pagination);
    }

    public PlDTO selectCityInfo(int plId) {
        return plMapper.selectCityInfo(plId);
    }

    public void deleteCity(int plId) {
        plMapper.deleteCity(plId);
    }

    public PlPaggingVo getPagination(int page, int limit, String plText) {
        int totalCount = plText == null ? plMapper.getTotalCount() : plMapper.getTotalCountBySearch(plText);
        System.out.println("Total Count: " + totalCount); 
        return new PlPaggingVo(totalCount, page, limit);
    }

    @Transactional
    public void cityInsert(PlDTO dto) {
        try {
            if (dto.getPlImg() != null && !dto.getPlImg().isEmpty()) {
                String base64Image = dto.getPlImg().split(",")[1];
                String mimeType = dto.getPlImg().split(",")[0].split(":")[1].split(";")[0];
                String extension;
                switch (mimeType) {
                    case "image/jpeg":
                        extension = "jpg";
                        break;
                    case "image/png":
                        extension = "png";
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported image type: " + mimeType);
                }
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                String imagePath = "C:\\Users\\user1\\git\\fileupload\\profile\\" + UUID.randomUUID().toString() + "." + extension;
                System.out.println(imagePath);
                File directory = new File("C:\\Users\\user1\\git\\fileupload\\profile");
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                try (OutputStream os = new FileOutputStream(imagePath)) {
                    os.write(imageBytes);
                }
                dto.setPlImgPath(imagePath);  // 메서드명 수정
            }
            plMapper.cityInsert(dto);
        } catch (Exception e) {
            System.out.println("놀이시설 등록 중 오류 발생 : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("놀이시설 등록 중 오류 발생");
        }
    }
}
