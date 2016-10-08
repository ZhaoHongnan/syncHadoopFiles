package parser;

import java.util.ArrayList;
import java.util.List;

public class BaiduRequest {

	private String id;
	private String ip;
	private String user_agent;
	/*  已停止使用
	private String baidu_user_id;
	private int baidu_user_id_version;*/
	private List<Long> user_category;
	private String gender;
	private String detected_language;
	private String url;
	private String referer;
	private int site_category;
	private int site_quality;
	private int page_type;
	private List<String> page_keyword;
	private int page_quality;
	private List<Integer> exclude_product_category;
	private List<AdSlot> adslot;
    private List<BaiduId> baidu_id_list;
    //from here
    private String flash_version;
    private Geo user_geo_info;
    private int page_vertical;
    private Mobile mobile;
    private Video video;

    class AdSlot {
        class VideoInfo
        {
            private int max_video_duration;
            private int min_video_duration;
            private int video_start_delay;

            public int getMax_video_duration() {
                return max_video_duration;
            }

            public void setMax_video_duration(int max_video_duration) {
                this.max_video_duration = max_video_duration;
            }

            public int getMin_video_duration() {
                return min_video_duration;
            }

            public void setMin_video_duration(int min_video_duration) {
                this.min_video_duration = min_video_duration;
            }

            public int getVideo_start_delay() {
                return video_start_delay;
            }

            public void setVideo_start_delay(int video_start_delay) {
                this.video_start_delay = video_start_delay;
            }
        }
        class LinkUnitInfo
        {
            private List<Integer> style_type;
            private int req_keyword_num;
            private List<String> proposed_keyword;

            public List<Integer> getStyle_type() {
                return style_type;
            }

            public void setStyle_type(List<Integer> style_type) {
                this.style_type = style_type;
            }

            public int getReq_keyword_num() {
                return req_keyword_num;
            }

            public void setReq_keyword_num(int req_keyword_num) {
                this.req_keyword_num = req_keyword_num;
            }

            public List<String> getProposed_keyword() {
                return proposed_keyword;
            }

            public void setProposed_keyword(List<String> proposed_keyword) {
                this.proposed_keyword = proposed_keyword;
            }
        }
        class PreferredOrderInfo
        {
            class PreferredOrder
            {
                private String order_id;
                private long fixed_cpm;

                public String getOrder_id() {
                    return order_id;
                }

                public void setOrder_id(String order_id) {
                    this.order_id = order_id;
                }

                public long getFixed_cpm() {
                    return fixed_cpm;
                }

                public void setFixed_cpm(long fixed_cpm) {
                    this.fixed_cpm = fixed_cpm;
                }
            }
            private List<PreferredOrder> preferred_orders;
            private boolean allow_auction;

            public List<PreferredOrder> getPreferred_orders() {
                return preferred_orders;
            }

            public void setPreferred_orders(List<PreferredOrder> preferred_orders) {
                this.preferred_orders = preferred_orders;
            }

            public boolean isAllow_auction() {
                return allow_auction;
            }

            public void setAllow_auction(boolean allow_auction) {
                this.allow_auction = allow_auction;
            }
        }
		private long ad_block_key;
		private int sequence_id;
		private int adslot_type;
		private int width;
		private int height;
		private int slot_visibility;
		private List<Integer> creative_type;
		private List<String> excluded_landing_page_url;
		private int minimum_cpm;
        //from here
        private List<Long> publisher_settings_list_id;
        private VideoInfo video_Info;
        private LinkUnitInfo link_unit_info;
        private PreferredOrderInfo preferred_order_info;

        public List<Long> getPublisher_settings_list_id() {
            return publisher_settings_list_id;
        }

        public void setPublisher_settings_list_id(List<Long> publisher_settings_list_id) {
            this.publisher_settings_list_id = publisher_settings_list_id;
        }

        public VideoInfo getVideo_Info() {
            return video_Info;
        }

        public void setVideo_Info(VideoInfo video_Info) {
            this.video_Info = video_Info;
        }

        public LinkUnitInfo getLink_unit_info() {
            return link_unit_info;
        }

        public void setLink_unit_info(LinkUnitInfo link_unit_info) {
            this.link_unit_info = link_unit_info;
        }

        public PreferredOrderInfo getPreferred_order_info() {
            return preferred_order_info;
        }

        public void setPreferred_order_info(PreferredOrderInfo preferred_order_info) {
            this.preferred_order_info = preferred_order_info;
        }

        public long getAd_block_key() {
			return ad_block_key;
		}

		public void setAd_block_key(long ad_block_key) {
			this.ad_block_key = ad_block_key;
		}

		public int getSequence_id() {
			return sequence_id;
		}

		public void setSequence_id(int sequence_id) {
			this.sequence_id = sequence_id;
		}

		public int getAdslot_type() {
			return adslot_type;
		}

		public void setAdslot_type(int adslot_type) {
			this.adslot_type = adslot_type;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getSlot_visibility() {
			return slot_visibility;
		}

		public void setSlot_visibility(int slot_visibility) {
			this.slot_visibility = slot_visibility;
		}

		public List<Integer> getCreative_type() {
			return creative_type;
		}

		public void setCreative_type(List<Integer> creative_type) {
			this.creative_type = creative_type;
		}

		public List<String> getExcluded_landing_page_url() {
			return excluded_landing_page_url;
		}

		public void setExcluded_landing_page_url(
				List<String> excluded_landing_page_url) {
			this.excluded_landing_page_url = excluded_landing_page_url;
		}

		public int getMinimum_cpm() {
			return minimum_cpm;
		}

		public void setMinimum_cpm(int minimum_cpm) {
			this.minimum_cpm = minimum_cpm;
		}

	}

    class BaiduId
    {
        private String baidu_user_id;
        private int baidu_user_id_version;

        public String getBaidu_user_id() {
            return baidu_user_id;
        }

        public void setBaidu_user_id(String baidu_user_id) {
            this.baidu_user_id = baidu_user_id;
        }

        public int getBaidu_user_id_version() {
            return baidu_user_id_version;
        }

        public void setBaidu_user_id_version(int baidu_user_id_version) {
            this.baidu_user_id_version = baidu_user_id_version;
        }
    }

    class Geo
    {
        class Coordinate
        {
            private String standard;
            private float latitude;
            private float longitude;

            public String getStandard() {
                return standard;
            }

            public void setStandard(String standard) {
                this.standard = standard;
            }

            public float getLatitude() {
                return latitude;
            }

            public void setLatitude(float latitude) {
                this.latitude = latitude;
            }

            public float getLongitude() {
                return longitude;
            }

            public void setLongitude(float longitude) {
                this.longitude = longitude;
            }
        }

        class UserLocation
        {
            private String province;
            private String city;
            private String district;
            private String street;

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }
        }

        private List<Coordinate> user_coordinate;
        private UserLocation user_location;

        public List<Coordinate> getUser_coordinate() {
            return user_coordinate;
        }

        public void setUser_coordinate(List<Coordinate> user_coordinate) {
            this.user_coordinate = user_coordinate;
        }

        public UserLocation getUser_location() {
            return user_location;
        }

        public void setUser_location(UserLocation user_location) {
            this.user_location = user_location;
        }
    }

    class Mobile
    {
        class MobileID
        {
            private String type;
            private String id;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
        class DeviceOsVersion
        {
            private int os_version_major;
            private int os_version_minor;
            private int os_version_micro;

            public int getOs_version_major() {
                return os_version_major;
            }

            public void setOs_version_major(int os_version_major) {
                this.os_version_major = os_version_major;
            }

            public int getOs_version_minor() {
                return os_version_minor;
            }

            public void setOs_version_minor(int os_version_minor) {
                this.os_version_minor = os_version_minor;
            }

            public int getOs_version_micro() {
                return os_version_micro;
            }

            public void setOs_version_micro(int os_version_micro) {
                this.os_version_micro = os_version_micro;
            }
        }
        class ForAdvertisingID
        {
            private String type;
            private String id;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
        class MobileApp
        {
            private String app_id;
            private String app_bundle_id;
            private int app_category;
            private int app_publisher_id;
            private List<String> app_interaction_type;

            public String getApp_id() {
                return app_id;
            }

            public void setApp_id(String app_id) {
                this.app_id = app_id;
            }

            public String getApp_bundle_id() {
                return app_bundle_id;
            }

            public void setApp_bundle_id(String app_bundle_id) {
                this.app_bundle_id = app_bundle_id;
            }

            public int getApp_category() {
                return app_category;
            }

            public void setApp_category(int app_category) {
                this.app_category = app_category;
            }

            public int getApp_publisher_id() {
                return app_publisher_id;
            }

            public void setApp_publisher_id(int app_publisher_id) {
                this.app_publisher_id = app_publisher_id;
            }

            public List<String> getApp_interaction_type() {
                return app_interaction_type;
            }

            public void setApp_interaction_type(List<String> app_interaction_type) {
                this.app_interaction_type = app_interaction_type;
            }
        }
        private String DEPRECATED_device_id;
        private List<MobileID> id;
        private String device_type;
        private String platform;
        private DeviceOsVersion os_version;
        private String brand;
        private String model;
        private int screen_width;
        private int screen_height;
        private long carrier_id;
        private String wireless_network_type;
        private String DEPRECATED_for_advertising_id;
        private List<ForAdvertisingID> for_advertising_id;
        private MobileApp mobile_app;

        public String getDEPRECATED_device_id() {
            return DEPRECATED_device_id;
        }

        public void setDEPRECATED_device_id(String DEPRECATED_device_id) {
            this.DEPRECATED_device_id = DEPRECATED_device_id;
        }

        public List<MobileID> getId() {
            return id;
        }

        public void setId(List<MobileID> id) {
            this.id = id;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public DeviceOsVersion getOs_version() {
            return os_version;
        }

        public void setOs_version(DeviceOsVersion os_version) {
            this.os_version = os_version;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public int getScreen_width() {
            return screen_width;
        }

        public void setScreen_width(int screen_width) {
            this.screen_width = screen_width;
        }

        public int getScreen_height() {
            return screen_height;
        }

        public void setScreen_height(int screen_height) {
            this.screen_height = screen_height;
        }

        public long getCarrier_id() {
            return carrier_id;
        }

        public void setCarrier_id(long carrier_id) {
            this.carrier_id = carrier_id;
        }

        public String getWireless_network_type() {
            return wireless_network_type;
        }

        public void setWireless_network_type(String wireless_network_type) {
            this.wireless_network_type = wireless_network_type;
        }

        public String getDEPRECATED_for_advertising_id() {
            return DEPRECATED_for_advertising_id;
        }

        public void setDEPRECATED_for_advertising_id(String DEPRECATED_for_advertising_id) {
            this.DEPRECATED_for_advertising_id = DEPRECATED_for_advertising_id;
        }

        public List<ForAdvertisingID> getFor_advertising_id() {
            return for_advertising_id;
        }

        public void setFor_advertising_id(List<ForAdvertisingID> for_advertising_id) {
            this.for_advertising_id = for_advertising_id;
        }

        public MobileApp getMobile_app() {
            return mobile_app;
        }

        public void setMobile_app(MobileApp mobile_app) {
            this.mobile_app = mobile_app;
        }
    }

    class Video
    {
        private String title;
        private List<String> tags;
        private int content_length;
        private List<Long> channel_id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public int getContent_length() {
            return content_length;
        }

        public void setContent_length(int content_length) {
            this.content_length = content_length;
        }

        public List<Long> getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(List<Long> channel_id) {
            this.channel_id = channel_id;
        }
    }

    public Geo getUser_geo_info() {
        return user_geo_info;
    }

    public void setUser_geo_info(Geo user_geo_info) {
        this.user_geo_info = user_geo_info;
    }

    public int getPage_vertical() {
        return page_vertical;
    }

    public void setPage_vertical(int page_vertical) {
        this.page_vertical = page_vertical;
    }

    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getFlash_version() {
        return flash_version;
    }

    public void setFlash_version(String flash_version) {
        this.flash_version = flash_version;
    }

    public List<BaiduId> getBaidu_id_list() {
        return baidu_id_list;
    }

    public void setBaidu_id_list(List<BaiduId> baidu_id_list) {
        this.baidu_id_list = baidu_id_list;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	public List<Long> getUser_category() {
		return user_category;
	}

	public void setUser_category(List<Long> user_category) {
		this.user_category = user_category;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDetected_language() {
		return detected_language;
	}

	public void setDetected_language(String detected_language) {
		this.detected_language = detected_language;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public int getSite_category() {
		return site_category;
	}

	public void setSite_category(int site_category) {
		this.site_category = site_category;
	}

	public int getSite_quality() {
		return site_quality;
	}

	public void setSite_quality(int site_quality) {
		this.site_quality = site_quality;
	}

	public int getPage_type() {
		return page_type;
	}

	public void setPage_type(int page_type) {
		this.page_type = page_type;
	}

	public List<String> getPage_keyword() {
		return page_keyword;
	}

	public void setPage_keyword(List<String> page_keyword) {
		this.page_keyword = page_keyword;
	}

	public int getPage_quality() {
		return page_quality;
	}

	public void setPage_quality(int page_quality) {
		this.page_quality = page_quality;
	}

	public List<Integer> getExclude_product_category() {
		return exclude_product_category;
	}

	public void setExclude_product_category(
			List<Integer> exclude_product_category) {
		this.exclude_product_category = exclude_product_category;
	}

	public List<AdSlot> getAdslot() {
		return adslot;
	}

	public void setAdslot(List<AdSlot> adslot) {
		this.adslot = adslot;
	}

	public BaiduRequest() {

	}

	public static BaiduRequest getBaiduRequestFromBidRequest(
			BaiduRealtimeBidding.BidRequest bidRequest) {
		BaiduRequest br = new BaiduRequest();
		br.setId(bidRequest.getId());
		br.setIp(bidRequest.getIp());
		br.setUser_agent(bidRequest.getUserAgent());
		br.setUser_category(bidRequest.getUserCategoryList());
		br.setGender(bidRequest.getGender().toString());
		br.setDetected_language(bidRequest.getDetectedLanguage());
		br.setUrl(bidRequest.getUrl());
		br.setReferer(bidRequest.getReferer());
		br.setSite_category(bidRequest.getSiteCategory());
		br.setSite_quality(bidRequest.getSiteQuality());
		br.setPage_type(bidRequest.getPageType());
		br.setPage_keyword(bidRequest.getPageKeywordList());
		br.setPage_quality(bidRequest.getPageQuality());
		br.setExclude_product_category(bidRequest
				.getExcludedProductCategoryList());
		List<AdSlot> adSlots = new ArrayList<BaiduRequest.AdSlot>();
		for (BaiduRealtimeBidding.BidRequest.AdSlot asd : bidRequest
				.getAdslotList()) {
			adSlots.add(br.getAdSlot(asd));
		}
        br.setAdslot(adSlots);
        List<BaiduId> baiduIds = new ArrayList<BaiduId>();
        for (BaiduRealtimeBidding.BidRequest.BaiduId bid:bidRequest.getBaiduIdListList())
        {
            baiduIds.add(br.getBaiduId(bid));
        }
        br.setBaidu_id_list(baiduIds);
        br.setFlash_version(bidRequest.getFlashVersion());
        Geo geo = br.getGeo(bidRequest.getUserGeoInfo());
        br.setUser_geo_info(geo);
        br.setPage_vertical(bidRequest.getPageVertical());
        Mobile mobile = br.getMobile(bidRequest.getMobile());
        br.setMobile(mobile);
        Video video = br.getVideo(bidRequest.getVideo());
        br.setVideo(video);
		return br;
	}

    public BaiduId getBaiduId(BaiduRealtimeBidding.BidRequest.BaiduId baiduId)
    {
        BaiduId bi = new BaiduId();
        bi.setBaidu_user_id(baiduId.getBaiduUserId());
        bi.setBaidu_user_id_version(baiduId.getBaiduUserIdVersion());
        return bi;
    }

    public Geo getGeo(BaiduRealtimeBidding.BidRequest.Geo geo)
    {
        Geo ge = new Geo();
        List<Geo.Coordinate> coordinates = new ArrayList<Geo.Coordinate>();
        for(BaiduRealtimeBidding.BidRequest.Geo.Coordinate coor:geo.getUserCoordinateList())
        {
            Geo.Coordinate coordinate = ge.new Coordinate();
            coordinate.setStandard(coor.getStandard().toString());
            coordinate.setLatitude(coor.getLatitude());
            coordinate.setLongitude(coor.getLongitude());
            coordinates.add(coordinate);
        }
        ge.setUser_coordinate(coordinates);

        Geo.UserLocation userLocation = ge.new UserLocation();
        userLocation.setProvince(geo.getUserLocation().getProvince());
        userLocation.setCity(geo.getUserLocation().getCity());
        userLocation.setDistrict(geo.getUserLocation().getDistrict());
        userLocation.setStreet(geo.getUserLocation().getStreet());
        ge.setUser_location(userLocation);

        return ge;
    }

    public Mobile getMobile(BaiduRealtimeBidding.BidRequest.Mobile mobile0)
    {
        Mobile mobile1 = new Mobile();
        mobile1.setDEPRECATED_device_id(mobile0.getDEPRECATEDDeviceId());
        List<Mobile.MobileID> mobileIDs = new ArrayList<Mobile.MobileID>();
        for(BaiduRealtimeBidding.BidRequest.Mobile.MobileID mi:mobile0.getIdList())
        {
            Mobile.MobileID mobileID = mobile1.new MobileID();
            mobileID.setId(mi.getId());
            mobileID.setType(mi.getType().toString());
        }
        mobile1.setId(mobileIDs);
        mobile1.setDevice_type(mobile0.getDeviceType().toString());
        mobile1.setPlatform(mobile0.getPlatform().toString());

        Mobile.DeviceOsVersion deviceOsVersion = mobile1.new DeviceOsVersion();
        deviceOsVersion.setOs_version_major(mobile0.getOsVersion().getOsVersionMajor());
        deviceOsVersion.setOs_version_micro(mobile0.getOsVersion().getOsVersionMicro());
        deviceOsVersion.setOs_version_minor(mobile0.getOsVersion().getOsVersionMinor());
        mobile1.setOs_version(deviceOsVersion);

        mobile1.setBrand(mobile0.getBrand());
        mobile1.setModel(mobile0.getModel());
        mobile1.setScreen_width(mobile0.getScreenWidth());
        mobile1.setScreen_height(mobile0.getScreenHeight());
        mobile1.setCarrier_id(mobile0.getCarrierId());
        mobile1.setDEPRECATED_for_advertising_id(mobile0.getDEPRECATEDForAdvertisingId());

        List<Mobile.ForAdvertisingID> forAdvertisingIDs = new ArrayList<Mobile.ForAdvertisingID>();
        for(BaiduRealtimeBidding.BidRequest.Mobile.ForAdvertisingID faid:mobile0.getForAdvertisingIdList())
        {
            Mobile.ForAdvertisingID forAdvertisingID = mobile1.new ForAdvertisingID();
            forAdvertisingID.setId(faid.getId());
            forAdvertisingID.setType(faid.getType().toString());
        }
        mobile1.setFor_advertising_id(forAdvertisingIDs);

        Mobile.MobileApp mobileApp = mobile1.new MobileApp();
        mobileApp.setApp_id(mobile0.getMobileApp().getAppId());
        mobileApp.setApp_bundle_id(mobile0.getMobileApp().getAppBundleId());
        mobileApp.setApp_category(mobile0.getMobileApp().getAppCategory());
        mobileApp.setApp_publisher_id(mobile0.getMobileApp().getAppPublisherId());
        List<String> appInteractionTypes = new ArrayList<String>();
        for (BaiduRealtimeBidding.BidRequest.Mobile.MobileApp.AppInteractionType appInteractionType
                :mobile0.getMobileApp().getAppInteractionTypeList())
        {
            appInteractionTypes.add(appInteractionType.toString());
        }
        mobileApp.setApp_interaction_type(appInteractionTypes);
        mobile1.setMobile_app(mobileApp);

        return mobile1;
    }

    public Video getVideo(BaiduRealtimeBidding.BidRequest.Video video0)
    {
        Video video1 = new Video();
        video1.setTitle(video0.getTitle());
        video1.setTags(video0.getTagsList());
        video1.setContent_length(video0.getContentLength());
        video1.setChannel_id(video0.getChannelIdList());
        return video1;
    }

	public AdSlot getAdSlot(BaiduRealtimeBidding.BidRequest.AdSlot adSlot) {
		AdSlot as = new AdSlot();
		as.setAd_block_key(adSlot.getAdBlockKey());
		as.setSequence_id(adSlot.getSequenceId());
		as.setAdslot_type(adSlot.getAdslotType());
		as.setWidth(adSlot.getWidth());
		as.setHeight(adSlot.getHeight());
		as.setSlot_visibility(adSlot.getSlotVisibility());
		as.setCreative_type(adSlot.getCreativeTypeList());
		as.setExcluded_landing_page_url(adSlot.getExcludedLandingPageUrlList());
		as.setMinimum_cpm(adSlot.getMinimumCpm());
		return as;
	}
}

