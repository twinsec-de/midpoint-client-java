package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.client.api.ObjectReference;
import com.evolveum.midpoint.client.api.TaskFuture;
import com.evolveum.midpoint.xml.ns._public.common.common_3.TaskType;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RestPrismCompletedFuture<T> implements TaskFuture<T> {

    private T object;

    public RestPrismCompletedFuture(T object) {
        this.object = object;
    }

    @Override
    public ObjectReference<TaskType> getTaskRef() {
        return null;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return object;
    }

    @Override
    public T get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return object;
    }
}
