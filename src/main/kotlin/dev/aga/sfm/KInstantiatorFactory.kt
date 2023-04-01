package dev.aga.sfm

import org.simpleflatmapper.reflect.BiInstantiator
import org.simpleflatmapper.reflect.InstantiatorDefinition
import org.simpleflatmapper.reflect.InstantiatorFactory
import org.simpleflatmapper.reflect.Parameter
import org.simpleflatmapper.reflect.asm.AsmFactoryProvider
import org.simpleflatmapper.util.BiFunction
import java.lang.reflect.Type
import kotlin.reflect.KClass

class KInstantiatorFactory(asmFactoryProvider: AsmFactoryProvider) : InstantiatorFactory(asmFactoryProvider) {
    override fun <S1, S2, T> getBiInstantiator(
        target: Type,
        s1: Class<*>,
        s2: Class<*>,
        constructors: List<InstantiatorDefinition>,
        injections: Map<Parameter, BiFunction<in S1, in S2, *>>,
        useAsmIfEnabled: Boolean,
        builderIgnoresNullValues: Boolean,
    ): BiInstantiator<S1, S2, T> {

        toKClass(target)?.let {
            val def = KConstructorInstantiatorDefinition(it)
            return KConstructorBiInstantiator(def, injections)
        }

        return super.getBiInstantiator(
            target,
            s1,
            s2,
            constructors,
            injections,
            useAsmIfEnabled,
            builderIgnoresNullValues
        )
    }

    companion object {
        /**
         * Gets the [KClass] for the provided [Type] if we are able to support it.
         *
         * @return the [KClass] for the [target] if we can support it, otherwise ```null```
         */
        internal fun toKClass(target: Type): KClass<*>? {
            val targetClazz = Class.forName(target.typeName).kotlin
            if (targetClazz.isData) {
                return targetClazz
            }
            return null
        }
    }
}
