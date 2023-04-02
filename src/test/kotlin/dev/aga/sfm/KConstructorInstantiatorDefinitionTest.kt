package dev.aga.sfm

import dev.aga.sfm.model.KotlinDataClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.simpleflatmapper.reflect.InstantiatorDefinition
import org.simpleflatmapper.reflect.Parameter

internal class KConstructorInstantiatorDefinitionTest {

    @Test
    internal fun testGetName() {
        val def = KConstructorInstantiatorDefinition(KotlinDataClass::class)
        assertThat(def.name).isEqualTo("dev.aga.sfm.model.KotlinDataClass")
    }

    @Test
    internal fun testGetParameters() {
        val def = KConstructorInstantiatorDefinition(KotlinDataClass::class)
        val actual = def.parameters
        val expected = arrayOf(
            Parameter(0, "id", Int::class.java),
            Parameter(1, "name", String::class.java)
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun testHasParam() {
        val def = KConstructorInstantiatorDefinition(KotlinDataClass::class)
        val idParam = Parameter(0, "id", Int::class.java)
        assertThat(def.hasParam(idParam)).isTrue

        val nameParam = Parameter(1, "name", String::class.java)
        assertThat(def.hasParam(nameParam)).isTrue

        val badNameParam = Parameter(2, "name", String::class.java)
        assertThat(def.hasParam(badNameParam)).isFalse

        val randomParam = Parameter(3, "fullName", String::class.java)
        assertThat(def.hasParam(randomParam)).isFalse
    }

    @Test
    internal fun testGetType() {
        val def = KConstructorInstantiatorDefinition(KotlinDataClass::class)
        assertThat(def.type).isEqualTo(InstantiatorDefinition.Type.CONSTRUCTOR)
    }
}
