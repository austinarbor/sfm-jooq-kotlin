# sfm-jooq-kotlin

This library is a simple enhancement on top of `sfm-jooq` to provide additional kotlin support. Currently, support has
only been added for data classes. This repo is brand new and will be constantly evolving, so I am not providing any
backwards compatibility guarantees between releases for the time being.

# What's Inside

1. `newMapper` extension function for `SelectQueryMapperFactory` which accepts a `KClass<*>` and instantiates custom
   handlers for the construction of the class by the mapper if the class is a data class. If the passed in `KClass` is
   not a data class, we revert to the default behavior
2. Custom `BiInstantiator`, `InstantiatorFactory`, `ReflectionService`, and `InstantiatorDefinition` to support the
   functionality

# Usage

For general usage of the `SelectQueryMapper` , see
the [SimpleFlatMapper docs](https://simpleflatmapper.org/0106-getting-started-jooq.html)

```kotlin
// note: each property of the class must have a default value
// OR you must be sure to always include values for the 
// properties without default values in your queries
data class Person(
    val id: Long? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val address: Address? = null,
)

data class Address(
    val id: Long? = null,
    val streetName: String? = null,
    val streetNumber: String? = null,
    val zipCode: String? = null,
    val city: String? = null,
    val state: String? = null,
)
```

```kotlin
import dev.aga.sfm.newMapper

// other imports

class MyService(private val dsl: DSLContext) {
    private val mapper =
        SelectQueryMapperFactory
            .newInstance()
            .ignorePropertyNotFound()
            .newMapper(Person::class) // note this takes a KClass

    fun getPersons(): List<Person> {
        val ppl = Persons.PERSONS
        val addr = Addresses.ADDRESSES

        val query = dsl.select(
            ppl.ID,
            ppl.FIRST_NAME,
            ppl.LAST_NAME,
            addr.ID,
            addr.STREET_NAME,
            addr.STREET_NUMBER,
            addr.ZIP_CODE,
            addr.CITY,
            addr.STATE
        ).from(ppl)
            .join(addr).on(ppl.ADDRESS_ID.eq(addr.ID))

        return mapper.asList(query)
    }
}
```
