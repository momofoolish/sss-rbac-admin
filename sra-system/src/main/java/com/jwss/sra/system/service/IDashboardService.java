package com.jwss.sra.system.service;

import com.jwss.sra.system.vo.SystemInfoVO;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * 仪表板服务
 * @date 2022-1-31 18:12:12
 * @author jwss
 */
public interface IDashboardService {
    /**
     * 获取系统统计数量
     * @return 数量集合
     */
    List<Map<String, Object>> getCount();

    /**
     * 获取系统信息
     * @return 系统信息
     */
    SystemInfoVO getSystemInfo() throws UnknownHostException;
}