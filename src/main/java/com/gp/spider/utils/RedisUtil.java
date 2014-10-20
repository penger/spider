package com.gp.spider.utils;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisUtil {
	// 数据源
			private ShardedJedisPool shardedJedisPool;
			
			public RedisUtil() {
				JedisPoolConfig config =new JedisPoolConfig();//Jedis池配置
				config.setMaxIdle(1000 * 60);//对象最大空闲时间
				config.setMaxWaitMillis(1000 * 10);//获取对象时最大等待时间
				config.setTestOnBorrow(true);
				
				List<JedisShardInfo> jdsInfoList =new ArrayList<JedisShardInfo>(1);
				JedisShardInfo infoA = new JedisShardInfo("spider2.crxy.cn", 6379);
				jdsInfoList.add(infoA);
				
				this.shardedJedisPool = new ShardedJedisPool(config, jdsInfoList);
			}

			/**
			 * 执行器，{@link com.futurefleet.framework.base.redis.RedisUtil}的辅助类，
			 * 它保证在执行操作之后释放数据源returnResource(jedis)
			 * @version V1.0
			 * @author fengjc
			 * @param <T>
			 */
			abstract class Executor<T> {

				ShardedJedis jedis;
				ShardedJedisPool shardedJedisPool;

				public Executor(ShardedJedisPool shardedJedisPool) {
					this.shardedJedisPool = shardedJedisPool;
					jedis = this.shardedJedisPool.getResource();
				}

				/**
				 * 回调
				 * @return 执行结果
				 */
				abstract T execute();

				/**
				 * 调用{@link #execute()}并返回执行结果
				 * 它保证在执行{@link #execute()}之后释放数据源returnResource(jedis)
				 * @return 执行结果
				 */
				public T getResult() {
					T result = null;
					try {
						result = execute();
					} catch (Throwable e) {
						throw new RuntimeException("Redis execute exception", e);
					} finally {
						if (jedis != null) {
							shardedJedisPool.returnResource(jedis);
						}
					}
					return result;
				}
			}
			
			public void rpush(final String key, final Object value) {
				new Executor(shardedJedisPool) {
					@Override
					Object execute() {
						jedis.rpush(key, value.toString());
						return null;
					}
				};
			}
			
			public String lpop(final String key) {
				return new Executor<String>(shardedJedisPool) {
					@Override
					String execute() {
						jedis.lpop(key);
						return null;
					}
				}.getResult();
			}
			
			public Long llen(final String key) {
				return new Executor<Long>(shardedJedisPool) {
					@Override
					Long execute() {
						return jedis.llen(key);
					}
				}.getResult();
			}

			public Boolean sismember(final String key, final String member) {
				
				return new Executor<Boolean>(shardedJedisPool) {
					@Override
					Boolean execute() {
						return jedis.sismember(key, member);
					}
				}.getResult();
			}
			

			public void sadd(final String key, final String target) {
				new Executor(shardedJedisPool) {
					@Override
					Object execute() {
						jedis.sadd(key, target);
						return null;
					}
				};
			}
}
