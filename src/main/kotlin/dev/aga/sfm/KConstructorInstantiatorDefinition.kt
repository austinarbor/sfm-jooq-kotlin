package dev.aga.sfm

import org.simpleflatmapper.reflect.InstantiatorDefinition
import org.simpleflatmapper.reflect.Parameter
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

class KConstructorInstantiatorDefinition(clazz: KClass<*>) : InstantiatorDefinition {
    val ctor: KFunction<*> = clazz.primaryConstructor!!

    private val params by lazy {
        ctor.parameters.map { kParam ->
            val kt = kParam.type.jvmErasure
            val clazz = kt.java
            Parameter(kParam.index, kParam.name, clazz)
        }.toTypedArray()
    }

    override fun getParameters(): Array<Parameter> {
        return params
    }

    override fun hasParam(param: Parameter): Boolean {
        return params.any { it == param }
    }

    override fun getType(): InstantiatorDefinition.Type {
        return InstantiatorDefinition.Type.CONSTRUCTOR
    }

    override fun getName(): String {
        return ctor.name
    }
}
