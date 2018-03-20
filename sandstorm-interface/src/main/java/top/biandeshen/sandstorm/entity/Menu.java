package top.biandeshen.sandstorm.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "rbac_menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父菜单id，父id为0则无父亲
     */
    private Integer pid;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 菜单路径
     */
    private String url;

    /**
     * 菜单等级，如一级菜单，二级菜单等
     */
    private Integer level;

    /**
     * 当前菜单操作（增删改查等）
     */
    private String state;

    /**
     * 菜单类型，是否为按钮
     */
    @Column(name = "is_button")
    private Boolean isButton;

    /**
     * 菜单状态，如是否启用等
     */
    private Boolean enable;

    /**
     * 菜单优先级，排列顺序
     */
    @Column(name = "order_no")
    private Integer orderNo;

    /**
     * 菜单样式
     */
    private String image;

    /**
     * 菜单描述
     */
    private String description;

    /**
     * 菜单创建者
     */
    private String creator;

    /**
     * 菜单创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 菜单修改者
     */
    private String modifier;

    /**
     * 菜单修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

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
     * 获取父菜单id，父id为0则无父亲
     *
     * @return pid - 父菜单id，父id为0则无父亲
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置父菜单id，父id为0则无父亲
     *
     * @param pid 父菜单id，父id为0则无父亲
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取菜单名称
     *
     * @return title - 菜单名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置菜单名称
     *
     * @param title 菜单名称
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取菜单路径
     *
     * @return url - 菜单路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置菜单路径
     *
     * @param url 菜单路径
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取菜单等级，如一级菜单，二级菜单等
     *
     * @return level - 菜单等级，如一级菜单，二级菜单等
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置菜单等级，如一级菜单，二级菜单等
     *
     * @param level 菜单等级，如一级菜单，二级菜单等
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取当前菜单操作（增删改查等）
     *
     * @return state - 当前菜单操作（增删改查等）
     */
    public String getState() {
        return state;
    }

    /**
     * 设置当前菜单操作（增删改查等）
     *
     * @param state 当前菜单操作（增删改查等）
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * 获取菜单类型，是否为按钮
     *
     * @return is_button - 菜单类型，是否为按钮
     */
    public Boolean getIsButton() {
        return isButton;
    }

    /**
     * 设置菜单类型，是否为按钮
     *
     * @param isButton 菜单类型，是否为按钮
     */
    public void setIsButton(Boolean isButton) {
        this.isButton = isButton;
    }

    /**
     * 获取菜单状态，如是否启用等
     *
     * @return enable - 菜单状态，如是否启用等
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * 设置菜单状态，如是否启用等
     *
     * @param enable 菜单状态，如是否启用等
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * 获取菜单优先级，排列顺序
     *
     * @return order_no - 菜单优先级，排列顺序
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * 设置菜单优先级，排列顺序
     *
     * @param orderNo 菜单优先级，排列顺序
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取菜单样式
     *
     * @return image - 菜单样式
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置菜单样式
     *
     * @param image 菜单样式
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * 获取菜单描述
     *
     * @return description - 菜单描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置菜单描述
     *
     * @param description 菜单描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取菜单创建者
     *
     * @return creator - 菜单创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置菜单创建者
     *
     * @param creator 菜单创建者
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 获取菜单创建时间
     *
     * @return gmt_create - 菜单创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置菜单创建时间
     *
     * @param gmtCreate 菜单创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取菜单修改者
     *
     * @return modifier - 菜单修改者
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置菜单修改者
     *
     * @param modifier 菜单修改者
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 获取菜单修改时间
     *
     * @return gmt_modified - 菜单修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置菜单修改时间
     *
     * @param gmtModified 菜单修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}