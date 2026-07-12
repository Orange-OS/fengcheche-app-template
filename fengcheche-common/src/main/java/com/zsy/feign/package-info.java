/**
 * Feign 客户端接口放在该包或其子包下，业务服务启动类会统一扫描 com.zsy。
 *
 * 示例：
 * @FeignClient(name = "fengcheche-system-service", contextId = "systemInternalClient")
 * interface SystemInternalClient {
 *     @GetMapping("/fengcheche/internal/system/user/{userId}")
 *     Result<UserVO> getUser(@PathVariable("userId") Long userId);
 * }
 */
package com.zsy.feign;
