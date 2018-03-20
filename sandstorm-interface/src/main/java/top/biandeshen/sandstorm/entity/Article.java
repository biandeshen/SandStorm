package top.biandeshen.sandstorm.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "cms_article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "audit_flag")
    private Byte auditFlag;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "delete_flag")
    private Boolean deleteFlag;

    @Column(name = "gmt_modified")
    private Date gmtModified;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    private String href;

    @Column(name = "order_no")
    private Integer orderNo;

    private String publisher;

    /**
     * 外链
     */
    @Column(name = "source_from")
    private String sourceFrom;

    /**
     * 摘要
     */
    private String summary;

    private String title;

    @Column(name = "column_info_id")
    private Integer columnInfoId;

    @Column(name = "is_audit")
    private Boolean isAudit;

    @Column(name = "is_top")
    private Boolean isTop;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "root_colunm_info_id")
    private Integer rootColunmInfoId;

    /**
     * 文章类型
     */
    private Boolean type;

    private String content;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return audit_flag
     */
    public Byte getAuditFlag() {
        return auditFlag;
    }

    /**
     * @param auditFlag
     */
    public void setAuditFlag(Byte auditFlag) {
        this.auditFlag = auditFlag;
    }

    /**
     * @return gmt_create
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return delete_flag
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @param deleteFlag
     */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * @return gmt_modified
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * @param gmtModified
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * @return cover_image_url
     */
    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    /**
     * @param coverImageUrl
     */
    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl == null ? null : coverImageUrl.trim();
    }

    /**
     * @return href
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href
     */
    public void setHref(String href) {
        this.href = href == null ? null : href.trim();
    }

    /**
     * @return order_no
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @param publisher
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    /**
     * 获取外链
     *
     * @return source_from - 外链
     */
    public String getSourceFrom() {
        return sourceFrom;
    }

    /**
     * 设置外链
     *
     * @param sourceFrom 外链
     */
    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom == null ? null : sourceFrom.trim();
    }

    /**
     * 获取摘要
     *
     * @return summary - 摘要
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置摘要
     *
     * @param summary 摘要
     */
    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * @return column_info_id
     */
    public Integer getColumnInfoId() {
        return columnInfoId;
    }

    /**
     * @param columnInfoId
     */
    public void setColumnInfoId(Integer columnInfoId) {
        this.columnInfoId = columnInfoId;
    }

    /**
     * @return is_audit
     */
    public Boolean getIsAudit() {
        return isAudit;
    }

    /**
     * @param isAudit
     */
    public void setIsAudit(Boolean isAudit) {
        this.isAudit = isAudit;
    }

    /**
     * @return is_top
     */
    public Boolean getIsTop() {
        return isTop;
    }

    /**
     * @param isTop
     */
    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    /**
     * @return view_count
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * @param viewCount
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * @return root_colunm_info_id
     */
    public Integer getRootColunmInfoId() {
        return rootColunmInfoId;
    }

    /**
     * @param rootColunmInfoId
     */
    public void setRootColunmInfoId(Integer rootColunmInfoId) {
        this.rootColunmInfoId = rootColunmInfoId;
    }

    /**
     * 获取文章类型
     *
     * @return type - 文章类型
     */
    public Boolean getType() {
        return type;
    }

    /**
     * 设置文章类型
     *
     * @param type 文章类型
     */
    public void setType(Boolean type) {
        this.type = type;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}