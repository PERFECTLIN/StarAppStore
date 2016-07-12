package com.example.perfectlin.starappstore.Activity.Bean;

/**
 * Created by PERFECTLIN on 2016/7/12.
 */
public class FirAppListBean {
    private String id;
    private String user_id;
    private String type;
    private String name;
    private String short_url;
    private String bundle_id;
    private int genre_id;
    private boolean is_opened;
    private String web_template;
    private boolean has_combo;
    private int created_at;
    private String icon_url;
    private MasterReleaseBean masterReleaseBean;

    public FirAppListBean() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public String getBundle_id() {
        return bundle_id;
    }

    public void setBundle_id(String bundle_id) {
        this.bundle_id = bundle_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public boolean is_opened() {
        return is_opened;
    }

    public void setIs_opened(boolean is_opened) {
        this.is_opened = is_opened;
    }

    public String getWeb_template() {
        return web_template;
    }

    public void setWeb_template(String web_template) {
        this.web_template = web_template;
    }

    public boolean isHas_combo() {
        return has_combo;
    }

    public void setHas_combo(boolean has_combo) {
        this.has_combo = has_combo;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public MasterReleaseBean getMasterReleaseBean() {
        return masterReleaseBean;
    }

    public void setMasterReleaseBean(MasterReleaseBean masterReleaseBean) {
        this.masterReleaseBean = masterReleaseBean;
    }

    public static class MasterReleaseBean {
        private String version;
        private String build;
        private String release_type;
        private String distribution_name;
        private String supported_platform;
        private int created_at;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getBuild() {
            return build;
        }

        public void setBuild(String build) {
            this.build = build;
        }

        public String getRelease_type() {
            return release_type;
        }

        public void setRelease_type(String release_type) {
            this.release_type = release_type;
        }

        public String getDistribution_name() {
            return distribution_name;
        }

        public void setDistribution_name(String distribution_name) {
            this.distribution_name = distribution_name;
        }

        public String getSupported_platform() {
            return supported_platform;
        }

        public void setSupported_platform(String supported_platform) {
            this.supported_platform = supported_platform;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }
    }
}
