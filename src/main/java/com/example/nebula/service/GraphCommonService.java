package com.example.nebula.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.nebula.exception.GraphExecuteException;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图数据库语句执行
 */
@Slf4j
@Service
public class GraphCommonService {

    @Autowired
    Session session;

    public List executeJson(String gql, Class<?> voClass) {
        try {
            log.info("执行 executeJson 方法，gql={}", gql);
            final String data = session.executeJson(gql);
            final JSONObject jsonObject = JSON.parseObject(data);
            // 查询语句异常分析, 根据 json 返回结果解析 error 节点信息
            final JSONObject error0 = jsonObject.getJSONArray("errors").getJSONObject(0);
            final Integer code = error0.getInteger("code");
            if (code != 0) {
                throw new GraphExecuteException("execute gql error, gql: " + gql +
                    " ,code:" + code + ", message:" + error0.getString("message"));
            }
            //return GraphVo.builder()
            //    .execGql(gql)
            //    .results(JSONUtil.toList(JSONUtil.parseArray(jsonObject.get("results")), voClass))
            //    .build();
            JSONArray results = JSONUtil.parseArray(jsonObject.get("results"));
            log.info("返回结果: {}", results);
            return JSONUtil.toList(results, voClass);
        } catch (IOErrorException e) {
            log.error("executeJson ql[{}] error, msg [{}]", gql, e.getMessage());
            throw new GraphExecuteException("execute gql error, gql: " + gql, e);
        }
    }

}
