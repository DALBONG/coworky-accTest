package com.coworky.coworky.repository;

import com.coworky.coworky.domain.Accommodation;
import com.coworky.coworky.domain.QAccommodation;
import com.coworky.coworky.domain.QLocation;
import com.coworky.coworky.dto.AccomSearchFilterDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AccommodationRepositoryImpl
        implements AccommodationRepositoryCustom{

    private final JPAQueryFactory qf;

    @Override
    public List<Accommodation> search(AccomSearchFilterDto filter) {

        QAccommodation acc = QAccommodation.accommodation;
        QLocation loc = QLocation.location;

        BooleanBuilder bBuilder = new BooleanBuilder();

        // 공백 제거 값
        String nameKey = filter.getName() != null ? filter.getName().trim() : null;
        String descriptKey = filter.getDescription() != null ? filter.getDescription().trim() : null;
        String detailKey = filter.getDetailInfo() != null ? filter.getDetailInfo().trim() : null;
        String cityKey = filter.getCity() != null ? filter.getCity().trim() : null;
        String addressKey = filter.getAddress() != null ? filter.getAddress().trim() : null;
        String region = filter.getRegion();

        // 숙소 이름 키워드 검색 필터
        if (nameKey != null && !nameKey.isEmpty()) {
            bBuilder.and(acc.name.contains(nameKey));
        }
        // 숙소 소개 키워드 검색 필터
        if (descriptKey != null && !descriptKey.isEmpty()) {
            bBuilder.and(acc.description.contains(descriptKey));
        }
        // 숙소 상세소개 키워드 검색 필터
        if (detailKey != null && !detailKey.isEmpty()) {
            bBuilder.and(acc.detailInfo.contains(detailKey));
        }
        // 최소 금액 필터
        if (filter.getMinPrice() != null){
            bBuilder.and(acc.basePrice.goe(filter.getMinPrice()));
        }
        // 최대 금액 필터
        if (filter.getMaxPrice() != null){
            bBuilder.and(acc.basePrice.loe(filter.getMaxPrice()));
        }
        // 태그 검색
        if (filter.getTags() != null) {
            bBuilder.and(acc.tags.any().in(filter.getTags()));
        }
        // 타입 검색
        if (filter.getType() != null){
            bBuilder.and(acc.type.eq(filter.getType()));
        }
        // 지역 셀렉 필터
        if (region != null) {
            bBuilder.and(acc.location.region.eq(region));
        }
        // 도시 검색 필터
        if (cityKey != null && !cityKey.isEmpty()) {
            bBuilder.and(acc.location.city.contains(cityKey));
        }
        // 주소 검색 필터
        if (addressKey != null && !addressKey.isEmpty()) {
            bBuilder.and(acc.location.address.contains(addressKey));
        }

        // 검색 쿼리.
        JPAQuery<Accommodation> query =
                qf.selectFrom(acc)
                        .distinct()
                        .leftJoin(acc.location, loc)
                        .fetchJoin()
                        .leftJoin(acc.tags)
                        .fetchJoin()
                        .where(bBuilder);

        // 스코어 정렬
        if (filter.isEnableScore()){

            NumberExpression<Double> finalSco =
                    Expressions.numberTemplate(Double.class,
                            """
                                    (
                                        CAST(10.0 AS DOUBLE) / (CAST(ST_Distance_Sphere(POINT({0},{1}), POINT({2},{3})) AS DOUBLE) + CAST(10.0 AS DOUBLE))
                                    ) * CAST(6.0 AS DOUBLE)
                                    +
                                    (
                                        CAST(10.0 AS DOUBLE) / (CAST({4} AS DOUBLE) + CAST(10.0 AS DOUBLE))
                                    ) * CAST(4.0 AS DOUBLE)
                                    """,
                            loc.longitude, loc.latitude,
                            126.9780, 37.5665,
                            acc.basePrice
                    );
            query.orderBy(finalSco.desc());
        } else {
            query.orderBy(acc.id.asc()); // 기본 정렬
        }
        return query.fetch();
    }
}
