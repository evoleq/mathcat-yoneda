/**
 * Copyright (c) 2020 Dr. Florian Schmidt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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