/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.device;

import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.core.models.entities.user.User;

import com.project.core.services.user.UserService;

import com.project.core.services.util.UserList;
import com.project.core.util.DateConverter;
import com.project.core.util.Log4jUtil;
import com.project.rest.mvc.web.ChartDataTwo;
import com.project.rest.mvc.web.ChartDataTwoEdge;
import com.project.rest.mvc.web.ChartDataTwoFill;
import com.project.rest.mvc.web.ChartDataTwoMain;
import com.project.rest.resources.asm.user.UserListResourceAsm;
import com.project.rest.resources.user.UsersListResource;
import com.project.rest.util.searchcriteria.user.StudentSearchCriteria;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Samrit
 */
@Controller
@RequestMapping("/rest/device/test")
public class TestRESTController_ForDevice {

//    public TestRESTController_ForDevice(AccountService accountService) {
//        this.accountService = accountService;
//    }
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users",
            method = RequestMethod.GET)
    public ResponseEntity<UsersListResource> findAllUsers(@RequestParam(value = "name", required = false) String name) {
        try {

//            Thread.sleep(5000);
            UserList list = null;
            if (name == null) {
                list = userService.findUserListWithAllUsers();
            } else {
                User user = userService.findUserByUsername(name);
                if (user == null) {
                    list = new UserList(new ArrayList<User>());
                } else {
                    list = new UserList(Arrays.asList(user));
                }
            }
            UsersListResource res = new UserListResourceAsm().toResource(list);
            return new ResponseEntity<UsersListResource>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/listUsers/{pageNumber}", method = RequestMethod.GET)
    public ResponseEntity<UsersListResource> findUsersWithPagination(@PathVariable(value = "pageNumber") Integer pageNumber, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "searchText", required = false) String searchText) {
        try {
            UserList list = null;

            if (name == null) {
                if (searchText == null) {
                    searchText = "";
                }
                list = userService.findUsersWithPagination(searchText, pageNumber, 5);
            } else {
                User user = userService.findUserByUsername(name);
                if (user == null) {
                    list = new UserList(new ArrayList<User>());
                } else {
                    list = new UserList(Arrays.asList(user));
                }
            }
            UsersListResource res = new UserListResourceAsm().toResource(list);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/readRole",
            method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> readRole(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password) {
        try {
            String role = "Anonymous";
            try {
                if (username.equals("admin") && password.equals("admin")) {
                    role = "adminstrator";
                }
            } catch (Exception e) {

            }
            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/",
            method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> readDate() {
        try {
            return new ResponseEntity<>(DateConverter.getCurrentDateTimeFormat1(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/studentsearchcriteria", method = RequestMethod.GET)
    public ResponseEntity<StudentSearchCriteria> testForStudentSearchCriteria() {
        try {
            StudentSearchCriteria res = new StudentSearchCriteria();
            res.setParentUsername("Hello World 3");
            res.setPageNo((int) (Math.random() * 100));
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<?> testForException() {
        try {

            Log4jUtil.fatal("fatal");
            Log4jUtil.debug("debug");
            Log4jUtil.error("error");
            Log4jUtil.info("info");
            Log4jUtil.trace("trace");
            Log4jUtil.warn("warn");
            StudentSearchCriteria res = new StudentSearchCriteria();
            res.setPageNo(103);
//            int i = 1 / 0;
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {

//            for(int i =0;i<1000;i++)
//            {
            Log4jUtil.error(e.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(e));
//            }
//            e.printStackTrace();

            return new ResponseEntity<>(new UsersListResource(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/myip2", method = RequestMethod.GET)
    public @ResponseBody
    String processData(HttpServletRequest request) {

        return request.getRemoteAddr();
        // some other code
    }

    @RequestMapping(value = "/myip",
            method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> readMyIP(HttpServletRequest request) {
        try {
            System.out.println("MOMO > " + request);
            for (String header : IP_HEADER_CANDIDATES) {
                String ip = request.getHeader(header);
                System.out.println("MOMO > " + ip);
                if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                    return new ResponseEntity<>("DIRECT : " + ip, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("IN-DIRECT : " + request.getRemoteAddr(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static final String[] IP_HEADER_CANDIDATES = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"};

    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    @RequestMapping(value = "/testnumber",
            method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Integer> readNumber() {
        try {
            return new ResponseEntity<>(5, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/teststring",
            method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> readString() {
        try {
            return new ResponseEntity<>("Test number 25 and " + (int) (Math.random() * 100) + " is being sent from test server", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @RequestMapping(value = "/testDate",
//            method = {RequestMethod.GET, RequestMethod.POST})
//    public ResponseEntity<?> groupByDateTest(HttpServletRequest request) {
//        try {
//            User user = userService.findUserByUsername("st-9");
//            List<StudentAttendanceReportModel> attendanceReportModels = schoolReportService.findStudentAttendanceStatusForSchoolCheckInAndOutFromLastOneYear("st-9", user);
//
//            return new ResponseEntity<>(attendanceReportModels, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @RequestMapping(value = "/resolvesamplestatus", method = RequestMethod.GET)
//    public ResponseEntity<ResolveSampleStatus> testForResolveSampleStatusa() {
//        try {
//            ResolveSampleStatus res = new ResolveSampleStatus();
//            res.setId((long) (Math.random() * 100));
//            res.setDeviceCountDouble(Math.random() * 100);
//            res.setDeviceCountFloat((float) (Math.random() * 100));
//            res.setDeviceCountInt((int) (Math.random() * 100));
//            res.setDeviceCountLong((long) (Math.random() * 100));
//
//            List<ResolveSampleSubStatus> resolveSampleSubStatuses = new ArrayList<>();
//            ResolveSampleSubStatus rss1 = new ResolveSampleSubStatus();
//            rss1.setId((long) (Math.random() * 100));
//            rss1.setName("Sub Status 1" + (int) (Math.random() * 100));
//            rss1.setNumber((int) (Math.random() * 100));
//
//            ResolveSampleSubStatus rss2 = new ResolveSampleSubStatus();
//            rss2.setId((long) (Math.random() * 100));
//            rss2.setName("Sub Status 2" + (int) (Math.random() * 100));
//            rss2.setNumber((int) (Math.random() * 100));
//
//            resolveSampleSubStatuses.add(rss1);
//            resolveSampleSubStatuses.add(rss2);
//
//            res.setDeviceName("Device Name " + (int) (Math.random() * 100));
//            res.setResolveSampleSubStatus(resolveSampleSubStatuses);
//
//            return new ResponseEntity<ResolveSampleStatus>(res, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @RequestMapping(value = "/chartData", method = RequestMethod.GET)
//    public ResponseEntity<List<ChartData>> testForChartData() {
//        try {
//            ChartData res = new ChartData();
//            res.setId(1);
//            res.setBackArrow("filled");
//            res.setConnectType("filled");
//            res.setForwardArrow("filled");
//            res.setFrom(1);
//            res.setFromSide("right");
//            res.setImg("https://snippet.dhtmlx.com/codebase/data/diagram/05/img/cloud.svg");
//            res.setIp("192.168.1." + (Math.random() * 100));
//            res.setStrokeType("line");
//            res.setText("NGNIX " + (Math.random() * 100));
//            res.setTo(2);
//            res.setToSide("top");
//            res.setType("networkCard");
//            res.setX((int) (Math.random() * 100));
//            res.setY((int) (Math.random() * 100));
//
//            ChartData res1 = new ChartData();
//            res1.setId(1);
//            res1.setBackArrow("filled");
//            res1.setConnectType("filled");
//            res1.setForwardArrow("filled");
//            res1.setFrom(1);
//            res1.setFromSide("right");
//            res1.setImg("https://snippet.dhtmlx.com/codebase/data/diagram/05/img/cloud.svg");
//            res1.setIp("192.168.1." + (Math.random() * 100));
//            res1.setStrokeType("line");
//            res1.setText("NGNIX " + (Math.random() * 100));
//            res1.setTo(2);
//            res1.setToSide("top");
//            res1.setType("networkCard");
//            res1.setX((int) (Math.random() * 100));
//            res1.setY((int) (Math.random() * 100));
//
//            List<ChartData> chartDatas = new ArrayList<>();
//
//            chartDatas.add(res);
////            chartDatas.add(res1);
//
//            return new ResponseEntity<List<ChartData>>(chartDatas, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @RequestMapping(value = "/chartData2", method = RequestMethod.GET)
    public ResponseEntity<ChartDataTwoMain> testForResolveSampleStatusab() {
        try {
            ChartDataTwo res = new ChartDataTwo();

            String name1 = "Device 1 - " + Math.round((Math.random() * 100) * 100.0) / 100.0;
            res.setNumId(1);
            res.setId(name1);
            res.setHeight(50);
            res.setLink("https://www.flaticon.com/svg/static/icons/svg/224/224597.svg");
            ChartDataTwoFill fill = new ChartDataTwoFill();
            fill.setSrc("https://www.flaticon.com/svg/static/icons/svg/224/224597.svg");
            res.setFill(fill);
            res.setAlert(Boolean.TRUE);

            ChartDataTwo res1 = new ChartDataTwo();
            String name2 = "Device 2 - " + Math.round((Math.random() * 100) * 100.0) / 100.0;
            res1.setId(name2);
            res1.setNumId(2);
            res1.setHeight(50);
            ChartDataTwoFill fill2 = new ChartDataTwoFill();
            fill2.setSrc("https://www.freeiconspng.com/uploads/server-icon-9.jpg");
            res1.setLink("https://awoiaf.westeros.org/images/thumb/6/61/House_Tully.svg/500px-House_Tully.svg.png");
            res1.setFill(fill2);
            res1.setAlert(Boolean.FALSE);

            ChartDataTwo res2 = new ChartDataTwo();
            String name3 = "Device 3 - " + Math.round((Math.random() * 100) * 100.0) / 100.0;
            res2.setId(name3);
            res2.setNumId(3);
            res2.setHeight(50);
            ChartDataTwoFill fill3 = new ChartDataTwoFill();
            fill3.setSrc("https://awoiaf.westeros.org/images/thumb/d/d5/House_Lannister.svg/500px-House_Lannister.svg.png");
            res2.setLink("https://awoiaf.westeros.org/images/thumb/d/d5/House_Lannister.svg/500px-House_Lannister.svg.png");
            res2.setFill(fill3);
            res2.setAlert(Boolean.TRUE);

            ChartDataTwo res4 = new ChartDataTwo();
            String name4 = "Device 4 - " + Math.round((Math.random() * 100) * 100.0) / 100.0;
            res4.setId(name4);
            res4.setHeight(50);
            res4.setNumId(4);
            ChartDataTwoFill fill4 = new ChartDataTwoFill();
            fill4.setSrc("https://awoiaf.westeros.org/images/thumb/d/d5/House_Lannister.svg/500px-House_Lannister.svg.png");
            res4.setLink("https://awoiaf.westeros.org/images/thumb/d/d5/House_Lannister.svg/500px-House_Lannister.svg.png");
            res4.setFill(fill4);
            res4.setAlert(Boolean.FALSE);

            List<ChartDataTwo> nodesvalue = new ArrayList<>();
            nodesvalue.add(res);
            nodesvalue.add(res1);
            nodesvalue.add(res4);
            nodesvalue.add(res2);

            ChartDataTwoEdge edge1 = new ChartDataTwoEdge();
            edge1.setFrom(name1);
            edge1.setTo(name2);

            ChartDataTwoEdge edge2 = new ChartDataTwoEdge();
            edge2.setFrom(name1);
            edge2.setTo(name3);

            ChartDataTwoEdge edge3 = new ChartDataTwoEdge();
            edge3.setFrom(name1);
            edge3.setTo(name4);

            List<ChartDataTwoEdge> edges = new ArrayList<>();
            edges.add(edge1);
            edges.add(edge2);
            edges.add(edge3);

            ChartDataTwoMain chartDataTwoMain = new ChartDataTwoMain();

            chartDataTwoMain.setNodes(nodesvalue);
            chartDataTwoMain.setEdges(edges);

            return new ResponseEntity<ChartDataTwoMain>(chartDataTwoMain, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    class ChartDataTwoEdges {

        List<ChartDataTwoEdge> edges;

        public List<ChartDataTwoEdge> getEdges() {
            return edges;
        }

        public void setEdges(List<ChartDataTwoEdge> edges) {
            this.edges = edges;
        }

    }

    class ResolveSampleStatus {

        private Long id = 0L;

        private String deviceName = "";
        private Integer deviceCountInt = 1;
        private Long deviceCountLong = 1L;
        private Double deviceCountDouble = 1d;
        private Float deviceCountFloat = 1f;
        private List ResolveSampleSubStatus = new ArrayList();

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public Integer getDeviceCountInt() {
            return deviceCountInt;
        }

        public void setDeviceCountInt(Integer deviceCountInt) {
            this.deviceCountInt = deviceCountInt;
        }

        public Long getDeviceCountLong() {
            return deviceCountLong;
        }

        public void setDeviceCountLong(Long deviceCountLong) {
            this.deviceCountLong = deviceCountLong;
        }

        public Double getDeviceCountDouble() {
            return deviceCountDouble;
        }

        public void setDeviceCountDouble(Double deviceCountDouble) {
            this.deviceCountDouble = deviceCountDouble;
        }

        public Float getDeviceCountFloat() {
            return deviceCountFloat;
        }

        public void setDeviceCountFloat(Float deviceCountFloat) {
            this.deviceCountFloat = deviceCountFloat;
        }

        public List getResolveSampleSubStatus() {
            return ResolveSampleSubStatus;
        }

        public void setResolveSampleSubStatus(List ResolveSampleSubStatus) {
            this.ResolveSampleSubStatus = ResolveSampleSubStatus;
        }

    }

    class ResolveSampleSubStatus {

        private Long id = 0L;
        private String name = "";
        private Integer number = 0;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

    }

    class ChartData {

        private int id;
        private String type;
        private int x;
        private int y;
        private String img;
        private String text;
        private String ip;
        private int from;
        private int to;
        private String fromSide;
        private String toSide;
        private String connectType;
        private String strokeType;
        private String backArrow;
        private String forwardArrow;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public String getFromSide() {
            return fromSide;
        }

        public void setFromSide(String fromSide) {
            this.fromSide = fromSide;
        }

        public String getToSide() {
            return toSide;
        }

        public void setToSide(String toSide) {
            this.toSide = toSide;
        }

        public String getConnectType() {
            return connectType;
        }

        public void setConnectType(String connectType) {
            this.connectType = connectType;
        }

        public String getStrokeType() {
            return strokeType;
        }

        public void setStrokeType(String strokeType) {
            this.strokeType = strokeType;
        }

        public String getBackArrow() {
            return backArrow;
        }

        public void setBackArrow(String backArrow) {
            this.backArrow = backArrow;
        }

        public String getForwardArrow() {
            return forwardArrow;
        }

        public void setForwardArrow(String forwardArrow) {
            this.forwardArrow = forwardArrow;
        }

    }

    @RequestMapping(value = "/testIds", method = RequestMethod.GET)
    public ResponseEntity<String> testIDS() {
        List<Long> longs = new ArrayList<>();
        longs.add(1L);
        longs.add(2L);

        String nodeIds = StringUtils.join(longs, ',');
        return new ResponseEntity<String>(nodeIds, HttpStatus.OK);

    }

}
