package com.navisdevs.buildingstore;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.modulith.core.ApplicationModule;
import org.springframework.modulith.core.ApplicationModuleDependency;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты модульной архитектуры (Spring Modulith).
 * <p>
 * Каждый прямой подпакет {@code com.navisdevs.buildingstore} — отдельный модуль:
 * admin, catalog, content, image, news, shared.
 * Классы в подпакетах {@code internal} — приватная реализация модуля,
 * к ним нельзя обращаться из других модулей.
 * <p>
 * Тесты не поднимают Spring-контекст и не требуют базы данных.
 */
class ModularityTests {

    private static final Set<String> EXPECTED_MODULES =
            Set.of("admin", "catalog", "content", "image", "news", "shared");

    private final ApplicationModules modules = ApplicationModules.of(BuildingStoreApplication.class);

    @Test
    @DisplayName("Нет циклических зависимостей и обращений к internal-пакетам чужих модулей")
    void verifiesModularStructure() {
        modules.verify();
    }

    @Test
    @DisplayName("Обнаружены все ожидаемые модули")
    void detectsAllExpectedModules() {
        Set<String> actual = modules.stream()
                .map(ApplicationModule::getName)
                .collect(Collectors.toSet());

        assertThat(actual).containsExactlyInAnyOrderElementsOf(EXPECTED_MODULES);
    }

    @ParameterizedTest(name = "Модуль ''{0}'' зависит только от shared")
    @ValueSource(strings = {"catalog", "content", "image", "news"})
    void businessModulesDependOnlyOnShared(String moduleName) {
        assertThat(dependenciesOf(moduleName))
                .as("Зависимости модуля '%s'", moduleName)
                .isSubsetOf("shared");
    }

    @Test
    @DisplayName("Модуль shared не зависит от бизнес-модулей")
    void sharedModuleHasNoDependencies() {
        assertThat(dependenciesOf("shared")).isEmpty();
    }

    @Test
    @DisplayName("Модуль admin не зависит от других модулей")
    void adminModuleHasNoDependencies() {
        assertThat(dependenciesOf("admin")).isEmpty();
    }

    @Test
    @DisplayName("Генерация документации модулей (target/spring-modulith-docs)")
    void writesDocumentation() {
        modules.forEach(System.out::println);

        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml()
                .writeModuleCanvases();
    }

    private Set<String> dependenciesOf(String moduleName) {
        ApplicationModule module = modules.getModuleByName(moduleName)
                .orElseThrow(() -> new AssertionError("Модуль не найден: " + moduleName));

        return module.getDependencies(modules).stream()
                .map(ApplicationModuleDependency::getTargetModule)
                .map(ApplicationModule::getName)
                .collect(Collectors.toSet());
    }
}
