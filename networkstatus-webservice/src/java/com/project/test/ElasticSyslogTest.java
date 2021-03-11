/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.test;

//import com.project.core.models.entities.geolocation.GeoLocation;
import com.vividsolutions.jts.geom.Coordinate;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
//import java.util.HashMap;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import static org.elasticsearch.common.xcontent.XContentFactory.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.common.geo.builders.ShapeBuilders;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilder;

/**
 *
 * @author Samrit
 */
public class ElasticSyslogTest {

    public static void main(String[] args) {
        try {
//            insertSectionGEO();
//            readSection();
//            deleteSection();
//            updateSection();
//            bluckProcessingSection();
bluckProcessingSectionAdvance();
//            searchSection();
//            geoshapeSection();
//        testEPSChartSection();
        } catch (Exception e) {
            System.out.println("s");
            e.printStackTrace();
        }
    }

    public static void insertSectionGEO() throws Exception {

        //Builder Format
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .startObject("pin")
                .field("name", "kimchy C")
                .field("location", new GeoPoint(12, 23))
                .endObject()
                .endObject();

        String host = "127.0.0.1";

        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));

        IndexResponse response = client.prepareIndex("my_locations_e", "location", "4")
                .setSource(builder).get();

        // Index name
        String _index = response.getIndex();
// Type name
        String _type = response.getType();
// Document ID (generated or not)
        String _id = response.getId();
// Version (if it's the first time you index this document, you will get: 1)
        long _version = response.getVersion();
// status has stored current instance statement.
        RestStatus status = response.status();

        System.out.println("index" + _index);
        System.out.println("_type" + _type);
        System.out.println("_id" + _id);
        System.out.println("_version" + _version);
        System.out.println("status" + status.toString());

    }

    public static void insertSection() throws Exception {

        // String format
        String json_str = "{"
                + "\"user\":\"kimchy A\","
                + "\"postDate\":\"2013-01-30\","
                + "\"message\":\"trying out Elasticsearch A\""
                + "}";

        // Map Format
        Map<String, Object> json_map = new HashMap<String, Object>();
        json_map.put("user", "kimchy B");
        json_map.put("postDate", new Date());
        json_map.put("message", "trying out Elasticsearch");

        //Builder Format
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("user", "kimchy C")
                .field("postDate", new Date())
                .field("message", "trying out Elasticsearch")
                .endObject();

        //Builder String Format
        XContentBuilder builderStr = jsonBuilder()
                .startObject()
                .field("user", "kimchy D")
                .field("postDate", new Date())
                .field("message", "trying out Elasticsearch")
                .endObject();
        String json_builder_str = builderStr.string();

        String host = "127.0.0.1";

        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));

        IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
                .setSource(json_str).get();

        response = client.prepareIndex("twitter", "tweet", "2")
                .setSource(json_map).get();

        response = client.prepareIndex("twitter", "tweet", "3")
                .setSource(builder).get();

        response = client.prepareIndex("twitter", "tweet", "4")
                .setSource(json_builder_str).get();

        // Index name
        String _index = response.getIndex();
// Type name
        String _type = response.getType();
// Document ID (generated or not)
        String _id = response.getId();
// Version (if it's the first time you index this document, you will get: 1)
        long _version = response.getVersion();
// status has stored current instance statement.
        RestStatus status = response.status();

        System.out.println("index" + _index);
        System.out.println("_type" + _type);
        System.out.println("_id" + _id);
        System.out.println("_version" + _version);
        System.out.println("status" + status.toString());

    }

    public static void readSection() throws Exception {

        String host = "127.0.0.1";

        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));

        GetResponse response = client.prepareGet("twitter", "tweet", "3").get();

        // Index name
        String _index = response.getIndex();
// Type name
        String _type = response.getType();
// Document ID (generated or not)
        String _id = response.getId();
// Version (if it's the first time you index this document, you will get: 1)
        long _version = response.getVersion();
// status has stored current instance statement.

        System.out.println("index : " + _index);
        System.out.println("_type : " + _type);
        System.out.println("_id : " + _id);
        System.out.println("_version : " + _version);

        Map<String, Object> results = response.getSource();
        System.out.println("User : " + results.get("user"));
    }

    public static void deleteSection() throws Exception {

        String host = "127.0.0.1";

        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));

        DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").get();

        // Index name
        String _index = response.getIndex();
// Type name
        String _type = response.getType();
// Document ID (generated or not)
        String _id = response.getId();
// Version (if it's the first time you index this document, you will get: 1)
        long _version = response.getVersion();
// status has stored current instance statement.

        System.out.println("index : " + _index);
        System.out.println("_type : " + _type);
        System.out.println("_id : " + _id);
        System.out.println("_version : " + _version);

    }

    public static void updateSection() throws Exception {

        String host = "127.0.0.1";

        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));

//        DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").get();
        IndexRequest indexRequest = new IndexRequest("index", "type", "1")
                .source(jsonBuilder()
                        .startObject()
                        .field("name", "Joe Smith")
                        .field("gender", "male")
                        .endObject());

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("index");
        updateRequest.type("type");
        updateRequest.id("1");
        updateRequest.doc(jsonBuilder()
                .startObject()
                .field("gender", "male")
                .endObject())
                .upsert(indexRequest);
        client.update(updateRequest).get();
    }

    public static void bluckProcessingSection() throws Exception {

        String host = "127.0.0.1";

        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));

//        DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").get();
        IndexRequest indexRequest = new IndexRequest("index", "type", "1")
                .source(jsonBuilder()
                        .startObject()
                        .field("name", "Joe Smith")
                        .field("gender", "male")
                        .endObject());

        BulkRequestBuilder bulkRequest = client.prepareBulk();

// either use client#prepare, or use Requests# to directly build index/delete requests
        bulkRequest.add(client.prepareIndex("twitterbluck", "tweet", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
        );

        bulkRequest.add(client.prepareIndex("twitterbluck", "tweet", "2")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post")
                        .endObject()
                )
        );

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
        }
    }

    public static void bluckProcessingSectionAdvance() throws Exception {

        String host = "127.0.0.1";

        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));

        BulkProcessor bulkProcessor = BulkProcessor.builder(
                client,
                new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId,
                    BulkRequest request) {
            }

            @Override
            public void afterBulk(long executionId,
                    BulkRequest request,
                    BulkResponse response) {
                System.out.println("__________)))))))))))))))))DDDDDDDDDDDDDDDD");
            }

            @Override
            public void afterBulk(long executionId,
                    BulkRequest request,
                    Throwable failure) {
                  System.out.println("__________)))))))))))))))))DDDDDDDDDDDDDDDD");
            }
        })
                .setBulkActions(10000)
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(1)
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();

// Add your requests
        Map<String, Object> json_map = new HashMap<String, Object>();
        json_map.put("user", "kimchy A");
        json_map.put("postDate", new Date());
        json_map.put("message", "trying out Elasticsearch");

        Map<String, Object> json_map2 = new HashMap<String, Object>();
        json_map2.put("user", "kimchy B");
        json_map2.put("postDate", new Date());
        json_map2.put("message", "trying out Elasticsearch");

        bulkProcessor.add(new IndexRequest("twitterbpd", "tweet", "1").source(json_map));
        bulkProcessor.add(new IndexRequest("twitterbpd", "tweet", "2").source(json_map2));
        bulkProcessor.add(new DeleteRequest("twitterbp", "tweet", "155"));

// Flush any remaining requests
        bulkProcessor.flush();

// Or close the bulkProcessor if you don't need it anymore
        bulkProcessor.close();

// Refresh your indices
        client.admin().indices().prepareRefresh().get();

// Now you can start searching!
        client.prepareSearch().get();

    }

    public static void searchSection() throws Exception {

        String host = "127.0.0.1";

        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));

        SearchResponse response = client.prepareSearch("twitter", "index")
                .setTypes("tweet", "type")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchPhraseQuery("user", "kimchy A")) // Query
                //                .setQuery(QueryBuilders.matchAllQuery())
                //                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18)) // Filter

                .setFrom(0).setSize(60).setExplain(true)
                .get();

        System.out.println("++++++++++++++++++++++++++Total No Of Doucument Found : " + response.getHits().getTotalHits());

        for (SearchHit hit : response.getHits()) {

            System.out.println("User :" + (String) hit.getSource().get("user"));

            System.out.println("");

        }

    }

    public static void geoshapeSection() throws Exception {

        String host = "127.0.0.1";

        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));

        QueryBuilder qb = QueryBuilders.geoDistanceQuery("pin.location")
                .point(40, -70)
                .distance(200, DistanceUnit.KILOMETERS);

        SearchResponse response = client.prepareSearch("my_locations_a")
                .setTypes("location")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(qb) // Query
                //                .setQuery(QueryBuilders.matchAllQuery())
                //                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18)) // Filter

                .setFrom(0).setSize(60).setExplain(true)
                .get();

        System.out.println("++++++++++++++++++++++++++Total No Of Doucument Found : " + response.getHits().getTotalHits());

        for (SearchHit hit : response.getHits()) {

            System.out.println("Fetched Data :" + (String) hit.getSource().get("pin").toString());

            Map pinMapObj = (HashMap) hit.getSource().get("pin");

            System.out.println(pinMapObj.get("name"));

            Map geoLocationObj = (HashMap) pinMapObj.get("location");
            System.out.println("Latitude :" + geoLocationObj.get("lat"));
            System.out.println("Longtitue :" + geoLocationObj.get("lon"));

            System.out.println("");

        }

    }

    public static void backupSection() throws Exception {

        String host = "127.0.0.1";

        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));

        GetResponse response = client.prepareGet("twitter", "tweet", "3").get();

        // Index name
        String _index = response.getIndex();
// Type name
        String _type = response.getType();
// Document ID (generated or not)
        String _id = response.getId();
// Version (if it's the first time you index this document, you will get: 1)
        long _version = response.getVersion();
// status has stored current instance statement.

        System.out.println("index : " + _index);
        System.out.println("_type : " + _type);
        System.out.println("_id : " + _id);
        System.out.println("_version : " + _version);

        Map<String, Object> results = response.getSource();
        System.out.println("User : " + results.get("user"));

//        SearchResponse response = client.prepareSearch("systemevents")
//                .setTypes("syslog")
//                //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                .setQuery(
//                        QueryBuilders.filteredQuery(
//                                !searchText.trim().equals("") ? QueryBuilders.matchQuery("_all", searchText) : QueryBuilders.matchAllQuery(),
//                                FilterBuilders.andFilter(
//                                        FilterBuilders.rangeFilter("@timestamp").from(fromUTCTimeZoneFormat).to(toUTCTimeZoneFormat),
//                                        //                                        !deviceName.trim().equals("") ? FilterBuilders.termFilter("syslog_hostname", deviceName) : FilterBuilders.matchAllFilter()
//                                        FilterBuilders.termsFilter("syslog_hostname", "sim", "172.30.1.90"), FilterBuilders.termsFilter("syslog_facility", "local2")
//                                ))
//                )
//                .setFrom(new Long(start).intValue()).setSize(new Long(row).intValue())
//                .addSort("@timestamp", sortBy == 1 ? SortOrder.DESC : SortOrder.ASC)
//                .setExplain(true).execute().actionGet();
//        //  response.getHits().getHits().length;
//        System.out.println("++++++++++++++++++++++++++Total No Of Doucument Found : " + response.getHits().getTotalHits());
//
//        for (SearchHit hit : response.getHits()) {
//            System.out.println("Search Result -" + hit.getSource().get("@timestamp") + " " + hit.getSource().get("host") + " " + hit.getSource().get("message"));
//
//            System.out.println("Message :" + (String) hit.getSource().get("syslog_message"));
//            System.out.println("Tag :" + (String) hit.getSource().get("syslog_program"));
//            System.out.println("Devices :" + (String) hit.getSource().get("syslog_hostname"));
//
//            System.out.println("Facility : " + hit.getSource().get("syslog_facility_code"));
//            System.out.println("Primary : " + hit.getSource().get("syslog_severity_code"));
//
//            System.out.println("@timestamp : " + hit.getSource().get("@timestamp"));
//            System.out.println("SyslogTimestamp : " + hit.getSource().get("syslog_timestamp"));
//
//            System.out.println("SyslogHost : " + hit.getSource().get("syslog_hostname"));
//
//            System.out.println("Local Date : " + DateConverter.getLocalUnixTimeFromUTCTimeZoneStringFormat((String) hit.getSource().get("@timestamp")));
//
//            System.out.println("");
//
//        }
    }

}
