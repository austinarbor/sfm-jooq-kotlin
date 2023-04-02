package dev.aga.sfm

import dev.aga.sfm.model.KotlinClass
import dev.aga.sfm.model.KotlinDataClass
import dev.aga.sfm.model.KotlinObject
import dev.aga.sfm.model.MyPojo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

internal class KReflectionServiceTest {
    @ParameterizedTest
    @MethodSource("testToKClassProvider")
    internal fun testToKClass(target: Type, expected: KClass<*>?) {
        val actual = KReflectionService.toKClass(target)
        assertThat(actual).isEqualTo(expected)
    }

    companion object {
        @OptIn(ExperimentalStdlibApi::class)
        @JvmStatic
        private fun testToKClassProvider(): List<Arguments> {
            return listOf(
                Arguments.arguments(typeOf<MyPojo>().javaType, null),
                Arguments.arguments(typeOf<KotlinClass>().javaType, null),
                Arguments.arguments(typeOf<KotlinObject>().javaType, null),
                Arguments.arguments(typeOf<KotlinDataClass>().javaType, KotlinDataClass::class)
            )
        }
    }
}
