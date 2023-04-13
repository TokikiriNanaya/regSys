package top.kiriya.regSys.entity;

import java.sql.Timestamp;

/**
 * @author Kiriya
 * @date 2023/1/11 17:44
 * 注册码实体类
 */
public class RegCode {

  private long id;
  private String code;
  private java.sql.Timestamp createTime;
  private long actived;
  private java.sql.Timestamp activeTime;
  private java.sql.Timestamp lastUseTime;
  private String pcInfo;
  private long useCount;
  private java.sql.Timestamp expiryTime;
  private long banned;
  private String email;
  private String qq;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public long getActived() {
    return actived;
  }

  public void setActived(long actived) {
    this.actived = actived;
  }

  public Timestamp getActiveTime() {
    return activeTime;
  }

  public void setActiveTime(Timestamp activeTime) {
    this.activeTime = activeTime;
  }

  public Timestamp getLastUseTime() {
    return lastUseTime;
  }

  public void setLastUseTime(Timestamp lastUseTime) {
    this.lastUseTime = lastUseTime;
  }

  public String getPcInfo() {
    return pcInfo;
  }

  public void setPcInfo(String pcInfo) {
    this.pcInfo = pcInfo;
  }

  public long getUseCount() {
    return useCount;
  }

  public void setUseCount(long useCount) {
    this.useCount = useCount;
  }

  public Timestamp getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(Timestamp expiryTime) {
    this.expiryTime = expiryTime;
  }

  public long getbanned() {
    return banned;
  }

  public void setbanned(long banned) {
    this.banned = banned;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getQq() {
    return qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  @Override
  public String toString() {
    return "RegCode{" +
            "id=" + id +
            ", code='" + code + '\'' +
            ", createTime=" + createTime +
            ", actived=" + actived +
            ", activeTime=" + activeTime +
            ", lastUseTime=" + lastUseTime +
            ", pcInfo='" + pcInfo + '\'' +
            ", useCount=" + useCount +
            ", expiryTime=" + expiryTime +
            ", banned=" + banned +
            ", email='" + email + '\'' +
            ", qq='" + qq + '\'' +
            '}';
  }
}
