package com.weison.base.util;

import org.redisson.api.*;

/**
 * @author xiaomalover <xiaomalover@gmail.com>
 * 消费者程序入口
 */
public class RedissonUtil {


    /**
     * 获取字符串对象
     *
     * @param redissonClient redisson客户端
     * @param objectName 缓存键名
     * @return RBucket
     */
    public static <T> RBucket<T> getRBucket(RedissonClient redissonClient, String objectName) {
        return redissonClient.getBucket(objectName);
    }

    /**
     * 获取Map对象
     *
     * @param redissonClient redisson客户端
     * @param objectName 缓存键名
     * @return RMap
     */
    public static <K, V> RMap<K, V> getRMap(RedissonClient redissonClient, String objectName) {
        return redissonClient.getMap(objectName);
    }

    /**
     * 获取有序集合
     *
     * @param redissonClient redisson客户端
     * @param objectName 缓存键名
     * @return RSortedSet
     */
    public static <V> RSortedSet<V> getRSortedSet(RedissonClient redissonClient, String objectName) {
        return redissonClient.getSortedSet(objectName);
    }

    /**
     * 获取集合
     *
     * @param redissonClient redisson客户端
     * @param objectName 缓存键名
     * @return RSet
     */
    public static <V> RSet<V> getRSet(RedissonClient redissonClient, String objectName) {
        return redissonClient.getSet(objectName);
    }

    /**
     * 获取列表
     *
     * @param redissonClient redisson客户端
     * @param objectName 缓存键名
     * @return RList
     */
    public static <V> RList<V> getRList(RedissonClient redissonClient, String objectName) {
        return redissonClient.getList(objectName);
    }

    /**
     * 获取队列
     *
     * @param redissonClient redisson客户端
     * @param objectName 缓存键名
     * @return RQueue
     */
    public static <V> RQueue<V> getRQueue(RedissonClient redissonClient, String objectName) {
        return redissonClient.getQueue(objectName);
    }

    /**
     * 获取双端队列
     *
     *
     * @param redissonClient redisson客户端
     * @param objectName 缓存键名
     * @return RDeque
     */
    public static <V> RDeque<V> getRDeque(RedissonClient redissonClient, String objectName) {
        return  redissonClient.getDeque(objectName);
    }

    /**
     * 此方法不可用在Redisson 1.2 中
     * 在1.2.2版本中 可用
     * @param redisson
     * @param objectName
     * @return
     */
    /**
     public <V> RBlockingQueue<V> getRBlockingQueue(Redisson redisson,String objectName){
     RBlockingQueue rb=redisson.getBlockingQueue(objectName);
     return rb;
     }*/

    /**
     * 获取锁
     *
     * @param redissonClient redisson客户端
     * @param objectName 缓存键名
     * @return RLock
     */
    public static RLock getRLock(RedissonClient redissonClient, String objectName) {
        return redissonClient.getLock(objectName);
    }

    /**
     * 获取原子数
     *
     * @param redissonClient redisson客户端
     * @param objectName 缓存键名
     * @return RAtomicLong
     */
    public static RAtomicLong getRAtomicLong(RedissonClient redissonClient, String objectName) {
        return redissonClient.getAtomicLong(objectName);
    }

    /**
     * 获取记数锁
     *
     * @param redissonClient redisson客户端
     * @param objectName 缓存键名
     * @return RCountDownLatch
     */
    public static RCountDownLatch getRCountDownLatch(RedissonClient redissonClient, String objectName) {
        return redissonClient.getCountDownLatch(objectName);
    }

    /**
     * 获取消息的Topic
     *
     * @param redissonClient redisson客户端
     * @param objectName 缓存键名
     * @return RTopic
     */
    public static <M> RTopic<M> getRTopic(RedissonClient redissonClient, String objectName) {
        return redissonClient.getTopic(objectName);
    }

}
