package com.github.feiyongjing.service.main;

import com.github.feiyongjing.service.CommandTest;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Redis extends CommandTest {
    public static void main(String[] args) {
        CommandTest commandTest = new Redis();
        commandTest.commandInputOutput(commandTest.getQuestionsAndAnswers());
    }

    @Override
    public HashMap<String, String> getQuestionsAndAnswers() {
//        LinkedHashMap<String, String> questionsAndAnswers = new LinkedHashMap<>();
        HashMap<String, String> questionsAndAnswers = new HashMap<>();
        // Redis 连接
        questionsAndAnswers.put("连接本机的Redis", "redis-cli");
        questionsAndAnswers.put("连接远程的Redis", "redis-cli -h host -p port -a password");
        // Redis 键操作命令
        questionsAndAnswers.put("key 存在时删除 key", "del key");
        questionsAndAnswers.put("检查给定 key 是否存在", "exists key");
        questionsAndAnswers.put("设置key过期时间，以秒计，以毫秒计呢", "expire key number & pexpire key number");
        questionsAndAnswers.put("返回给定 key 的剩余生存时间，以秒为单位，以毫秒呢", "ttl key & pttl key");
        questionsAndAnswers.put("移除 key 的过期时间，key 将持久保持", "persist key");
        questionsAndAnswers.put("查找所有符合给定正则匹配(pattern)的 key", "keys pattern");
        questionsAndAnswers.put("将当前数据库的 key 移动到给定数字的数据库 db 当中", "move key dbNumber");
        questionsAndAnswers.put("修改 key 的名称，如果新的名字已经存在就会覆盖值", "rename key newkey");
        questionsAndAnswers.put("仅当 newkey 不存在时，将 key 改名为 newkey", "renamenx key newkey");
        questionsAndAnswers.put("返回 key 所储存的值的类型", "type key");
        questionsAndAnswers.put("从当前数据库中随机返回一个 key", "randomkey");
        questionsAndAnswers.put("序列化给定 key ，并返回被序列化的值", "dump key");
        // Redis String
        questionsAndAnswers.put("String类型：增加一对键值对，如果键已经存在就覆盖值", "set key value");
        questionsAndAnswers.put("String类型：获取一个指定key的值", "get key");
        questionsAndAnswers.put("String类型：增加多对键值对，如果键已经存在就覆盖值", "mset key1 value1 key2 value2");
        questionsAndAnswers.put("String类型：获取多个指定key的值", "mget key1 key2");
        questionsAndAnswers.put("String类型：只有在 key 不存在时设置 key 的值", "setnx key value");
        questionsAndAnswers.put("String类型：增加多对键值对，只有在 key 不存在时设置 key 的值", "msetnx key1 value1 key2 value2");
        questionsAndAnswers.put("String类型：覆盖key的旧值，并返回旧值", "getset key value");
        questionsAndAnswers.put("String类型：将值 value 关联到 key ，并将 key 的过期时间设为 number (以秒为单位)，以毫秒为单位呢",
                "setex key number value & psetex key number value");
        questionsAndAnswers.put("String类型：用 value 覆盖给定 key 所储存的字符串值，从偏移量 offset 开始(包含offset)",
                "setrange key offset value");
        questionsAndAnswers.put("String类型：根据start和end两个数字作为下标截取key对应的值，注意包含start和end，如果end是  -1则代表末尾",
                "getrange key start end");
        questionsAndAnswers.put("String类型：如果 key 已经存在并且是一个字符串，将指定的 value 追加到该 key 原来值（value）的末尾",
                "append key value");
        questionsAndAnswers.put("String类型：返回 key 所储存的字符串值的长度", "strlen key");
        questionsAndAnswers.put("String类型：将 key 所储存的值加上给定的增量值(increment)", "incrby key increment");
        questionsAndAnswers.put("String类型：key 所储存的值减去给定的减量值(decrement)", "decrby key decrement");
// Redis Hash
        questionsAndAnswers.put("Hash类型：向哈希表 key 中添加一个字段 field ，值设为 value", "Hset key field value");
        questionsAndAnswers.put("Hash类型：获取存储在哈希表中一个指定字段的值", "Hget key field");
        questionsAndAnswers.put("Hash类型：向哈希表 key 中添加多个字段 field 和值value", "Hmset key field1 value1 field2 value2");
        questionsAndAnswers.put("Hash类型：获取存储在哈希表中多个指定字段的值", "Hmget key field1 field2");
        questionsAndAnswers.put("Hash类型：只有在字段 field 不存在时，设置哈希表字段的值", "Hsetnx key field value");
        questionsAndAnswers.put("Hash类型：删除一个或多个哈希表字段", "Hdel key field1 field2");
        questionsAndAnswers.put("Hash类型：查看哈希表 key 中，指定的字段是否存在", "Hexists key field");
        questionsAndAnswers.put("Hash类型：获取所有哈希表中的字段", "Hkeys key");
        questionsAndAnswers.put("Hash类型：获取哈希表中所有值", "Hvals key");
        questionsAndAnswers.put("Hash类型：获取在哈希表中指定 key 的所有字段和值", "Hgetall key");
        questionsAndAnswers.put("Hash类型：获取哈希表中字段的数量", "Hlen key");
        questionsAndAnswers.put("Hash类型：为哈希表 key 中的指定字段的整数值加上增量 increment", "Hincrby key field increment");
        questionsAndAnswers.put("Hash类型：为哈希表 key 中的指定字段的浮点数值加上增量 increment", "Hincrbyfloat key field increment");
        // Redis List
        questionsAndAnswers.put("List类型：添加一个或多个值插入到列表头部，列表不存在创建列表进行添加", "Lpush key value1 value2");
        questionsAndAnswers.put("List类型：添加一个或多个值插入到以存在的列表头部，列表不存在则不进行任何操作", "Lpushx key value1 value2");
        questionsAndAnswers.put("List类型：添加一个或多个值插入到列表尾部，列表不存在创建列表进行添加", "Rpush key value1 value2");
        questionsAndAnswers.put("List类型：添加一个或多个值插入到以存在的列表尾部，列表不存在则不进行任何操作", "Rpushx key value1 value2");
        questionsAndAnswers.put("List类型：将值 value 插入到列表 key 当中，位于值 pivot 之前", "Linsert key before pivot value");
        questionsAndAnswers.put("List类型：将值 value 插入到列表 key 当中，位于值 pivot 之后", "Linsert key after pivot value");
        questionsAndAnswers.put("List类型：通过索引下标来替换指定列表元素的值，负数索引下标代表从倒数第几个开始数，索引下标不能超出列表的范围",
                "Lset key index value");
        questionsAndAnswers.put("List类型：用于通过索引下标获取列表中的元素值，负数索引下标代表从倒数第几个开始数", "Lindex key index");
        questionsAndAnswers.put("List类型：返回列表中指定区间内的元素，区间以偏移量 start 和 end 指定，负数索引下标代表从倒数第几个开始数",
                "Lrange key start end");
        questionsAndAnswers.put("List类型：用于返回列表的长度，列表不存在返回0", "Llen key");
        questionsAndAnswers.put("List类型：移出并获取多个列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时时间number(单位秒)或发现可弹出元素为止",
                "Blpop key1 key2 number");
        questionsAndAnswers.put("List类型：移出并获取多个列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时时间number(单位秒)或发现可弹出元素为止",
                "Brpop key1 key2 number");
        questionsAndAnswers.put("List类型：用于移除并返回列表头部number个元素(number必须是正数)，不加number只移除1个元素",
                "Lpop key number");
        questionsAndAnswers.put("List类型：用于移除并返回列表尾部number个元素(number必须是正数)，不加number只移除1个元素",
                "Rpop key number");
        questionsAndAnswers.put("List类型：移除source列表的最后一个元素，并将该元素添加到destination列表的头部并返回",
                "Rpoplpush source destination");
        questionsAndAnswers.put("List类型：移除source列表的最后一个元素，并将该元素添加到destination列表的头部并返回，如果列表没有元素会阻塞列表直到等待超时时间number(单位秒)或发现可弹出元素为止",
                "Brpoplpush source destination number");
        questionsAndAnswers.put("List类型：移除列表中指定区间之外的全部元素，区间以偏移量 start 和 end 指定，负数索引下标代表从倒数第几个开始数",
                "Ltrim key start end");
        questionsAndAnswers.put("List类型：根据参数 number 的值，移除列表中与参数 VALUE 相等的元素", "Lrem key number value");
//  Redis Set
        questionsAndAnswers.put("Set类型：向集合添加一个或多个元素", "Sadd key value1 value2");
        questionsAndAnswers.put("Set类型：判断集合是否存在指定元素", "Sismember key value");
        questionsAndAnswers.put("Set类型：返回集合中的所有的元素， 不存在的集合 key 被视为空集合", "Smembers key");
        questionsAndAnswers.put("Set类型：查看集合元素数量", "Scard key");
        questionsAndAnswers.put("Set类型：将member元素从source集合移动到destination集合", "Smove source destination member");
        questionsAndAnswers.put("Set类型：移除集合中的一个或多个指定的成员元素，不存在的成员元素会被忽略", "Srem key value1 value2");
        questionsAndAnswers.put("Set类型：随机移除指定集合number个元素", "Spop key number");
        questionsAndAnswers.put("Set类型：随机查看指定集合number个元素", "Srandmember key number");

        questionsAndAnswers.put("Set类型：查看只有第一个key存在而其他key中不存在的元素", "Sdiff key1 key2");
        questionsAndAnswers.put("Set类型：查看只有第一个key存在而其他key中不存在的元素，并存入destination集合，如果 destination 集合已经存在，则将其覆盖，destination 可以是 key 本身",
                "Sdiffstore destination key1 key2");
        questionsAndAnswers.put("Set类型：返回给定所有给定集合的交集。 不存在的集合 key 被视为空集。 当给定集合当中有一个空集时，结果也为空集",
                "Sinter key1 key2");
        questionsAndAnswers.put("Set类型：将给定集合之间的交集存储在指定的destination集合中。如果指定的集合已经存在，则将其覆盖",
                "Sinterstore destination key1 key2");
        questionsAndAnswers.put("Set类型：返回多个集合的并集，不存在的集合 key 被视为空集", "Sunion key1 key2");
        questionsAndAnswers.put("Set类型：返回多个集合的并集，并存入destination集合，如果 destination 已经存在，则将其覆盖",
                "Sunionstore destination key1 key2");
        // Redis ZSet
        questionsAndAnswers.put("ZSet类型：向有序集合添加一个或多个score分数和元素，元素以存在就更新分数并从插入新的存储位置",
                "Zadd key score1 value1 score2 value2");
        questionsAndAnswers.put("ZSet类型：查看有序集合元素数量", "Zcard key");
        questionsAndAnswers.put("ZSet类型：计算在有序集合中指定区间分数的成员元素数", "Zcount key min max");
        questionsAndAnswers.put("ZSet类型：有序集合中对指定成员元素的分数加上增量 increment",
                "Zincrby key increment value");
        questionsAndAnswers.put("ZSet类型：计算给定的一个或多个有序集的交集保存到destination有序集合中，其中给定 key 的数量必须以 numkeys 参数指定",
                "Zinterstore destination numkeys key1 key2");
        questionsAndAnswers.put("ZSet类型：计算给定的一个或多个有序集的并集保存到destination有序集合中，其中给定 key 的数量必须以 numkeys 参数指定",
                "Zunionstore destination numkeys key1 key2");
        questionsAndAnswers.put("ZSet类型：成员的位置先按分数值递增(从小到大)来排序，然后查看指定索引下标区间内的成员元素",
                "Zrange key start end");
        questionsAndAnswers.put("ZSet类型：成员的位置先按分数值递增(从大到小)来排序，然后查看指定索引下标区间内的成员元素",
                "Zrevrange key start end");
        questionsAndAnswers.put("ZSet类型：成员的位置先按分数值递增(从小到大)来排序返回有序集合，然后查看指定索引下标区间内的成员元素和分数",
                "Zrange key start end withscores");
        questionsAndAnswers.put("ZSet类型：成员的位置先按分数值递增(从大到小)来排序返回有序集合，然后查看指定索引下标区间内的成员元素和分数",
                "Zrevrange key start end withscores");
        questionsAndAnswers.put("ZSet类型：返回有序集合中指定分数区间(min和max之间)的成员列表，有序集成员按分数值递增(从小到大)次序排列，并支持选择分页返回，偏移量offset，返回数量count",
                "Zrangebyscore key min max Limit offset count");
        questionsAndAnswers.put("ZSet类型：返回有序集合中指定分数区间(min和max之间)的成员列表，有序集成员按分数值递增(从大到小)次序排列，并支持选择分页返回，偏移量offset，返回数量count",
                "Zrevrangebyscore key min max Limit offset count");
        questionsAndAnswers.put("ZSet类型：返回有序集合中指定成员的分数", "Zscore key value");
        questionsAndAnswers.put("ZSet类型：返回有序集合中指定成员的排名(分数从小到大的排名，最小的排名显示是0)",
                "Zrank key value");
        questionsAndAnswers.put("ZSet类型：返回有序集合中指定成员的排名(分数从大到小的排名，最大的排名显示是0)",
                "Zrevrank key value");
        questionsAndAnswers.put("ZSet类型：移除有序集合中的一个或多个成员元素", "Zrem key value1 value2");
        questionsAndAnswers.put("ZSet类型：按照start和end指定的索引区间（包含start和end），移除区间内的全部成员元素",
                "Zremrangebyrank key start end");
        questionsAndAnswers.put("ZSet类型：按照min和max指定的分数区间（包含min和max），移除区间内的全部成员元素",
                "Zremrangebyscore key min max");

//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");
//        questionsAndAnswers.put("", "");

        return questionsAndAnswers;
    }
}
