/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*@Copyright :SiamSecure Consulting Co., Ltd.
2521/38 BizTown Soi 3,
Ladprao, Wangthonglang,
Bangkok 10310 THAILAND
Tel : (66) 2 539 5703
Fax : (66) 2 539 5704
 */
package license;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author SSC-Client
 */
public class License implements Serializable {

    public static final long serialVersionUID = 90000L;
    private long id;
    private String userName;
    private Date expiraryDate;
    private long randomId;
    private boolean used = false;
    private String hostID;

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getRandomId() {
        return randomId;
    }

    public void setRandomId(long randomId) {
        this.randomId = randomId;
    }

    public Date getExpiraryDate() {
        return expiraryDate;
    }

    public void setExpiraryDate(Date expiraryDate) {
        this.expiraryDate = expiraryDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
