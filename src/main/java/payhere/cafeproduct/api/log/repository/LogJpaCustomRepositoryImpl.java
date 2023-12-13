package payhere.cafeproduct.api.log.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import payhere.cafeproduct.api.log.domain.QLog;
import payhere.cafeproduct.api.log.event.vo.LogDetail;

@RequiredArgsConstructor
public class LogJpaCustomRepositoryImpl implements LogJpaCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<LogDetail> findLogList(Integer userId, Long logId, Pageable pageable) {
        QLog l = QLog.log1;

        QueryResults<LogDetail> list = queryFactory.select(
                Projections.constructor(
                        LogDetail.class,
                        l.id,
                        l.logType,
                        l.log,
                        l.logData,
                        l.userId,
                        l.createdDate
                )).from(l).where(
                l.userId.eq(userId).and(
                        ltLogId(logId)
                )
        ).orderBy(l.id.desc()).fetchResults();

        return new PageImpl(list.getResults(), pageable, list.getTotal());
    }

    private BooleanExpression ltLogId(Long logId) {
        if (logId.equals(0L)) {
            return null;
        }

        return QLog.log1.id.lt(logId);
    }
}
