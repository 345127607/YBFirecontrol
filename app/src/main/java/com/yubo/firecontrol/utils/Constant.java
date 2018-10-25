package com.yubo.firecontrol.utils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SDCardUtils;

import java.io.File;

/**
 * Created by lyf on 2018/8/10 10:12
 *
 * @author lyf
 * desc：常量类
 */
public class Constant {

    /**
     * 包名
     */
    public static final String ANDROID_CHANNEL_ID = AppUtils.getAppPackageName();

    /**
     * APP名称
     */
    public static final String ANDROID_CHANNEL_NAME = AppUtils.getAppName();

    /**
     * 请求地址
     */
    public static final String REQUEST_ADDRESS = "request_address";
    public static final String REQUEST_ADDRESS_DEFAULT = "http://172.16.102.227:10080/";

    /**
     * 文件服务器地址
     */
    public static final String FILE_SERVICE_PATH = "file_service_path";
    public static final String FILE_SERVICE_PATH_DEFAULT = "http://192.168.1.108:8080/";

    /**
     * 用户名
     */
    public static final String USER_NAME = "user_name";

    /**
     * 用户ID
     */
    public static final String USER_ID = "user_id";

    /**
     * 域ID
     */
    public static final String DOMAIN_ID = "domain_id";

    /**
     * 版本更新通知栏标识
     */
    public static final int VERSION_NOTIFICTION_FLAG = 9;

    /**
     * 密码
     */
    public static final String PASS_WORD = "pass_word";

    /**
     * 真实姓名
     */
    public static final String TRUE_NAME = "true_name";

    /**
     * 是否有网络
     */
    public static final String NET_STATE = "net_state";

    /**
     * token
     */
    public static final String TOKEN = "token";
    /**
     * appVersion
     */
    public static final String AppVersion = "AppVersion";
    /**
     * 弹窗间隔时间
     */
    public static final int DIALOG_TIME = 350;


    /**
     * 图片选择最大数量
     */
    public static final int PICTURE_MAX_COUNT = 9;

    /**
     * APP系统文件夹
     */
    public static final String APP_DIR = "cdqj";
    /**
     * 字典表数据库版本
     */
    public static final int SYSDIC_VERSION = 9;
    /**
     * 字典表数据库版本
     */
    public static final String SYSDIC_VERSION_PREFENCE = "sysdicversion";
    /**
     * 隐患类型数据库版本
     */
    public static final String HDPATROL_VERSION_PREFENCE = "hdpatrolversion";
    /**
     * APP系统文件夹地址
     */
    public static final String APP_PATH = SDCardUtils.getSDCardPathByEnvironment() + File.separator + APP_DIR;

    /**
     * 存放拍照图片的文件夹
     */
    public static final String APP_IMAGE_PATH = APP_PATH + File.separator + "image";

    /**
     * 图片选择最小数量
     */
    public static final int PICTURE_MIN_COUNT = 1;

    /**
     * 文件是否允许选择已有文件/只能现场拍摄录制
     */
    public static final boolean FILE_MODEL = true;

    /**
     * 坐标保存数据库执行间隔
     */
    public static final Long SAVE_POINT_TIME = 10 * 1000L;

    /**
     * 坐标上传执行间隔
     */
    public static final Long UPDATE_POINT_TIME = 10 * 1000L;

    /**
     * 坐标扫描数据库间隔
     */
    public static final Long DEL_POINT_SUBMIT_TIME = 2 * 60 * 60 * 1000L;

    /**
     * 坐标数据库数据删除间隔
     */
    public static final Long DEL_POINT_TIME = 5 * 24 * 60 * 60 * 1000L;

    /**
     * 最大速度40米/秒--->144KM/H
     */
    public static final double MAX_SPEED = 40;

    /**
     * 最大采集时间差（暂时一分钟）
     */
    public static final double MAX_TIME = 60 * 1000;

    /**
     * 最大采集时间差（暂时一分钟）
     */
    public static final String IS_PATROL_DO_WORK = "is_patrol_do_work";

    /**
     * 最大距离 3KM
     */
    public static final int MAX_DISTANCE = 3 * 1000;

    /**
     * 坐标X
     */
    public static final String LOCATION_LATITUDE = "location_latitude";

    /**
     * 坐标Y
     */
    public static final String LOCATION_LONGITUDE = "location_longitude";

    /**
     * 地址信息
     */
    public static final String LOCATION_ADDRESS = "location_address";

    /**
     * 卫星数量
     */
    public static final String LOCATION_SATELLITE_NUMBER = "location_satellite_number";

    /**
     * 方向
     */
    public static final String LOCATION_DIRECTION = "location_direction";

    /**
     * 速度
     */
    public static final String LOCATION_SPEED = "location_speed";

    /**
     * 运营商
     */
    public static final String LOCATION_OPERATOR_NAME = "location_operator_name";

    /**
     * 手机信号
     */
    public static final String PHONE_SINGLE = "phone_single";

    /**
     * 信号强弱标识
     */
    public static final String LOCATION_SATELLITE_NUMBER_FLAG = "location_satellite_number_flag";

    /**
     * 电池电量
     */
    public static final String BATTERY_LEVEL = "battery_level";

    /**
     * 拍照标识
     */
    public static final int REQUEST_CODE_IMAGE_CAPTURE = 500;

    /**
     * 拍照标识
     */
    public static final int REQUEST_CODE_IMAGE_CHECK = 501;

    /**
     * 图片质量压缩的最大大小/byte
     */
    public static final long IMAGE_MAX_SIZE = 60 * 8 * 1024L;

    /**
     * 图片压缩的采样率
     */
    public static final int IMAGE_MAX_SAMPLE_SIZE = 4;

    /**
     * 图片大小压缩的宽高
     */
    public static final float IMAGE_WHITE = 800, IMAGE_HEIGHT = 1000;

    /**
     * 文件类型标识-图片
     */
    public static final int IMG_TYPE = 1;

    /**
     * 文件类型标识-音频
     */
    public static final int AUDIO_TYPE = 2;

    /**
     * 文件类型标识-视频
     */
    public static final int VIDEO_TYPE = 3;
}
