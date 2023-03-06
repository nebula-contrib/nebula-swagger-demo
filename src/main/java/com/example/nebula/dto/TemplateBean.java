package com.example.nebula.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName= TemplateBean
 */
@Data
public class TemplateBean {

    private String version = "v3";
    private String description = "web console import";
    private Boolean removeTempFiles = false;
    private ClientSettings clientSettings;
    private String logPath;
    private List<File> files;


    @Data
    public static class File {
        private String path;
        private String failDataPath;
        private Integer batchSize = 60;
        private String limit;
        private String inOrder;
        private String type = "csv";
        private CSV csv;
        private Schema schema;
    }

    @Data
    public static class CSV {
        private Boolean withHeader = true;
        private Boolean withLabel = false;
        private String delimiter;
    }

    @Data
    public static class Schema {
        private String type = "vertex";
//        private Vertex vertex;
        private Edge edge;
    }

    @Data
    public static class Edge {
        private String name = "order";
        private Boolean withRanking = false;
//        private List<Prop> props;
//        private SrcDst srcVID;
//        private SrcDst dstVID;
//        private Integer rank;
    }


//    @Data
//    public static class SrcDst {
//        private Integer index = 1;
//        private String function = "";
//        private String type = "string";
//        private String prefix = "";
//    }


//    @Data
//    public static class Vertex {
//        private Vid vid;
//        private List<Tag> tags;
//    }


//    @Data
//    public static class Vid {
//        private Integer index = 0;
//        private String function;
//        private String type = "string";
//        private String prefix;
//    }


//    @Data
//    public static class Tag {
//        private String name = "item";
//        private List<Prop> props;
//    }

//    @Data
//    public static class Prop {
//        private String name = "id_single_item";
//        private String type = "string";
//        private Integer index = 0;
//    }


    @Data
    public static class ClientSettings {
        private Integer retry = 3;
        private Integer concurrency = 10;
        private Integer channelBufferSize = 128;
        private String space = "dataImport";
        private String postStart;
        private String preStop;
        private Connection connection;
    }

    @Data
    public static class Connection {
        private String user = "root";
        private String password = "nebula";
        private String address = "127.0.0.1:9669";
    }

}
