package com.echofeng.common.config;

/**
  * @ProjectName:    NBN
  * @ClassName:      AppStatusManager
  * @Description:    内存回收重启管理
  * @CreateDate:     2020/10/28 17:43
  * @UpdateUser:     更新者
  * @UpdateDate:     2020/10/28 17:43
  * @UpdateRemark:   更新说明
  * @Version:        1.0
 */
public class AppStatusManager {
    public int appStatus = AppConstants.AppStatus.STATUS_RECYCLE;//APP状态 初始值被系统回收

    public static AppStatusManager appStatusManager;

    private AppStatusManager(){}

    //单例模式
    public static AppStatusManager getInstance() {
        if (appStatusManager == null) {
            appStatusManager = new AppStatusManager();
        }
        return appStatusManager;
    }
    //得到状态
    public int getAppStatus() {
        return appStatus;
    }
    //设置状态
    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
    }
}
