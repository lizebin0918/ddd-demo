package com.lzb.demo.common.joininmemory.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.lzb.demo.common.joininmemory.JoinItemExecutor;
import com.lzb.demo.common.joininmemory.JoinItemExecutorFactory;
import org.apache.commons.lang3.reflect.FieldUtils;

import org.springframework.core.annotation.AnnotatedElementUtils;

import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/8/5.
 * 
 * 
 */
abstract class AbstractAnnotationBasedJoinItemExecutorFactory<A extends Annotation>
    implements JoinItemExecutorFactory {
    private final Class<A> annotationCls;

    protected AbstractAnnotationBasedJoinItemExecutorFactory(Class<A> annotationCls) {
        this.annotationCls = annotationCls;
    }

    @Override
    public <DATA> List<JoinItemExecutor<DATA>> createForType(Class<DATA> cls) {
        // 从 字段 上获取 注解，并将其转换为 JoinItemExecutor
        List<Field> fieldsListWithAnnotation = FieldUtils.getAllFieldsList(cls);

        return fieldsListWithAnnotation.stream()
                .map(field -> createForField(cls, field,
                        AnnotatedElementUtils.findMergedAnnotation(field, annotationCls))
                ).filter(Objects::nonNull)
                .collect(toList());
    }

    private <DATA> JoinItemExecutor<DATA> createForField(Class<DATA> cls, Field field,A ann) {
        if (ann == null){
            return null;
        }
        JoinItemExecutorAdapter adapter = JoinItemExecutorAdapter.builder()
                .name(createName(cls, field, ann))
                .runLevel(createRunLevel(cls, field, ann))
                .keyFromSourceData(createKeyGeneratorFromData(cls, field, ann))
                .joinDataLoader(createDataLoader(cls, field, ann))
                .keyFromJoinData(createKeyGeneratorFromJoinData(cls, field, ann))
                .joinDataConverter(createDataConverter(cls, field, ann))
                .foundCallback(createFoundFunction(cls, field, ann))
                .lostCallback(createLostFunction(cls, field, ann))
                .build();

        return adapter;
    }

    protected <DATA> BiConsumer<Object, Object> createLostFunction(Class<DATA> cls, Field field, A ann){
        return null;
    }

    protected abstract <DATA> BiConsumer<Object, List<Object>> createFoundFunction(Class<DATA> cls, Field field, A ann);

    protected abstract <DATA> Function<Object, Object> createDataConverter(Class<DATA> cls, Field field, A ann);

    protected abstract <DATA> Function<Object, Object> createKeyGeneratorFromJoinData(Class<DATA> cls, Field field, A ann);

    protected abstract <DATA> Function<List<Object>, List<Object>> createDataLoader(Class<DATA> cls, Field field, A ann);

    protected abstract <DATA> Function<Object, Object> createKeyGeneratorFromData(Class<DATA> cls, Field field, A ann);

    protected abstract <DATA> int createRunLevel(Class<DATA> cls, Field field, A ann);

    protected <DATA> String createName(Class<DATA> cls, Field field, A ann){
        return new StringBuilder()
                .append("class[").append(cls.getSimpleName()).append("]")
                .append("#field[").append(field.getName()).append("]")
                .append("-").append(ann.getClass().getSimpleName())
                .toString();
    }
}
