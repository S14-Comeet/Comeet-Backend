package com.backend.domain.image.service.facade;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.common.enums.FileType;
import com.backend.common.s3.service.S3FileUploader;
import com.backend.domain.image.dto.ImageResDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageFacadeService {
	private final S3FileUploader imageUploader;

	public ImageResDto uploadImage(final MultipartFile image) {
		String imageUrl = imageUploader.uploadFile(image, FileType.IMAGE);
		return ImageResDto.builder()
			.url(imageUrl)
			.build();
	}
}
