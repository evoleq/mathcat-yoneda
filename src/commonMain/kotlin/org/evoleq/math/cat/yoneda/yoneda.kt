package org.evoleq.math.cat.yoneda




interface HomSetNatTrafo<A,B> {
    fun <X> eta(aToX: (A)->X): (B)->X
}

fun <A,B> HomSetNatTrafo<A, B>.yoneda(): (B)->A = {
    b -> this.eta{a -> a}(b)
}

fun <A, B, X> ((B)->A).yonedaInverse(): ((A)->X)->((B)->X) = {
    aToX: (A)->X -> {
        b -> aToX( this@yonedaInverse(b) )
    }
}
