package dev.aga.sfm

import org.simpleflatmapper.reflect.BiInstantiator
import org.simpleflatmapper.reflect.Parameter
import org.simpleflatmapper.reflect.impl.BiArgumentBuilder
import org.simpleflatmapper.util.BiFunction
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

class KConstructorBiInstantiator<S1, S2, T>(
    private val def: KConstructorInstantiatorDefinition,
    private val injections: Map<Parameter, BiFunction<in S1, in S2, *>>
) : BiInstantiator<S1, S2, T> {

    private val argBuilder: BiArgumentBuilder<S1, S2> = BiArgumentBuilder(def, injections)

    override fun newInstance(s1: S1, s2: S2): T {
        val allArgValues = argBuilder.build(s1, s2)
        val ctor = def.ctor as KFunction<T>
        val ctorParams = ctor.parameters.associateBy { it.name }
        val invokeArgs: Map<KParameter, Any?> =
            injections.asSequence()
                .map { (param, _) -> param }
                .filter { ctorParams.containsKey(it.name) }
                .map { ctorParams[it.name]!! to allArgValues[it.index] }
                .toMap()

        return ctor.callBy(invokeArgs)
    }
}
