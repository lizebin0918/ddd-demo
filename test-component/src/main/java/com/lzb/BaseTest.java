package com.lzb;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.approvaltests.JsonApprovals;
import org.approvaltests.approvers.FileApprover;
import org.approvaltests.core.Options;
import org.approvaltests.core.VerifyResult;
import org.json.JSONException;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * 测试基类<br/>
 * Created on : 2023-03-01 10:31
 * @author lizebin
 */
public abstract class BaseTest extends Mockito implements BaseAssertions {

    private static final ObjectMapper INSTANCE = JsonMapper.builder()
            .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true)
            .configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .defaultLocale(Locale.CHINA)
            .defaultTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()))
            .addModule(new JavaTimeModule())
            .build();

    static {

        INSTANCE
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
        .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        ;
    }

    /**
     * 支持字段乱序断言
     * 1. STRICT：默认模式，要求 JSON 中的所有元素都必须一致，包括顺序和值(数组)的类型。
     *
     * 2. NON_EXTENSIBLE: 在 STRICT 模式基础上，JSON 目标值和实际值的元素必须一致
     *
     * 3. LENIENT：允许 JSON 中的目标值比测试值多，但是不允许存在任何一个测试值不在目标值内，忽略在目标值中找不到的项（即测试值多的情况）。
     *
     * 4. NON_EXTENSIBLE_LENIEN：LENIENT 模式在 NON_EXTENSIBLE 模式上的拓展，允许在测试值比目标值多的情况下，忽略测试值中的多余项。
     *
     * 总的来说，这些常量模式可以帮助开发人员方便地定义 JSON 对象之间的比较规则，以便测试 JSON 数据的正确性和一致性。
     * @param o
     * @param toJsonConvertor
     * @param <T>
     */
    protected void assertJSON(Object o, String... excludeFields) {
        Options options = getOptions();
        try {
            JsonApprovals.verifyJson(INSTANCE.copy().setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
                @Override
                public boolean hasIgnoreMarker(final AnnotatedMember m) {
                    List<String> exclusions = Arrays.asList(excludeFields);
                    return exclusions.contains(m.getName()) || super.hasIgnoreMarker(m);
                }
            }).writerWithDefaultPrettyPrinter().writeValueAsString(o), options);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected void assertJSON(String jsonString) {
        JsonApprovals.verifyJson(jsonString, getOptions());
    }

    private Options getOptions() {
        Options options = new Options();
        options = options.withComparator((actualFile, exceptFile) -> {
            if (!exceptFile.exists()) {
                return FileApprover.approveTextFile(actualFile, exceptFile);
            }
            try {
                String actualString = Files.readString(Paths.get(actualFile.getAbsolutePath()));
                String expectString = Files.readString(Paths.get(exceptFile.getAbsolutePath()));
                JSONAssert.assertEquals(expectString, actualString, JSONCompareMode.NON_EXTENSIBLE);
                return VerifyResult.SUCCESS;
            } catch (IOException | JSONException e) {
                return VerifyResult.FAILURE;
            }
        });
        return options;
    }

}
