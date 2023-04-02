package dev.aga.sfm

import org.simpleflatmapper.jooq.SelectQueryMapper
import org.simpleflatmapper.jooq.SelectQueryMapperFactory
import org.simpleflatmapper.reflect.ReflectionService
import org.simpleflatmapper.reflect.asm.AsmFactory
import kotlin.reflect.KClass

fun <T : Any> SelectQueryMapperFactory.newMapper(clazz: KClass<T>): SelectQueryMapper<T> {
    // the useAsm bool is private so for now we must assume it's true
    // otherwise maybe we should build the default reflection service, and then
    // call getReflectionService?
    reflectionService(KReflectionService(AsmFactory(ReflectionService::class.java.classLoader)))
    return newMapper(clazz.java)
}
