package dev.aga.sfm

import org.simpleflatmapper.reflect.DefaultReflectionService
import org.simpleflatmapper.reflect.InstantiatorDefinition
import org.simpleflatmapper.reflect.InstantiatorFactory
import org.simpleflatmapper.reflect.asm.AsmFactory
import java.lang.reflect.Member
import java.lang.reflect.Type
import kotlin.reflect.KClass

class KReflectionService(asmFactory: AsmFactory?) : DefaultReflectionService(asmFactory) {
    override fun getInstantiatorFactory(): InstantiatorFactory {
        return KInstantiatorFactory(this)
    }

    override fun extractInstantiator(target: Type, extraInstantiator: Member?): MutableList<InstantiatorDefinition> {
        toKClass(target)?.let {
            return mutableListOf(KConstructorInstantiatorDefinition(it))
        }

        return super.extractInstantiator(target, extraInstantiator)
    }

    companion object {
        /**
         * Gets the [KClass] for the provided [Type] if we are able to support it.
         *
         * @param target the [Type] to fetch the [KClass] for
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
