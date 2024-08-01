package com.gae.vo;

import java.util.List;

import com.gae.dto.ShelterDTO;

public class ShelterResonseVo {
	private List<ShelterDTO> shelter;
	private ShelterPaggingVo pagination;

	public ShelterResonseVo(List<ShelterDTO> shelter, ShelterPaggingVo pagination) {
		this.shelter = shelter;
		this.pagination = pagination;
	}

	public List<ShelterDTO> getShelter() {
		return shelter;
	}

	public void setShelter(List<ShelterDTO> shelter) {
		this.shelter = shelter;
	}

	public ShelterPaggingVo getPagination() {
		return pagination;
	}

	public void setPagination(ShelterPaggingVo pagination) {
		this.pagination = pagination;
	}
}
