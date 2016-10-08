package parser;

import java.util.List;

public class TanxRequest {
	// 当前协议版本号，目前为3
	public int version;

	// Tanx生成的唯一的竞价ID，32字节的字符串
	public String bid;

	// 如果不为0，那么这是一个测试请求。
	// DSP需要返回一个正常填写的应答
	// Tanx不会展现给用户，且不会对该次请求计费
	public int is_test = 0;

	// 如果不为0，那么这是一个ping请求
	// DSP需要返回一个空的应答
	public int is_ping = 0;

	// ------------------------------------------- 浏览者信息

	// 用户身份标识
	// 如果进行cookie托管，且hosted_match_data存在，则该值不会被设置
	public String tid;

	// 用户的IP地址， ipv4
	// 例如：192.168.1.1
	public String ip;

	// 用户的浏览器类型，即HTTP请求头部的User-Agent
	public String user_agent;

	// 用户所处时区的分钟偏移量
	// 例如：如果是东八区，则 timezone_offset = 60 * 8 = 480.
	public int timezone_offset;

	// 用户垂直类别
	// 预留字段，暂无实际值
	public List<Integer> user_vertical;

	// tid 字段的版本号
	public int tid_version;

	// ------------------------------------------- 网页信息

	// 媒体禁止的目标跳转url
	// 所有条目的总长度不超过200个字符
	// 该字段可能不是全量数据，
	// 建议使用离线数据获取媒体设置的全量数据
	public List<String> excluded_click_through_url;

	// 推广位所在的页面url
	public String url;

	// 推广位所在网站的分类
	// 参见数据字典 Tanx-dict-site-category.txt
	public int category;

	// 流量所属来源，预留字段，目前为0
	public int adx_type = 0;

	// 预留字段，暂无实际值
	public String anonymous_id;

	// 预留字段，暂无实际值
	public String detected_language;

	// category 字段版本号，目前为1
	public int category_version;

	// ------------------------------------------- 推广位基础信息
	public static class AdzInfo {
		// 推广位编号，目前固定为0
		public int id;

		// 推广位的唯一标识
		public String pid;

		// 媒体的唯一标识, 和创意审核状态查询api配合使用
		public String publisher_id;

		// 推广位的尺寸: 宽 x 高
		// 例如：300x250
		public String size;

		// 推广位希望从单个DSP获取的竞价广告数量
		// DSP可以提供小于等于此值的广告个数（只会有一个广告胜出）
		// 多于AdzInfo.ad_bid_count数值的广告，将被截断而不参与竞价
		public int ad_bid_count = 2;

		// 推广位的展现形式
		// 参见数据字典 Tanx-dict-view-type.txt
		public List<Integer> view_type;

		// 媒体禁止的创意类型
		// 参见数据字典 Tanx-dict-creative-type.txt
		// 该字段可能不是全量数据，
		// 建议使用离线数据获取媒体设置的全量数据
		public List<Integer> excluded_filter;

		// 最低竞标价格，货币单位为人民币，数值含义为分/千次展现
		public int min_cpm_price;

		// 已经废弃，请忽略
		public enum Location {
			NA(0), FIRST_VIEW(1), OTHER_VIEW(2);
			int location;

			private Location(int loc) {
				this.location = loc;
			}
			
			public static Location valueOf(int value) {
		          switch (value) {
		            case 0: return NA;
		            case 1: return FIRST_VIEW;
		            case 2: return OTHER_VIEW;
		            default: return null;
		          }
			}
		}

		public Location adz_location = Location.NA;

		// 推广位在页面所在的屏数
		// 0: 未识别; 1-5: 第1-5屏; 6: 第六屏及以外
		public enum ViewScreen {
			SCREEN_NA(0), SCREEN_FIRST(1), SCREEN_SECOND(2), SCREEN_THIRD(3), SCREEN_FOURTH(
					4), SCREEN_FIFTH(5), SCREEN_OTHER(6);
			public int val;

			private ViewScreen(int v) {
				this.val = v;
			}
			
			public static ViewScreen valueOf(int i) {
				switch (i){
	            case 0: return SCREEN_NA;
	            case 1: return SCREEN_FIRST;
	            case 2: return SCREEN_SECOND;
	            case 3: return SCREEN_THIRD;
	            case 4: return SCREEN_FOURTH;
	            case 5: return SCREEN_FIFTH;
	            case 6: return SCREEN_OTHER;
	            default: return null;
				}
			}
		}

		public ViewScreen view_screen = ViewScreen.SCREEN_NA;

		// 推广位在整个页面的渲染顺序，从0开始递增
		public int page_session_ad_idx;

		// 推广位支持的apiFramework
		// 1 VPAID 1.0; 2 VPAID 2.0; 3 MRAID-1; 4 ORMMA; 5 MRAID-2
		public List<Integer> api;
	}

	public List<AdzInfo> adzinfo;

	// 媒体禁止的敏感类目
	// 参见数据字典 Tanx-dict-sensitive-category.txt
	// 该字段可能不是全量数据，
	// 建议使用离线数据获取媒体设置的全量数据
	public List<Integer> excluded_sensitive_category;

	// 媒体禁止的广告行业类目
	// 参见数据字典 Tanx-dict-ad-category-version-2.xlsx
	// 该字段可能不是全量数据，
	// 建议使用离线数据获取媒体设置的全量数据
	public List<Integer> excluded_ad_category;

	// DSP进行Cookie托管的用户标识id
	// 对应cookie mapping里的tanx_hm输入参数
	// 如果hosted_match_data存在，则不设置tid
	public String hosted_match_data;

	// DSP进行Cookie托管的用户属性
	// 对应cookie mapping里的tanx_ua参数
	public static class UserAttribute {
		// DSP进行Cookie托管的用户属性id
		public int id;

		// DSP进行Cookie托管的用户属性id所对应的时间戳
		public int timestamp;
	}

	public List<UserAttribute> user_attribute;

	// 标识一个页面pv
	// 对于同一个页面的单个PV，Tanx会生成一个相同且唯一的page_session_id
	public String page_session_id;

	// 预留字段，暂无实际值
	public static class PrivateInfo {
		public String tanx_cnaui;
		public String risk_control;
	}

	public List<PrivateInfo> private_info;

	// ------------------------------------------- 无线推广位其他信息
	public static class Mobile {
		// 标识该次广告请求是否来自APP应用
		public boolean is_app;

		// 需要展示的创意数量
		// 仅对于AdzInfo.view_type为无线墙时，本字段有效
		public int ad_num;

		// 关键词，预留字段，暂无实际值
		public List<String> ad_keyword;

		// 推广位是否全屏展示
		public boolean is_fullscreen;

		// 推广位所在的应用包名
		// 例如：com.moji.MojiWeather
		public String package_name;

		public static class Device {
			// 设备平台(小写)
			// 例如：android, iphone, ipad
			public String platform;

			// 设备品牌(小写)
			// 例如：nokia, samsung.
			public String brand;

			// 设备型号(小写)
			// 例如：n70, galaxy.
			public String model;

			// 操作系统(小写)
			// 例如：android, ios
			public String os;

			// 操作系统版本
			// 例如：7.0.2
			public String os_version;

			// 设备所处网络环境
			// 0-未识别, 1-wifi, 2-2g, 3-3g, 4-4g
			public int network;

			// 设备的网络运营商
			// 0-未知, 1-移动, 2-联通, 3-电信
			public int operator;

			// 设备所在地理位置的经度（小数点格式）
			// 例如：116.41667
			public String longitude;

			// 设备所在地理位置的纬度（小数点格式）
			// 例如：39.91667
			public String latitude;

			// 设备的屏幕分辨率
			// 例如：1024x768
			public String device_size;

			// 设备ID号
			// 对于IOS(6.0及以上)设备，该值为idfa进行加密后的值
			// 对于IOS(6.0以下)设备，该值为mac进行加密后的值
			// 对于android设备，该值为imei进行加密后的值
			// 加密规则和密钥请联系Tanx接口人
			public String device_id;

			public int device_pixel_ratio = 1000;
		}

		public Device device;
	}

	// 若本字段被设置，则本次请求，来自移动设备。
	public Mobile mobile;

	// 基于推广位所在页面内容的分类信息
	public static class ContentCategory {
		// 基于推广位所在页面内容的网页类目ID
		// 参见数据字典 Tanx-dict-content-category.xlsx
		public int id;

		// 对应网页类目ID的置信分数。取值范围[0,1000]
		public int confidence_level;
	}

	public List<ContentCategory> content_categories;

	public static class Video {
		// 预留字段, 暂时忽略
		public enum VideoFormat {
			// HTML5 VAST创意包括mp4和webm媒体
			VIDEO_FLASH(0), VIDEO_HTML5(1);
			public int v;

			private VideoFormat(int v) {
				this.v = v;
			}
			
			public VideoFormat valueOf(int value) {
		          switch (value) {
		            case 0: return VIDEO_FLASH;
		            case 1: return VIDEO_HTML5;
		            default: return null;
		          }
			}
		}

		public List<VideoFormat> video_format;

		// 视频所在页面描述信息
		// utf-8编码
		public static class Content {
			// 视频标题
			public String title;
			// 视频时长，单位秒
			public int duration;
			// 视频标签
			public List<String> keywords;
		}

		public Content content;

		// 贴片位置相对于所在视频的起始时间，0 表示前贴片, -1 表示后贴片，大于0的值表示中插
		public int videoad_start_delay;

		// 上述位置可能包含多个贴片，表示本贴片在上述集合中相对起始位置，单位毫秒
		public int videoad_section_start_delay;

		// 贴片最小播放时间长度,视频创意播放时间不可小于该值，单位毫秒
		public int min_ad_duration;

		// 贴片最大播放时间长度，单位毫秒
		public int max_ad_duration;

		// VAST协议版本号.
		// 本期使用3.0版本，向下兼容
		public String protocol;
	}

	// 视频信息相关参数
	public Video video;

	// 阿里跨屏id
	public String aid;

}

