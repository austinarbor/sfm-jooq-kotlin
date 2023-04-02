package dev.aga.sfm

import dev.aga.sfm.model.KotlinDataClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.simpleflatmapper.reflect.Parameter
import org.simpleflatmapper.util.BiFunction

internal class KConstructorBiInstantiatorTest {
    @Test
    internal fun `test all values are passed into the constructor`() {
        val def = KConstructorInstantiatorDefinition(KotlinDataClass::class)
        val injections = mapOf(
            Parameter(0, "id", Int::class.java) to makeBiFunction(6),
            Parameter(1, "name", String::class.java) to makeBiFunction("sfm-jooq-kotlin")
        )
        val instantiator = KConstructorBiInstantiator<Any, Any, KotlinDataClass>(def, injections)

        val actual = instantiator.newInstance(Any(), Any())
        val expected = KotlinDataClass(6, "sfm-jooq-kotlin")
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun `test default values are used if not provided`() {
        val def = KConstructorInstantiatorDefinition(KotlinDataClass::class)
        val injections = mapOf(
            Parameter(0, "id", Int::class.java) to makeBiFunction(6)
        )
        val instantiator = KConstructorBiInstantiator<Any, Any, KotlinDataClass>(def, injections)

        val actual = instantiator.newInstance(Any(), Any())
        val expected = KotlinDataClass(id = 6)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun `test unrecognized properties are ignored`() {
        val def = KConstructorInstantiatorDefinition(KotlinDataClass::class)
        val injections = mapOf(
            Parameter(0, "id", Int::class.java) to makeBiFunction(6),
            Parameter(1, "name", String::class.java) to makeBiFunction("Samara"),
            Parameter(2, "doesNotExist", Long::class.java) to makeBiFunction(-1L)
        )
        val instantiator = KConstructorBiInstantiator<Any, Any, KotlinDataClass>(def, injections)

        val actual = instantiator.newInstance(Any(), Any())
        val expected = KotlinDataClass(6, "Samara")
        assertThat(actual).isEqualTo(expected)
    }


    private fun makeBiFunction(ret: Any): BiFunction<Any, Any, *> {
        return BiFunction<Any, Any, Any> { _, _ -> ret }
    }
}
