package cn.sx.decentworld.bean;

import java.util.List;

import cn.sx.decentworld.DecentWorldApp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * 
 * @ClassName: DisplayAuthority.java
 * @Description: 获取用户信息时，对应的信息是否显示，获取对应的信息是否显示
 * @author: cj
 * @date: 2015年10月8日 下午6:57:29
 */
@Table(name = "displayAuthority")
public class DisplayAuthority extends Model
{
    /**
     * 用戶id
     */
    @Column(name = "userId")
    private String userId;

    /**
     * 是否显示家乡 1
     */
    @Column(name = "show_hometown")
    private Boolean showHometown;

    /**
     * 是否显示年龄 2
     */
    @Column(name = "show_age")
    private Boolean showAge;

    /**
     * 是否显示喜欢的运动 3
     */
    @Column(name = "show_likesport")
    private Boolean showLikesport;

    /**
     * 是否显示个性标签 4
     */
    @Column(name = "show_sign")
    private Boolean showSign;

    /**
     * 是否显示旅行足迹 5
     */
    @Column(name = "show_footprint")
    private Boolean showFootprint;

    /**
     * 是否显示喜欢的书籍 6
     */
    @Column(name = "show_likebooks")
    private Boolean showLikebooks;

    /**
     * 是否显示喜欢的电影 7
     */
    @Column(name = "show_likesmovies")
    private Boolean showLikesmovies;

    /**
     * 是否显示喜欢的音乐 8
     */
    @Column(name = "show_likemusic")
    private Boolean showLikemusic;

    /**
     * 是否显示喜欢的音乐 9
     */
    @Column(name = "show_userType")
    private Boolean showUserType;

    /**
     * 是否显示喜欢的音乐 10
     */
    @Column(name = "show_motto")
    private Boolean showMotto;

    /**
     * 是否显示电子邮箱 11
     */
    @Column(name = "show_email")
    private Boolean showEmail;

    /**
     * 是否显示婚姻状况 12
     */
    @Column(name = "show_MaritalStatus")
    private Boolean showMaritalStatus;

    /**
     * 是否显示身高 13
     */
    @Column(name = "show_stature")
    private Boolean showStature;

    /**
     * 是否显示民族 14
     */
    @Column(name = "show_nation")
    private Boolean showNation;

    /**
     * 是否显示二维码 15
     */
    @Column(name = "show_QRCode")
    private Boolean showQRCode;

    /**
     * 是否显示血型 16
     */
    @Column(name = "show_bloodType")
    private Boolean showBloodType;

    /**
     * 是否显示作品图 17
     */
    @Column(name = "show_workChart")
    private Boolean showWorkChart;

    /**
     * 是否显示有车子 18
     */
    @Column(name = "show_vehicle")
    private Boolean showVehicle;

    /**
     * 是否显示特长 19
     */
    @Column(name = "show_speciality")
    private Boolean showSpeciality;

    /**
     * 是否显示星座 20
     */
    @Column(name = "show_constellatory")
    private Boolean showConstellatory;

    /**
     * 是否显示喜欢的美食 21
     */
    @Column(name = "show_cate")
    private Boolean showCate;

    /**
     * 是否显示偶像 22
     */
    @Column(name = "show_Idol")
    private Boolean showIdol;

    /**
     * 是否显示宗教 23
     */
    @Column(name = "show_Religion")
    private Boolean showReligion;

    /**
     * 是否显示班级 24
     */
    @Column(name = "show_classes")
    private Boolean showClasses;

    /**
     * 是否显示院系 25
     */
    @Column(name = "show_Department")
    private Boolean showDepartment;

    /**
     * 是否显示学校 26
     */
    @Column(name = "show_school")
    private Boolean showSchool;

    /**
     * 是否显示职业 27
     */
    @Column(name = "show_occupation")
    private Boolean showOccupation;

    /**
     * 是否显示职位 28
     */
    @Column(name = "show_job")
    private Boolean showjob;

    /**
     * 是否显示公司 29
     */
    @Column(name = "show_corporation")
    private Boolean showCorporation;

    /**
     * 是否显示你常去的地点 30
     */
    @Column(name = "show_position")
    private Boolean showPosition;

    /**
     * 是否显示头像 31
     */
    @Column(name = "show_icon")
    private Boolean showIcon;

    /**
     * 是否显示微博 32
     */
    @Column(name = "show_blog")
    private Boolean showBlog;

    /**
     * 是否显示昵称 33
     */
    @Column(name = "show_nickname")
    private Boolean showNickName;

    /**
     * 是否显示qq 34
     */
    @Column(name = "show_qq")
    private Boolean showQQ;

    /**
     * 是否显示微信 35
     */
    @Column(name = "show_wechat")
    private Boolean showWechat;

    /**
     * 是否显示真实姓名 36
     */
    @Column(name = "show_realName")
    private Boolean showRealName;

    /**
     * 是否显示手机号码 37
     */
    @Column(name = "show_phoneNum")
    private Boolean showPhoneNum;

    /**
     * 是否显示性别 38
     */
    @Column(name = "show_gender")
    private Boolean showGender;

    /**
     * 是否显示身家
     */
    @Column(name = "show_wealth")
    private Boolean showWealth;

    /**
     * 是否显示身家
     */
    @Column(name = "show_worth")
    private Boolean showWorth;

    public DisplayAuthority()
    {
        this(DecentWorldApp.getInstance().getDwID());
    };

    public DisplayAuthority(String userId)
    {
        this.userId = userId;
        this.showBloodType = true;
        this.showCate = true;
        this.showClasses = true;
        this.showConstellatory = true;
        this.showCorporation = true;
        this.showDepartment = true;
        this.showEmail = true;
        this.showFootprint = true;
        this.showHometown = true;
        this.showIdol = true;
        this.showjob = true;
        this.showLikebooks = true;
        this.showLikemusic = true;
        this.showLikesmovies = true;
        this.showLikesport = true;
        this.showMaritalStatus = true;
        this.showNation = true;
        this.showOccupation = true;
        this.showPosition = true;
        this.showQRCode = true;
        this.showReligion = true;
        this.showSchool = true;
        this.showSpeciality = true;
        this.showStature = true;
        this.showVehicle = true;
        this.showWorkChart = true;
        this.showAge = true;
        this.showBlog = true;
        this.showIcon = true;
        this.showNickName = true;
        this.showWechat = true;
        this.showQQ = true;
        this.showSign = true;
        this.showMotto = false;
        this.showUserType = false;
        this.showPhoneNum = false;
        this.showRealName = false;
        this.showGender = true;
        this.showWealth = false;
        this.showWorth = true;
    }

    public Boolean getShowRealName()
    {
        return showRealName;
    }

    public void setShowRealName(Boolean showRealName)
    {
        this.showRealName = showRealName;
    }

    public Boolean getShowPhoneNum()
    {
        return showPhoneNum;
    }

    public void setShowPhoneNum(Boolean showPhoneNum)
    {
        this.showPhoneNum = showPhoneNum;
    }

    public Boolean getShowQQ()
    {
        return showQQ;
    }

    public void setShowQQ(Boolean showQQ)
    {
        this.showQQ = showQQ;
    }

    public Boolean getShowWechat()
    {
        return showWechat;
    }

    public void setShowWechat(Boolean showWechat)
    {
        this.showWechat = showWechat;
    }

    public Boolean getShowNickName()
    {
        return showNickName;
    }

    public void setShowNickName(Boolean showNickName)
    {
        this.showNickName = showNickName;
    }

    /**
     * @return the userId
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public Boolean getShowHometown()
    {
        return showHometown;
    }

    public void setShowHometown(Boolean showHometown)
    {
        this.showHometown = showHometown;
    }

    public Boolean getShowAge()
    {
        return showAge;
    }

    public void setShowAge(Boolean showAge)
    {
        this.showAge = showAge;
    }

    public Boolean getShowLikesport()
    {
        return showLikesport;
    }

    public void setShowLikesport(Boolean showLikesport)
    {
        this.showLikesport = showLikesport;
    }

    public Boolean getShowSign()
    {
        return this.showSign;
    }

    public void setShowSign(Boolean showSign)
    {
        this.showSign = showSign;
    }

    public Boolean getShowFootprint()
    {
        return showFootprint;
    }

    public void setShowFootprint(Boolean showFootprint)
    {
        this.showFootprint = showFootprint;
    }

    public Boolean getShowLikebooks()
    {
        return showLikebooks;
    }

    public void setShowLikebooks(Boolean showLikebooks)
    {
        this.showLikebooks = showLikebooks;
    }

    public Boolean getShowLikesmovies()
    {
        return showLikesmovies;
    }

    public void setShowLikesmovies(Boolean showLikesmovies)
    {
        this.showLikesmovies = showLikesmovies;
    }

    public Boolean getShowLikemusic()
    {
        return showLikemusic;
    }

    public void setShowLikemusic(Boolean showLikemusic)
    {
        this.showLikemusic = showLikemusic;
    }

    public Boolean getShowEmail()
    {
        return showEmail;
    }

    public void setShowEmail(Boolean showEmail)
    {
        this.showEmail = showEmail;
    }

    public Boolean getShowMaritalStatus()
    {
        return showMaritalStatus;
    }

    public void setShowMaritalStatus(Boolean showMaritalStatus)
    {
        this.showMaritalStatus = showMaritalStatus;
    }

    public Boolean getShowStature()
    {
        return showStature;
    }

    public void setShowStature(Boolean showStature)
    {
        this.showStature = showStature;
    }

    public Boolean getShowNation()
    {
        return showNation;
    }

    public void setShowNation(Boolean showNation)
    {
        this.showNation = showNation;
    }

    public Boolean getShowQRCode()
    {
        return showQRCode;
    }

    public void setShowQRCode(Boolean showQRCode)
    {
        this.showQRCode = showQRCode;
    }

    public Boolean getShowBloodType()
    {
        return showBloodType;
    }

    public void setShowBloodType(Boolean showBloodType)
    {
        this.showBloodType = showBloodType;
    }

    public Boolean getShowWorkChart()
    {
        return showWorkChart;
    }

    public void setShowWorkChart(Boolean showWorkChart)
    {
        this.showWorkChart = showWorkChart;
    }

    public Boolean getShowVehicle()
    {
        return showVehicle;
    }

    public void setShowVehicle(Boolean showVehicle)
    {
        this.showVehicle = showVehicle;
    }

    public Boolean getShowSpeciality()
    {
        return showSpeciality;
    }

    public void setShowSpeciality(Boolean showSpeciality)
    {
        this.showSpeciality = showSpeciality;
    }

    public Boolean getShowConstellatory()
    {
        return showConstellatory;
    }

    public void setShowConstellatory(Boolean showConstellatory)
    {
        this.showConstellatory = showConstellatory;
    }

    public Boolean getShowCate()
    {
        return showCate;
    }

    public void setShowCate(Boolean showCate)
    {
        this.showCate = showCate;
    }

    public Boolean getShowIdol()
    {
        return showIdol;
    }

    public void setShowIdol(Boolean showIdol)
    {
        this.showIdol = showIdol;
    }

    public Boolean getShowReligion()
    {
        return showReligion;
    }

    public void setShowReligion(Boolean showReligion)
    {
        this.showReligion = showReligion;
    }

    public Boolean getShowClasses()
    {
        return showClasses;
    }

    public void setShowClasses(Boolean showClasses)
    {
        this.showClasses = showClasses;
    }

    public Boolean getShowDepartment()
    {
        return showDepartment;
    }

    public void setShowDepartment(Boolean showDepartment)
    {
        this.showDepartment = showDepartment;
    }

    public Boolean getShowSchool()
    {
        return showSchool;
    }

    public void setShowSchool(Boolean showSchool)
    {
        this.showSchool = showSchool;
    }

    public Boolean getShowOccupation()
    {
        return showOccupation;
    }

    public void setShowOccupation(Boolean showOccupation)
    {
        this.showOccupation = showOccupation;
    }

    public Boolean getShowjob()
    {
        return showjob;
    }

    public void setShowjob(Boolean showjob)
    {
        this.showjob = showjob;
    }

    public Boolean getShowCorporation()
    {
        return showCorporation;
    }

    public void setShowCorporation(Boolean showCorporation)
    {
        this.showCorporation = showCorporation;
    }

    public Boolean getShowPosition()
    {
        return showPosition;
    }

    public void setShowPosition(Boolean showPosition)
    {
        this.showPosition = showPosition;
    }

    public Boolean getShowIcon()
    {
        return showIcon;
    }

    public void setShowIcon(Boolean showIcon)
    {
        this.showIcon = showIcon;
    }

    public Boolean getShowBlog()
    {
        return showBlog;
    }

    public void setShowBlog(Boolean showBlog)
    {
        this.showBlog = showBlog;
    }

    /**
     * @return the showUserType
     */
    public Boolean getShowUserType()
    {
        return showUserType;
    }

    /**
     * @param showUserType
     *            the showUserType to set
     */
    public void setShowUserType(Boolean showUserType)
    {
        this.showUserType = showUserType;
    }

    public Boolean getShowMotto()
    {
        return showMotto;
    }

    public void setShowMotto(Boolean showMotto)
    {
        this.showMotto = showMotto;
    }

    /**
     * @return the showGender
     */
    public Boolean getShowGender()
    {
        return showGender;
    }

    /**
     * @param showGender
     *            the showGender to set
     */
    public void setShowGender(Boolean showGender)
    {
        this.showGender = showGender;
    }

    /**
     * @return the showWealth
     */
    public Boolean getShowWealth()
    {
        return showWealth;
    }

    /**
     * @param showWealth
     *            the showWealth to set
     */
    public void setShowWealth(Boolean showWealth)
    {
        this.showWealth = showWealth;
    }

    /**
     * @return the showWorth
     */
    public Boolean getShowWorth()
    {
        return showWorth;
    }

    /**
     * @param showWorth
     *            the showWorth to set
     */
    public void setShowWorth(Boolean showWorth)
    {
        this.showWorth = showWorth;
    }

    /**
     * 清空权限表
     * 
     * @return
     */
    public static void deleteAll()
    {
        new Delete().from(DisplayAuthority.class).execute();
    }

    /**
     * 查询数据库是否有记录，若返回 0 ，则代表数据库为空
     * 
     * @return
     */
    public static List<DisplayAuthority> queryAll()
    {
        return new Select().from(DisplayAuthority.class).execute();
    }

    /**
     * 查询指定dwID号的记录并返回结果
     * 
     * @param dwID
     */
    public static DisplayAuthority queryByDwID(String userId)
    {
        return new Select().from(DisplayAuthority.class).where("userId=?", userId).executeSingle();
    }

}
