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

import io.appform.functionmetrics.MonitoredFunction;
import io.appform.signals.signals.ConsumingSyncSignal;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
public class StateMachine<T, D, S extends Enum<S>, C extends ActionContext<D>, A extends Action<T, S, C, D>> {

    private final Map<S, Transition<T, D, S, C, A>> validTransitions;
    private final ConsumingSyncSignal<StateData<S, T>> stateChanged;

    @Getter
    private StateData<S, T> currentState;
    @Getter
    private final C context;
    private final ActionFactory<T, D, S, C, A> actionFactory;
    private final AtomicReference<A> currentAction = new AtomicReference<>();

    protected StateMachine(
            final StateData<S, T> initalState,
            C context,
            ActionFactory<T, D, S,C,A> actionFactory,
            List<Transition<T, D, S, C, A>> transitions) {
        this.context = context;
        this.actionFactory = actionFactory;
        this.stateChanged = new ConsumingSyncSignal<>();
        this.validTransitions = transitions.stream()
                .collect(Collectors.toMap(Transition::getFrom, Function.identity()));
        this.currentState = initalState;
    }

    public final ConsumingSyncSignal<StateData<S, T>> onStateChange() {
        return stateChanged;
    }

    @MonitoredFunction
    public S execute() {
        val state = currentState.getState();
        val transition = validTransitions.get(state);
        currentAction.set(actionFactory.create(transition));
        log.debug("Action to be executed: {}", currentAction.get().getClass().getSimpleName());
        val newStateData = currentAction.get().execute(context, currentState);
        val newState = newStateData.getState();
        if (!transition.getTo().contains(newState)) {
            throw new IllegalStateException("No defined transitions from " + state.name() + " to " + newState.name());
        }
        currentState = newStateData;
        stateChanged.dispatch(newStateData);
        return newState;
    }

    public Optional<A> currentAction() {
        return Optional.ofNullable(currentAction.get());
    }

    public boolean notifyUpdate(D parameter) {
        val status = context.recordUpdate(parameter);
        if(status) {
            log.info("Update recorded successfully");
        }
        else {
            log.error("Update could not be recorded as there is already an update being processed. Current update: {}", context.getUpdate().orElse(null));
        }
        return status;
    }

    public void stop() {
        context.getAlreadyStopped().set(true);
        val action = currentAction.get();

        if(action != null) {
            action.stop(); //TODO::FIX the race condition here
        }
    }
}
