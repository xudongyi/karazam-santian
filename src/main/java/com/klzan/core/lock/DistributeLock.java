package com.klzan.core.lock;

import com.klzan.core.util.TimeLength;

public interface DistributeLock {
    /**
     * @param lockStack
     * @param lockActionName
     * @return
     */
    boolean lock(LockStack lockStack, String lockActionName);

    /**
     * @param lockStack
     * @param lockActionName
     * @param waitTime       wait time for fetch lock
     * @return
     */
    boolean lock(LockStack lockStack, String lockActionName, TimeLength waitTime);

    /**
     * @param lockStack
     * @param lockActionName
     */
    void unLock(LockStack lockStack, String lockActionName);

    /**
     * 尝试加锁,如果失败，立马返回
     *
     * @param lockStack
     * @param lockActionName
     * @param expireTime
     * @return
     */
    boolean tryLock(LockStack lockStack, String lockActionName, TimeLength expireTime);
}
