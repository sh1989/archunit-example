package uk.co.samhogy.archunit.example;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "uk.co.samhogy.archunit.example", importOptions = { ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class })
public class ArchitectureTests {

    @ArchTest
    static ArchRule no_generic_exceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

    @ArchTest
    static ArchRule do_not_use_jodatime = NO_CLASSES_SHOULD_USE_JODATIME;

    @ArchTest
    static ArchRule layeredArchitecture =
            layeredArchitecture()
            .layer("Entity").definedBy("..entity..")
            .layer("Repository").definedBy("..repository..")
            .layer("Domain").definedBy("..domain..")
            .layer("Service").definedBy("..service..")
            .layer("DTO").definedBy("..dto..")
            .layer("Web").definedBy("..web..")

            .whereLayer("DTO").mayOnlyBeAccessedByLayers("Web")
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Web")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Web", "Service")
            .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
            .whereLayer("Entity").mayOnlyBeAccessedByLayers("Repository", "Service");

    @ArchTest
    static ArchRule entities_must_be_suffixed_in_correct_package =
            classes()
            .that().areAnnotatedWith("Entity")
            .should().resideInAPackage("..entity..")
            .andShould().haveSimpleNameEndingWith("Entity");

    @ArchTest
    static ArchRule services_must_be_in_correct_package =
            classes()
            .that().haveSimpleNameEndingWith("Service")
            .should().resideInAPackage("..service..");

    @ArchTest
    static ArchRule rest_resources_must_be_in_correct_package =
            classes()
            .that().areAnnotatedWith("RestController")
            .should().resideInAnyPackage("..web..");

    @ArchTest
    static ArchRule dtos_must_be_suffixed_in_correctPackage =
            classes()
            .that().resideInAPackage("..dto..")
            .should().haveSimpleNameEndingWith("DTO");

    @ArchTest
    static ArchRule classes_must_not_be_suffixed_with_impl =
            noClasses()
            .should().haveSimpleNameEndingWith("Impl");
}
