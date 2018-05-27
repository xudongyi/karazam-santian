package com.klzan.core.lock;

import com.klzan.core.util.DateUtils;
import com.klzan.core.util.TimeLength;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class LocalDistributeLock implements DistributeLock {

    private static final TimeLength MAX_WAIT_TIME = TimeLength.seconds(60);

    private static final TimeLength SLEEP_TIME = TimeLength.milliseconds(200);

    private static final TimeLength LOCK_EXPIRE_TIME = TimeLength.minutes(3);

    private final ConcurrentHashMap<String, LockEntry> locks = new ConcurrentHashMap<String, LockEntry>();

    private static final class LockEntry {

        private final Date expireDate;

        public LockEntry(Date expireDate) {
            super();
            this.expireDate = expireDate;
        }

        public Date getExpireDate() {
            return expireDate;
        }

    }

    public boolean lock(LockStack lockStack, String lockActionName) {
        return this.lock(lockStack, lockActionName, MAX_WAIT_TIME);
    }

    public boolean lock(LockStack lockStack, String lockActionName, TimeLength waitTime) {
        int _waitTime = (int) waitTime.toMilliseconds();
        int waitTimeMax = _waitTime > (int) MAX_WAIT_TIME.toMilliseconds() ? (int) MAX_WAIT_TIME.toMilliseconds() : _waitTime;
        int tryTimes = 0;
        try {
            String key = generatedLockKey(lockStack, lockActionName);
            do {
                LockEntry prevLockEntry = locks.put(key, new LockEntry(expirationTime(LOCK_EXPIRE_TIME.toSeconds())));
                if (prevLockEntry == null || prevLockEntry.getExpireDate().before(new Date())) {
                    return true;
                } else {
                    locks.put(key, prevLockEntry);
                }
                if (tryTimes > 0) {
                    Thread.sleep(SLEEP_TIME.toMilliseconds());
                }
                tryTimes += SLEEP_TIME.toMilliseconds();
            } while (tryTimes < waitTimeMax);
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public void unLock(LockStack lockStack, String lockActionName) {
        locks.remove(generatedLockKey(lockStack, lockActionName));
    }

    protected String generatedLockKey(LockStack lockStack, String lockActionName) {
        StringBuilder key = new StringBuilder("DISTRIBUTELOCK:").append(lockStack.getKey());
        if (lockActionName != null) key.append(lockActionName);
        return key.toString();
    }

    private Date expirationTime(long expireTime) {
        return DateUtils.addSeconds(new Date(), (int) expireTime);
    }

    @Override
    public boolean tryLock(LockStack lockStack, String lockActionName,
                           TimeLength expireTime) {
        return true;
    }
}
