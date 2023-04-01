package dev.aga.sfm

import dev.aga.sfm.model.KotlinClass
import dev.aga.sfm.model.KotlinDataClass
import dev.aga.sfm.model.KotlinObject
import dev.aga.sfm.model.MyPojo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

internal class KInstantiatorFactoryTest {

    @ParameterizedTest
    @MethodSource("testToKClassProvider")
    internal fun testToKClass(target: Type, expected: KClass<*>?) {
        val actual = KInstantiatorFactory.toKClass(target)
        assertThat(actual).isEqualTo(expected)
    }

    companion object {
        @OptIn(ExperimentalStdlibApi::class)
        @JvmStatic
        private fun testToKClassProvider(): List<Arguments> {
            return listOf(
                arguments(typeOf<MyPojo>().javaType, null),
                arguments(typeOf<KotlinClass>().javaType, null),
                arguments(typeOf<KotlinObject>().javaType, null),
                arguments(typeOf<KotlinDataClass>().javaType, KotlinDataClass::class)
            )
        }
    }
}
