package com.example.perfectlin.starappstore.Activity.Bean;

/**
 * Created by PERFECTLIN on 2016/7/12.
 */
public class FirAppBean {
    private String id;
    private String type;
    private String name;
    private String desc;
    private String short_url;
    private String is_opened;
    private String bundle_id;
    private boolean is_show_plaza;
    private String passwd;
    private String max_release_count;
    private boolean is_store_auto_sync;
    private boolean store_link_visible;
    private int genre_id;
    private String created_at;
    private boolean has_combo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public String getIs_opened() {
        return is_opened;
    }

    public void setIs_opened(String is_opened) {
        this.is_opened = is_opened;
    }

    public String getBundle_id() {
        return bundle_id;
    }

    public void setBundle_id(String bundle_id) {
        this.bundle_id = bundle_id;
    }

    public boolean is_show_plaza() {
        return is_show_plaza;
    }

    public void setIs_show_plaza(boolean is_show_plaza) {
        this.is_show_plaza = is_show_plaza;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getMax_release_count() {
        return max_release_count;
    }

    public void setMax_release_count(String max_release_count) {
        this.max_release_count = max_release_count;
    }

    public boolean is_store_auto_sync() {
        return is_store_auto_sync;
    }

    public void setIs_store_auto_sync(boolean is_store_auto_sync) {
        this.is_store_auto_sync = is_store_auto_sync;
    }

    public boolean isStore_link_visible() {
        return store_link_visible;
    }

    public void setStore_link_visible(boolean store_link_visible) {
        this.store_link_visible = store_link_visible;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isHas_combo() {
        return has_combo;
    }

    public void setHas_combo(boolean has_combo) {
        this.has_combo = has_combo;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public boolean is_owner() {
        return is_owner;
    }

    public void setIs_owner(boolean is_owner) {
        this.is_owner = is_owner;
    }

    private String icon_url;
    private boolean is_owner;
}
