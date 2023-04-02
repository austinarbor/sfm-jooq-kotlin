package dev.aga.sfm

import org.simpleflatmapper.reflect.BiInstantiator
import org.simpleflatmapper.reflect.InstantiatorDefinition
import org.simpleflatmapper.reflect.InstantiatorFactory
import org.simpleflatmapper.reflect.Parameter
import org.simpleflatmapper.reflect.asm.AsmFactoryProvider
import org.simpleflatmapper.util.BiFunction
import java.lang.reflect.Type

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

        // if we have a Kotlin Constructor instantiator, return that right away
        constructors
            .filterIsInstance<KConstructorInstantiatorDefinition>()
            .firstOrNull()?.let {
                return KConstructorBiInstantiator(it, injections)
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
}
