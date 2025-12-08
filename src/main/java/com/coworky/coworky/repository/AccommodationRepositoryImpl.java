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

        // 숙소 이름 키워드 검색 필터
        if (filter.getName() != null && !filter.getName().trim().isEmpty()) {
            bBuilder.and(acc.name.contains(filter.getName()));
        }
        // 숙소 소개 키워드 검색 필터
        if (filter.getDescription() != null && !filter.getDescription().trim().isEmpty()) {
            bBuilder.and(acc.description.contains(filter.getDescription()));
        }
        // 숙소 상세소개 키워드 검색 필터
        if (filter.getDetailInfo() != null && !filter.getDetailInfo().trim().isEmpty()) {
            bBuilder.and(acc.detailInfo.contains(filter.getDetailInfo()));
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
        if (filter.getRegion() != null) {
            bBuilder.and(acc.location.region.eq(filter.getRegion()));
        }
        // 도시 검색 필터
        if (filter.getCity() != null && !filter.getCity().trim().isEmpty()) {
            bBuilder.and(acc.location.city.contains(filter.getCity()));
        }
        // 주소 검색 필터
        if (filter.getAddress() != null && !filter.getAddress().trim().isEmpty()) {
            bBuilder.and(acc.location.address.contains(filter.getAddress()));
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
