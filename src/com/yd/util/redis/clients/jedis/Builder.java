package com.yd.util.redis.clients.jedis;

public abstract class Builder<T> {
    public abstract T build(Object data);
}
