package dev.aga.sfm

import dev.aga.sfm.model.KotlinDataClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.simpleflatmapper.reflect.impl.InjectConstructorBiInstantiator
import org.simpleflatmapper.reflect.instantiator.ExecutableInstantiatorDefinition
import kotlin.reflect.javaType
import kotlin.reflect.jvm.javaConstructor
import kotlin.reflect.typeOf

internal class KInstantiatorFactoryTest {
    @OptIn(ExperimentalStdlibApi::class)
    @Test
    internal fun `test getBiInstantiator checks for KConsturcotrInstantiatorDefinition first`() {
        val reflectionService = KReflectionService(null)
        val factory = reflectionService.instantiatorFactory
        val def = KConstructorInstantiatorDefinition(KotlinDataClass::class)
        val badDef = ExecutableInstantiatorDefinition(def.ctor.javaConstructor, *def.parameters)
        val biInstantiator =
            factory.getBiInstantiator<Any, Any, KotlinDataClass>(
                typeOf<KotlinDataClass>().javaType,
                Any::class.java,
                Any::class.java,
                listOf(badDef, def),
                mapOf(),
                false,
                true
            )
        assertThat(biInstantiator).isInstanceOf(KConstructorBiInstantiator::class.java)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    internal fun `test getBiInstantiator delegates to super if no KConstructorInstantiatorDefinition available`() {
        val reflectionService = KReflectionService(null)
        val factory = reflectionService.instantiatorFactory
        val def = KConstructorInstantiatorDefinition(KotlinDataClass::class)
        val notKDef = ExecutableInstantiatorDefinition(def.ctor.javaConstructor, *def.parameters)
        val biInstantiator =
            factory.getBiInstantiator<Any, Any, KotlinDataClass>(
                typeOf<KotlinDataClass>().javaType,
                Any::class.java,
                Any::class.java,
                listOf(notKDef),
                mapOf(),
                false,
                true
            )

        assertThat(biInstantiator).isInstanceOf(InjectConstructorBiInstantiator::class.java)
    }
}
