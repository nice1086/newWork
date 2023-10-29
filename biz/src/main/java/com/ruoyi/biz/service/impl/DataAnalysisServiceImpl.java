package com.ruoyi.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.biz.constant.TimeRangeEnum;
import com.ruoyi.biz.domain.BO.EntranceCountSearchBO;
import com.ruoyi.biz.domain.BO.TimeRangeBO;
import com.ruoyi.biz.domain.PO.Company;
import com.ruoyi.biz.domain.VO.ColumnarDataVO;
import com.ruoyi.biz.domain.VO.CompanyDataAnalysisVO;
import com.ruoyi.biz.domain.VO.ResidentCompanyVO;
import com.ruoyi.biz.mapper.DataAnalysisMapper;
import com.ruoyi.biz.service.IDataAnalysisService;
import com.ruoyi.biz.service.IResidentCompanyService;
import com.ruoyi.biz.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 数据统计服务类
 * @author MR.ZHAO
 * @since 2023/7/20
 */
@Service
public class DataAnalysisServiceImpl implements IDataAnalysisService {

    @Autowired
    private DataAnalysisMapper dataAnalysisMapper;

    @Autowired
    private IResidentCompanyService residentCompanyService;

    /**
     * 获取岗亭名称
     *
     * @return 岗亭名称列表
     */
    @Override
    public List<String> getEntranceName() {
        List<String> entranceNames = dataAnalysisMapper.selectEntranceName();
        entranceNames.sort(Comparator.comparingInt(this::chToNum));
        return entranceNames;
    }

    /**
     * 获取岗亭车辆统计（岗亭名称）
     *
     * @return 获取岗亭车辆统计（岗亭名称）
     */
    @Override
    public ColumnarDataVO getEntranceCountName(EntranceCountSearchBO searchBO) {
        TimeRangeBO timeRange = TimeUtil.getTimeRange(searchBO.getTimeRange(), searchBO.getStartTime(), searchBO.getEndTime());
        searchBO.setStartTime(timeRange.getStartTime());
        searchBO.setEndTime(timeRange.getEndTime());
        List<Map<String, Object>> mapDataList =  dataAnalysisMapper.selectEntranceCountName(searchBO);
        mapDataList.sort(Comparator.comparingInt(o -> chToNum(o.get("name").toString())));
        return mapToEntranceCount(mapDataList);
    }

    /**
     * 获取岗亭车辆统计
     *
     * @return 岗亭车辆统计结果
     */
    @Override
    public ColumnarDataVO getEntranceCount(EntranceCountSearchBO searchBO) {
        TimeRangeBO timeRange = TimeUtil.getTimeRange(searchBO.getTimeRange(), searchBO.getStartTime(), searchBO.getEndTime());
        searchBO.setStartTime(timeRange.getStartTime());
        searchBO.setEndTime(timeRange.getEndTime());
        List<Map<String, Object>> mapDataList;
        Map<String, Map<String, Object>> nameMap = new HashMap<>(36);
        List<Map<String, Object>> fillMapData = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate beginDate = searchBO.getStartTime().toLocalDate();
        LocalDate endDate = searchBO.getEndTime().toLocalDate();
        if (searchBO.getTimeRange() == TimeRangeEnum.DAY || beginDate.equals(endDate)) {
            // 查询数据（小时统计）
            mapDataList = dataAnalysisMapper.selectEntranceCountHour(searchBO);
            // 填充0
            mapDataList.forEach(v -> nameMap.put(v.get("name").toString(), v));
            for (int hour = 0; hour <= 23; hour++) {
                String hourStr = String.format("%02d", hour);
                if (nameMap.containsKey(hourStr)) {
                    fillMapData.add(nameMap.get(hourStr));
                } else {
                    HashMap<String, Object> zeroData = new HashMap<>(10);
                    zeroData.put("name", hourStr);
                    fillMapData.add(zeroData);
                }
            }
        } else {
            // 查询数据（日统计）
            mapDataList = dataAnalysisMapper.selectEntranceCountDay(searchBO);
            // 填充0
            mapDataList.forEach(v -> nameMap.put(v.get("name").toString(), v));
            while (beginDate.isBefore(endDate) || beginDate.equals(endDate)) {
                String dayStr = dtf.format(beginDate);
                if (nameMap.containsKey(dayStr)) {
                    fillMapData.add(nameMap.get(dayStr));
                } else {
                    HashMap<String, Object> zeroData = new HashMap<>(10);
                    zeroData.put("name", dayStr);
                    fillMapData.add(zeroData);
                }
                beginDate = beginDate.plusDays(1);
            }
        }
        return mapToEntranceCount(fillMapData);
    }

    /**
     * 获取企业车辆统计
     *
     * @return 企业车辆统计结果
     */
    @Override
    public List<CompanyDataAnalysisVO> getCompanyCount() {
        List<ResidentCompanyVO> residentCompanyVOList = residentCompanyService.selectCompanyList(new Company());
        Long vehicleCount = getVehicleCount(null, true);
        List<CompanyDataAnalysisVO> companyDataAnalysisVOList = new ArrayList<>();
        residentCompanyVOList.forEach(v -> {
            List<Long> transportIds = residentCompanyService.selectTransport(v.getCompanyId());
            transportIds.add(v.getCompanyId());
            List<String> carNos = residentCompanyService.selectCarNo(transportIds);
            CompanyDataAnalysisVO companyDataAnalysisVO = new CompanyDataAnalysisVO();
            companyDataAnalysisVO.setCompanyId(v.getCompanyId());
            companyDataAnalysisVO.setCompanyName(v.getCompanyName());
            if (carNos != null && carNos.size() != 0) {
                Map<String, Object> mapData = dataAnalysisMapper.selectCompanyCount(carNos);
                Long sumCount = Long.valueOf(mapData.getOrDefault("sum_count", "0").toString());
                companyDataAnalysisVO.setInCount(Long.valueOf(mapData.getOrDefault("in_count", "0").toString()));
                companyDataAnalysisVO.setOutCount(Long.valueOf(mapData.getOrDefault("out_count", "0").toString()));
                companyDataAnalysisVO.setSumCount(sumCount);
                companyDataAnalysisVO.setScale(sumCount.doubleValue() / vehicleCount * 100);
            } else {
                companyDataAnalysisVO.setInCount(0L);
                companyDataAnalysisVO.setOutCount(0L);
                companyDataAnalysisVO.setSumCount(0L);
                companyDataAnalysisVO.setScale(0.0);
            }
            companyDataAnalysisVOList.add(companyDataAnalysisVO);
        });
        companyDataAnalysisVOList.sort((o1, o2) -> (int) (o2.getSumCount() - o1.getSumCount()));
        return companyDataAnalysisVOList;
    }

    /**
     * 统计车辆出入数量
     * @param searchBO 统计范围
     * @param all 是否全部
     * @return 车辆出入数量
     */
    @Override
    public Long getVehicleCount(EntranceCountSearchBO searchBO, boolean all) {
        return dataAnalysisMapper.getVehicleCount(searchBO, all);
    }


    /**
     * 字典数据转岗亭车辆统计数据
     * @param mapDataList 字典数据
     * @return 岗亭车辆统计数据
     */
    private ColumnarDataVO mapToEntranceCount(List<Map<String, Object>> mapDataList) {
        List<Object> nameData = new ArrayList<>();
        List<Long> inCountData = new ArrayList<>();
        List<Long> outCountData = new ArrayList<>();
        List<Long> countData = new ArrayList<>();
        for (Map<String, Object> map : mapDataList) {
            nameData.add(map.get("name"));
            inCountData.add(Long.valueOf(map.getOrDefault("in_count", "0").toString()));
            outCountData.add(Long.valueOf(map.getOrDefault("out_count", "0").toString()));
            countData.add(Long.valueOf(map.getOrDefault("sum_count", "0").toString()));
        }
        return new ColumnarDataVO(nameData, inCountData, outCountData, countData);
    }

    /**
     * 将岗亭编号转为阿拉伯数字
     * @param entranceName 岗亭名称
     * @return 岗亭阿拉伯数字
     */
    private int chToNum(String entranceName) {
        int result = 0;
        if (StrUtil.isEmpty(entranceName)) {
            return result;
        }
        Map<Character, Integer> numMap = new HashMap<>();
        numMap.put('一', 1);
        numMap.put('二', 2);
        numMap.put('三', 3);
        numMap.put('四', 4);
        numMap.put('五', 5);
        numMap.put('六', 6);
        numMap.put('七', 7);
        numMap.put('八', 8);
        numMap.put('九', 9);
        numMap.put('十', 10);
        char cnNum = entranceName.charAt(0);
        if (!numMap.containsKey(cnNum)) {
            return result;
        }
        if (cnNum == '十') {
            result += 10;
            if (entranceName.length() >= 2) {
                cnNum = entranceName.charAt(1);
                if (numMap.containsKey(cnNum)) {
                    result += numMap.get(cnNum);
                }
            }
        } else {
            result = numMap.get(cnNum);
        }
        return result;
    }

}
