package dev.aga.sfm

import org.simpleflatmapper.reflect.DefaultReflectionService
import org.simpleflatmapper.reflect.InstantiatorFactory
import org.simpleflatmapper.reflect.asm.AsmFactory

class KReflectionService(asmFactory: AsmFactory?) : DefaultReflectionService(asmFactory) {
    override fun getInstantiatorFactory(): InstantiatorFactory {
        return KInstantiatorFactory(this)
    }
}
