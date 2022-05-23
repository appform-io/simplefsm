/*
 * Copyright 2022. Santanu Sinha
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 *   compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is
 *  distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied. See the License for the specific language governing permissions and limitations
 *  under the License.
 */

package io.appform.simplefsm;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Set;

/**
 *
 */
@Value
@AllArgsConstructor
public class Transition<T, D, S extends Enum<S>, C extends ActionContext<D>, A extends Action<T, S, C, D>> {
    S from;
    Class<? extends A> action;
    Set<S> to;

    @SafeVarargs
    public Transition(S from, Class<? extends A> action, S... to) {
        this(from, action, Set.of(to));
    }
}
