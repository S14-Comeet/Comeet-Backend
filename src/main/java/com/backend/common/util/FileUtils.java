package com.backend.common.util;

import org.springframework.util.StringUtils;

import com.backend.common.enums.FileExtension;
import com.backend.common.error.ErrorCode;
import com.backend.common.error.exception.FileException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtils {
	public static FileExtension getFileExtension(String fileName) {
		if (!StringUtils.hasText(fileName) || !fileName.contains(".")) {
			throw new FileException(ErrorCode.INVALID_FILE_NAME);
		}
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		return FileExtension.fromExtension(extension);
	}
}
