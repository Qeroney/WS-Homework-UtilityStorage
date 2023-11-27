package thewhite.homework.utils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicates {

    private final List<Predicate> predicates = new ArrayList<>();

    public <T> QPredicates add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    public Predicate buildAnd() {
        Predicate predicate = ExpressionUtils.allOf(predicates);
        if (predicate == null) {
            return new BooleanBuilder();
        }
        return predicate;
    }

    public Predicate buildOr() {
        Predicate predicate = ExpressionUtils.anyOf(predicates);
        if (predicate == null) {
            return new BooleanBuilder();
        }
        return predicate;
    }

    public static QPredicates builder() {
        return new QPredicates();
    }
}
