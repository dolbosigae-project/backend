package com.gae.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gae.dto.CoDTO;
import com.gae.dto.PlDTO;
import com.gae.mapper.CoMapper;
import com.gae.vo.CoPaggingVo;
import com.gae.vo.CoResponseVo;

@Service
public class CoService {
	private CoMapper coMapper;

	public CoService(CoMapper coMapper) {
		this.coMapper = coMapper;
	}

	public CoResponseVo getConvenList(int page, int limit, boolean isDescending) {
		int startRow = (page - 1) * limit + 1; 
		int endRow = page * limit;
		List<CoDTO> list;
        if (isDescending) {
            list = coMapper.getConvenListDesc(startRow, endRow);
        } else {
            list = coMapper.getConvenList(startRow, endRow);
        }
		CoPaggingVo pagination = getPagination(page, limit, null);
		return new CoResponseVo(list, pagination);
	}


	public CoResponseVo searchConven(int page, int limit, String coText) {
		int startRow = (page - 1) * limit + 1;
		int endRow = page * limit;
		List<CoDTO> list = coMapper.searchConven(coText, startRow, endRow);
		CoPaggingVo pagination = getPagination(page, limit, coText);
		return new CoResponseVo(list, pagination);
	}
	
	public CoPaggingVo getPagination(int page, int limit, String coText) {
		int totalCount = coText == null ? coMapper.getTotalCount() : coMapper.getTotalCountBySearch(coText);
        System.out.println("Total Count: " + totalCount); 
		return new CoPaggingVo(totalCount, page, limit);
	}

	public CoDTO selectConvenInfo(int coId) {
		return coMapper.selectConvenInfo(coId);
	}

	public void deleteConvenience(int coId) {
		coMapper.deleteConvenience(coId);
	}

	public void convenInsert(CoDTO dto) {
		try {
            if (dto.getCoImg() != null && !dto.getCoImg().isEmpty()) {
                String base64Image = dto.getCoImg().split(",")[1];
                String mimeType = dto.getCoImg().split(",")[0].split(":")[1].split(";")[0];
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
                String imagePath = "C:\\Users\\user1\\git\\fileupload\\coProfile\\" + UUID.randomUUID().toString() + "." + extension;
                System.out.println(imagePath);
                File directory = new File("C:\\Users\\user1\\git\\fileupload\\coProfile");
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                try (OutputStream os = new FileOutputStream(imagePath)) {
                    os.write(imageBytes);
                }
                dto.setCoImgPath(imagePath);  // 메서드명 수정
            }
            coMapper.convenInsert(dto);
        } catch (Exception e) {
            System.out.println("편의시설 등록 중 오류 발생 : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("편의시설 등록 중 오류 발생");
        }
	}
	
}