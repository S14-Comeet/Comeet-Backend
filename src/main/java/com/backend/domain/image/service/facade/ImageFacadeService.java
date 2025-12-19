package com.backend.domain.image.service.facade;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.common.component.FileUploader;
import com.backend.common.enums.FileType;
import com.backend.domain.image.dto.ImageResDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageFacadeService {
	private final FileUploader fileUploader;

	public ImageResDto uploadImage(final MultipartFile image) {
		String imageUrl = fileUploader.uploadFile(image, FileType.IMAGE);
		return ImageResDto.builder()
			.url(imageUrl)
			.build();
	}
}
