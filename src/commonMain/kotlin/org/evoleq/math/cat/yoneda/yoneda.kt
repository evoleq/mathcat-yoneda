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
