package com.lzb.arch;

import com.lzb.component.exception.BizException;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.lzb",
        importOptions = {ImportOption.DoNotIncludeJars.class, ImportOption.DoNotIncludeTests.class})
//要扫描的package
public class ArchUnitTest {

    ///////////////////////////////////////////////////////////////////////////
    // 具体格式
    ///////////////////////////////////////////////////////////////////////////

    /*
    ArchRuleDefinition.GIVEN_OBJECTS
    .that(). PREDICATE
    .should(). CONDITION
     */

    ///////////////////////////////////////////////////////////////////////////
    // 指定后缀名
    ///////////////////////////////////////////////////////////////////////////

    @ArchTest
    public static final ArchRule dto_validate_suffix = classes()
            .that()
            .resideInAPackage("..dto..")
            .and()
            .areTopLevelClasses()
            .should()
            .haveSimpleNameEndingWith("Dto");

    ///////////////////////////////////////////////////////////////////////////
    // Assembler/Converter 不能引用 BizException
    ///////////////////////////////////////////////////////////////////////////

    @ArchTest
    public static final ArchRule assembler_converter_not_access_BizException = noClasses()
            .that()
            .resideInAnyPackage("..assembler..", "..converter..")
            .should()
            .accessClassesThat()
            .areAssignableTo(BizException.class);

    ///////////////////////////////////////////////////////////////////////////
    // Controller/Repository 命名
    ///////////////////////////////////////////////////////////////////////////

    @ArchTest
    static ArchRule controllers_should_have_name_ending_with_controller
            = ArchRuleDefinition.classes()
            .that()
            .areAnnotatedWith(Controller.class)
            .and().areTopLevelClasses()
            .should().haveSimpleNameEndingWith(Controller.class.getSimpleName());


    @ArchTest
    static ArchRule repositories_should_have_name_ending_with_repository
            = ArchRuleDefinition.classes()
            .that()
            .implement(Repository.class)
            .and()
            .areTopLevelClasses()
            .should().haveSimpleNameEndingWith("Repository");

    ///////////////////////////////////////////////////////////////////////////
    // 分层隔离
    ///////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////
    // code smell
    ///////////////////////////////////////////////////////////////////////////

    /*
    @ArchTest
  static ArchRule long_parameters_list = ArchRuleDefinition.methods()
    .should(new ArchCondition<>("have parameters at most 3") {
      @Override
      public void check(JavaMethod item, ConditionEvents events) {
        if (item.getParameters().size() > 3) {
          events.add(
            SimpleConditionEvent.violated(item,
              "%s have %s parameters".formatted(item.getDescription(), item.getParameters().size()))
          );
        }
      }
    });

  @ArchTest
  static ArchRule large_class = ArchRuleDefinition.classes()
    .should(new ArchCondition<>("contains less than 30 fields") {
      @Override
      public void check(JavaClass item, ConditionEvents events) {
        if (item.getFields().size() > 30) {
          events.add(
            SimpleConditionEvent.violated(item,
              "%s have %s fields".formatted(item.getDescription(), item.getFields().size()))
          );
        }
      }
    });

  @ArchTest
  static ArchRule long_method = ArchRuleDefinition.methods()
    .should(notHaveCodeLinesMoreThan(30));

  private static ArchCondition<JavaMethod> notHaveCodeLinesMoreThan(final int maxLines) {
    return new ArchCondition<>("not have no more than %s code lines".formatted(maxLines)) {
      @Override
      public void check(JavaMethod item, ConditionEvents events) {
        var first = item.getSourceCodeLocation().getLineNumber();
        var last = item.getCallsFromSelf().stream().mapToInt(JavaCall::getLineNumber).max()
          .orElse(first);
        var lines = last - first + 1;
        if (lines > maxLines) {
          events.add(
            SimpleConditionEvent.violated(
              item,
              "%s have %s code lines".formatted(item.getDescription(), lines)
            )
          );
        }
      }
    };
  }
     */

}