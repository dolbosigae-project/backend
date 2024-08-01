package com.gae.service;

import java.util.List;
import java.util.Base64; // Ensure this import is present
import org.springframework.stereotype.Service;
import com.gae.dto.ShowDTO;
import com.gae.mapper.ShowMapper;
import com.gae.vo.PaggingVO;
import com.gae.vo.ShowResponseVo;
import com.gae.service.S3ImageService; // Import the S3ImageService

@Service
public class ShowService {
    private final ShowMapper mapper;
    private final S3ImageService s3ImageService; // Inject S3ImageService

    public ShowService(ShowMapper mapper, S3ImageService s3ImageService) {
        this.mapper = mapper;
        this.s3ImageService = s3ImageService;
    }

    // Show 목록을 가져오는 메서드
    public ShowResponseVo getShowList(int page, int limit) {
        int startRow = (page - 1) * limit + 1;  // startRow 수정: 1부터 시작
        int endRow = page * limit;
        List<ShowDTO> list = mapper.getShowList(startRow, endRow);
        PaggingVO pagination = getPagination(page, limit, null);
        return new ShowResponseVo(list, pagination);
    }

    // Show 검색 결과를 가져오는 메서드
    public ShowResponseVo searchShow(String showText, int page, int limit) {
        int startRow = (page - 1) * limit + 1;  // startRow 수정: 1부터 시작
        int endRow = page * limit;
        List<ShowDTO> list = mapper.searchShow(showText, startRow, endRow);
        PaggingVO pagination = getPagination(page, limit, showText);
        return new ShowResponseVo(list, pagination);
    }

    // Show 상세 정보를 가져오는 메서드
    public ShowDTO selectShowInfo(int showNo) {
        return mapper.selectShowInfo(showNo);
    }

    // Show 정보를 삭제하는 메서드
    public void deleteShow(int showNo) {
        mapper.deleteShow(showNo);
    }

    // 페이지네이션 정보를 가져오는 메서드
    public PaggingVO getPagination(int page, int limit, String showText) {
        int totalCount = showText == null ? mapper.getTotalCount() : mapper.getTotalCountBySearch(showText);
        return new PaggingVO(totalCount, page, limit);
    }

    // Show 정보를 삽입하는 메서드
    public void insertShow(ShowDTO showDTO) {
        // 프로필 이미지가 있는 경우 처리
        if (showDTO.getShowImage() != null && !showDTO.getShowImage().isEmpty()) {
            String base64Image = showDTO.getShowImage().split(",")[1]; // "data:image/png;base64," 부분 제거
            String mimeType = showDTO.getShowImage().split(",")[0].split(":")[1].split(";")[0]; // MIME 타입 추출
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

            // S3에 이미지 업로드
            String s3ImagePath = s3ImageService.uploadImageBytes(imageBytes, mimeType, extension);
            showDTO.setShowImagePath(s3ImagePath); // 이미지 경로 설정
        }

        // SHOW_NO는 자동으로 생성되므로, DTO에서 설정하지 않도록 합니다.
        mapper.insertShow(showDTO);
    }

    //게시글 수정
	public int editShow(ShowDTO showDTO) {
        int showResult = mapper.editShow(showDTO);
        
        if (showResult == 0) {
            throw new RuntimeException("Failed to update board");
        }
        return showResult;
    }
}
