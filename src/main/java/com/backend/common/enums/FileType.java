package com.backend.common.enums;

import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {
	IMAGE("이미지", "images"),
	;

	private final String description;
	private final String directory;

	static final Map<FileType, Set<FileExtension>> EXTENSIONS_BY_TYPE = Map.of(
		IMAGE, Set.of(FileExtension.JPG, FileExtension.JPEG, FileExtension.PNG)
	);

	public boolean isSupportedExtension(FileExtension extension) {
		return EXTENSIONS_BY_TYPE.get(this).contains(extension);
	}
}
