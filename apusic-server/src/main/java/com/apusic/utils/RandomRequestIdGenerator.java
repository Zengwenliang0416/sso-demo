package com.apusic.utils;

import java.util.UUID;

/**
 * @author 曾文亮
 * @version 1.0.0
 * @email wenliang_zeng416@163.com
 * @date 2023年11月16日 09:46:42
 * @packageName com.apusic.utils
 * @className randomRequestIdGenerator
 * @describe TODO
 */
public class RandomRequestIdGenerator {
    public static String generateRandomRequestId() {
        UUID uuid = UUID.randomUUID();
        String randomRequestId = uuid.toString();
        // 去掉生成的UUID中的"-"
        randomRequestId = randomRequestId.replace("-", "");
        // 将字符串按照指定格式插入"-"
        String RequestId = String.format(
                "%s-%s-%s-%s-%s",
                randomRequestId.substring(0, 8),
                randomRequestId.substring(8, 12),
                randomRequestId.substring(12, 16),
                randomRequestId.substring(16, 20),
                randomRequestId.substring(20)
        );
        return RequestId;
    }
}
