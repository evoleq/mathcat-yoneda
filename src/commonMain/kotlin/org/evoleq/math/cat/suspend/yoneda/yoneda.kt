package org.evoleq.math.cat.suspend.yoneda

import kotlinx.coroutines.CoroutineScope
import org.evoleq.math.cat.suspend.morphism.ScopedSuspended
import org.evoleq.math.cat.suspend.morphism.by

interface HomSetNatTrafo<A,B> {
    fun <X> eta(aToX: suspend CoroutineScope.(A)->X): suspend CoroutineScope.(B)->X
    fun <X> eta(aToX: ScopedSuspended<A,X>): ScopedSuspended<B, X> = ScopedSuspended ( eta(by(aToX)) )
}

fun <A,B> HomSetNatTrafo<A, B>.yoneda(): ScopedSuspended<B, A> = ScopedSuspended{
    b -> this@yoneda.eta{a -> a}(b)
}

fun <A, B, X> (suspend CoroutineScope.(B)->A).yonedaInverse(): (suspend CoroutineScope.(A)->X)->(suspend CoroutineScope.(B)->X) = {
    aToX: suspend CoroutineScope.(A)->X -> {
        b -> aToX( this@yonedaInverse(b) )
    }
}

fun <A, B> ScopedSuspended<B, A>.yonedaInverse(): HomSetNatTrafo<A, B> = object : HomSetNatTrafo<A, B> {
    override fun <X> eta(aToX: suspend CoroutineScope.(A) -> X): suspend CoroutineScope.(B) -> X =
        by(this@yonedaInverse).yonedaInverse<A, B, X>()(aToX)
}