package com.backend.domain.map.service.query;

import com.backend.domain.map.dto.request.MapBoundsReqDto;
import com.backend.domain.map.dto.response.MapMarkersResDto;
import com.backend.domain.map.mapper.query.MapQueryMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MapQueryServiceImpl implements MapQueryService {
    private final MapQueryMapper queryMapper;

    @Override
    public MapMarkersResDto getStoresWithinDistance(MapBoundsReqDto reqDto) {
        return null;
    }
}
