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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 *
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StateData<S extends Enum<S>, T> {
    S state;
    T data;
    String error;

    public static<S extends Enum<S>, T> StateData<S, T> create(S state, T data) {
        return create(state, data, "");
    }

    public static<S extends Enum<S>, T> StateData<S, T> create(S state, T data, String error) {
        return new StateData<>(state, data, error);
    }

    public static <S extends Enum<S>, T> StateData<S,T> errorFrom(StateData<S,T> old, S state, String error) {
        return create(state, old.getData(), error);
    }

    public static <S extends Enum<S>, T> StateData<S,T> from(StateData<S,T> old, S state) {
        return create(state, old.getData(), old.getError());
    }
}
