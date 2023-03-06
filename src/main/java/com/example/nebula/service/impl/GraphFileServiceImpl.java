package com.example.nebula.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.csv.*;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ZipUtil;
import com.example.nebula.constant.AttributeEnum;
import com.example.nebula.dto.*;
import com.example.nebula.dto.graph.GraphShowAttribute;
import com.example.nebula.dto.graph.GraphShowInfo;
import com.example.nebula.entity.GraphFile;
import com.example.nebula.exception.GraphExecuteException;
import com.example.nebula.service.AttributeService;
import com.example.nebula.service.GraphCommonService;
import com.example.nebula.service.GraphFileService;
import com.example.nebula.util.NebulaUtil;
import com.example.nebula.vo.AttributeVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * (GraphFile)表服务实现类
 *
 * @author makejava
 * @since 2022-08-23 09:09:27
 */
@Service("graphFileService")
@Slf4j
public class GraphFileServiceImpl implements GraphFileService {

    @Value("${path.uploadFilePath}")
    private String filePath;

    @Value("${path.importerPath}")
    private String importerPath;

    private static String demoName = "示例导入.zip";
    private static String VID = ":VID(string)";
    private static String SRC_VID = ":SRC_VID(string)";
    private static String DST_VID = ":DST_VID(string)";

    /**
     * 上传文件
     * 仅仅记录文件位置,其中解析仅仅是为了预览数据
     * @param multipartFile 文件
     * @return 实例对象
     */
    @Override
    @Transactional
    public GraphFile uploadFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new GraphExecuteException("文件为空");
        }

        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!suffix.equalsIgnoreCase("csv")) {
            throw new GraphExecuteException("文件格式不支持");
        }

        Integer userId = Integer.parseInt("1");
        String uploadFilePath = filePath + "/" + userId;
        //判断路径是否存在，不存在则创建
        makeDir(uploadFilePath);

        File file = new File(uploadFilePath, fileName);
        try {
            multipartFile.transferTo(file);
            //FileUtil.convertCharset(file, CharsetUtil.defaultCharset(), CharsetUtil.CHARSET_UTF_8);
        } catch (IOException e) {
            throw new GraphExecuteException("文件读写异常");
        }


        GraphFile graphFile = new GraphFile();
        graphFile.setUserId(userId);
        graphFile.setFileName(fileName);
        graphFile.setFilePath(uploadFilePath + "/" + fileName);
        graphFile.setFileSize(file.length() + "B");
        graphFile.setCreatedTime(DateUtil.date());

        //GraphFile result = this.graphFileDao.queryByFileName(fileName, userId);
        ////判断是否有同名文件
        //if (Objects.isNull(result)) {
        //    //若没有，插入数据
        //    this.graphFileDao.insert(graphFile);
        //} else {
        //    //若有，更新
        //    graphFile.setId(result.getId());
        //    this.graphFileDao.update(graphFile);
        //    List<Integer> ids = new ArrayList<>();
        //    ids.add(result.getId());
        //    this.graphFilePreviewService.deleteByFileIds(ids);
        //}

        //从文件中读取CSV数据
        CsvReader reader = CsvUtil.getReader();
        CsvData data = reader.read(file, StandardCharsets.UTF_8);
        List<CsvRow> rows = data.getRows();
        //List<GraphFilePreview> list = new ArrayList<>();
        //int min = Math.min(rows.size(), 5);
        //for (int i = 0; i < min; i++) {
        //    GraphFilePreview graphFilePreview = new GraphFilePreview(graphFile.getId(),
        //        JSONUtil.toJsonStr(rows.get(i).getRawList()), DateUtil.date());
        //    list.add(graphFilePreview);
        //}
        //this.graphFilePreviewService.insertBatch(list);
        return graphFile;
    }

    @SneakyThrows
    @Override
    public boolean importFile(ImportBean importBean) {

        long start = System.currentTimeMillis();
        String randomString = RandomUtil.randomString(6);
        String fileName = String.format("%s-%s", importBean.getSpace(), randomString);
        //客户端配置
        TemplateBean templateBean = new TemplateBean();
        String logPath = filePath + "/logFile";
        makeDir(logPath);
        String logFile = String.format("%s/%s.log", logPath, fileName);
        templateBean.setLogPath(logFile);
        TemplateBean.ClientSettings clientSettings = new TemplateBean.ClientSettings();
        clientSettings.setConnection(new TemplateBean.Connection());
        clientSettings.setSpace(importBean.getSpace());
        templateBean.setClientSettings(clientSettings);
        //文件配置
        List<TemplateBean.File> fileList = new ArrayList<>();
        // 点
        getVertex(importBean, fileList);
        //边
        getEdge(importBean, fileList);
        templateBean.setFiles(fileList);
        String yamlFilePath = filePath + "/yamlFile";
        makeDir(yamlFilePath);
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(dumperOptions);
        String yamlFile = String.format("%s/%s.yaml", yamlFilePath, fileName);
        yaml.dump(templateBean, new FileWriter(yamlFile));
        Process exec = Runtime.getRuntime().exec("./nebula-importer --config " + yamlFile, null, new File(importerPath));

        if (exec.waitFor() < 0) {// 等待执行完成
            return false;
        }
        long end = System.currentTimeMillis();

        if (!FileUtil.exist(new File(logFile))) {
            return false;
        }
        //GraphLog graphLog = new GraphLog();
        //Integer userId = Integer.parseInt(String.valueOf(StpUtil.getLoginId()));
        //graphLog.setUserId(userId);
        //graphLog.setSpaceName(importBean.getSpace());
        //graphLog.setImportTime((end - start) + "ms");
        // 执行日志内容
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile), StandardCharsets.UTF_8));
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = br.readLine()) != null) {
            content.append(line).append("\n");
        }
        //graphLog.setContent(content.toString());
        //graphLog.setTask("task-" + randomString);
        //graphLog.setCreateTime(DateUtil.date());
        //this.graphLogService.insert(graphLog);

        return true;
    }

    @Autowired
    AttributeService attributeService;
    @Autowired
    GraphCommonService graphCommonService;

    /**
     * 模板下载
     *
     * @return void
     * @Param [space]
     **/
    @Override
    public void template(String space, HttpServletResponse response) {

        Map<String, List<String>> tagMap = MapUtil.newHashMap();
        Map<String, List<String>> edgeMap = MapUtil.newHashMap();

        // 查询所有tag  edges
        GraphShowAttribute graphShowAttribute = new GraphShowAttribute(space, AttributeEnum.TAGS.name());
        List<AttributeVo> tagList = graphCommonService.executeJson(NebulaUtil.showAttributes(graphShowAttribute), AttributeVo.class);

        graphShowAttribute.setAttribute(AttributeEnum.EDGES.name());
        List<AttributeVo> edgeList = graphCommonService.executeJson(NebulaUtil.showAttributes(graphShowAttribute), AttributeVo.class);

        if (CollectionUtil.isEmpty(tagList.get(0).getData()) && CollectionUtil.isEmpty(edgeList.get(0).getData())) {
            throw new GraphExecuteException("请先添加 模式设计 数据");
        }

        // 查询每个属性的子属性
        tagList.get(0).getData().stream().forEach(attributeVo -> {
            attributeVo.getRow().forEach(row -> {
                GraphShowInfo graphShowInfo = new GraphShowInfo();
                graphShowInfo.setSpace(space);
                graphShowInfo.setAttributeName(row);
                graphShowInfo.setAttribute("tag");
                List<AttributeVo> attributeVoList = graphCommonService.executeJson(NebulaUtil.showAttributeInfo(graphShowInfo), AttributeVo.class);
                List<String> rows = CollectionUtil.newArrayList();
                //attributeVoList.forEach(attributeVo1 -> attributeVo1.getData().forEach(dataBean -> rows.add(dataBean.getRow().get(0))));
                rows.add(VID);
                attributeVoList.forEach(attributeVo1 -> attributeVo1.getData().forEach(dataBean -> {
                    String type = "int64".equalsIgnoreCase(dataBean.getRow().get(1)) ? "int" : dataBean.getRow().get(1);
                    rows.add(row + "." + dataBean.getRow().get(0) + ":" + type);
                }));
                tagMap.put(row, rows);
            });
        });

        edgeList.get(0).getData().stream().forEach(attributeVo -> {
            attributeVo.getRow().forEach(row -> {
                GraphShowInfo graphShowInfo = new GraphShowInfo();
                graphShowInfo.setSpace(space);
                graphShowInfo.setAttributeName(row);
                graphShowInfo.setAttribute("edge");
                List<AttributeVo> attributeVoList = graphCommonService.executeJson(NebulaUtil.showAttributeInfo(graphShowInfo), AttributeVo.class);
                List<String> rows = CollectionUtil.newArrayList();
                //rows.add("srcId");
                //rows.add("dstId");
                //attributeVoList.forEach(attributeVo1 -> attributeVo1.getData().forEach(dataBean -> rows.add(dataBean.getRow().get(0))));
                rows.add(SRC_VID);
                rows.add(DST_VID);
                attributeVoList.forEach(attributeVo1 -> attributeVo1.getData().forEach(dataBean -> {
                    String type = "int64".equalsIgnoreCase(dataBean.getRow().get(1)) ? "int" : dataBean.getRow().get(1);
                    rows.add(row + "." + dataBean.getRow().get(0) + ":" + type);
                }));
                edgeMap.put(row, rows);
            });
        });

        // 生成模板.csv
        String randomNumbers = RandomUtil.randomNumbers(6);
        String path = filePath + "/zipFile/" + randomNumbers;
        tagMap.forEach((key, value) -> {
            String fileName = "实体-" + key;
            String pathname = path + "/" + fileName + ".csv";
            CsvWriter csvWriter = CsvUtil.getWriter(new File(pathname), CharsetUtil.CHARSET_UTF_8);
            csvWriter.write(Convert.toStrArray(value));
            csvWriter.flush();
            csvWriter.close();
        });
        edgeMap.forEach((key, value) -> {
            String fileName = "关系-" + key;
            String pathname = path + "/" + fileName + ".csv";
            CsvWriter csvWriter = CsvUtil.getWriter(new File(pathname), CharsetUtil.CHARSET_UTF_8);
            csvWriter.write(Convert.toStrArray(value));
            csvWriter.flush();
            csvWriter.close();
        });

        // 压缩包zip
        String zipPath = path + demoName;
        ZipUtil.zip(path, zipPath, CharsetUtil.CHARSET_UTF_8, false);
        FileUtil.del(path);// 删除csv源文件

        //返回压缩包
        try {
            BufferedInputStream bis;
            OutputStream out = response.getOutputStream();
            response.setContentType("text/html; charset=UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(randomNumbers + demoName, CharsetUtil.UTF_8))));
            bis = new BufferedInputStream(new FileInputStream(zipPath));
            //定义byte，长度就是要转成zip文件的byte长度，避免浪费资源
            byte[] buffer = new byte[bis.available()];
            bis.read(buffer);
            out.flush();
            out.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void makeDir(String logPath) {
        File dir = new File(logPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void getVertex(ImportBean importBean, List<TemplateBean.File> fileList) {
        for (VertexCombo vertexCombo : importBean.getVertices()) {
            TemplateBean.File file = new TemplateBean.File();
            //GraphFile graphFile = this.graphFileDao.queryById(vertexCombo.getFileId());
            String failDataPath = filePath + "/failDataFile";
            makeDir(failDataPath);
            String randomString = RandomUtil.randomString(6);
            file.setFailDataPath(String.format("%s/%s-%s", failDataPath, randomString, "DEMO"));
            //file.setFailDataPath(String.format("%s/%s-%s", failDataPath, randomString, graphFile.getFileName()));
            //file.setPath(graphFile.getFilePath());
            file.setPath("DEMO");
            file.setCsv(new TemplateBean.CSV());

            TemplateBean.Schema schema = new TemplateBean.Schema();
            schema.setType("vertex");
            file.setSchema(schema);
            fileList.add(file);
        }
    }

    private void getEdge(ImportBean importBean, List<TemplateBean.File> fileList) {
        for (EdgeCombo edgeCombo : importBean.getEdges()) {
            TemplateBean.File file = new TemplateBean.File();
            //GraphFile graphFile = this.graphFileDao.queryById(edgeCombo.getFileId());
            String failDataPath = filePath + "/failDataFile";
            makeDir(failDataPath);
            String randomString = RandomUtil.randomString(6);
            file.setFailDataPath(String.format("%s/%s-%s", failDataPath, randomString, "demo"));
            //file.setFailDataPath(String.format("%s/%s-%s", failDataPath, randomString, graphFile.getFileName()));
            //file.setPath(graphFile.getFilePath());
            file.setPath("demo");
            file.setCsv(new TemplateBean.CSV());

            TemplateBean.Schema schema = new TemplateBean.Schema();
            schema.setType("edge");

            TemplateBean.Edge edge = new TemplateBean.Edge();
            EdgeElement edgeElement = edgeCombo.getEdgeElement();
            edge.setName(edgeElement.getElementName());
//            TemplateBean.SrcDst src = new TemplateBean.SrcDst();
//            src.setIndex(edgeElement.getSrcId());
//            edge.setSrcVID(src);
//            TemplateBean.SrcDst dst = new TemplateBean.SrcDst();
//            dst.setIndex(edgeElement.getDstId());
//            edge.setDstVID(dst);
////            edge.setRank(edgeElement.getRank());
//            edge.setProps(getPropList(edgeElement.getProperties()));
            schema.setEdge(edge);
            file.setSchema(schema);
            fileList.add(file);
        }
    }

//    private List<TemplateBean.Prop> getPropList(List<Property> properties) {
//        List<TemplateBean.Prop> propList = new ArrayList<>();
//        for (Property property : properties) {
//            TemplateBean.Prop prop = new TemplateBean.Prop();
//            prop.setName(property.getPropName());
//            prop.setIndex(property.getCsvIndex());
//            String type = "int64".equals(property.getType()) ? "int" : property.getType();
//            prop.setType(type);
//            propList.add(prop);
//        }
//        return propList;
//    }
}
