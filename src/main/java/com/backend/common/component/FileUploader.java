package com.backend.common.component;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.backend.common.enums.FileExtension;
import com.backend.common.enums.FileType;

public interface FileUploader {
	String uploadFile(MultipartFile file, FileType type);

	static String generateKey(String directory, FileExtension extension) {
		return directory + "/" + UUID.randomUUID() + extension.getExtension();
	}
}
