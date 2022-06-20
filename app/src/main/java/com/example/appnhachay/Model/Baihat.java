package com.example.appnhachay.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Baihat {

@SerializedName("IdBaihat")
@Expose
private String idBaihat;
@SerializedName("TenBaihat")
@Expose
private String tenBaihat;
@SerializedName("HinhBahat")
@Expose
private String hinhBahat;
@SerializedName("Casi")
@Expose
private String casi;
@SerializedName("LinkBaihat")
@Expose
private String linkBaihat;
@SerializedName("Luotthich")
@Expose
private String luotthich;

public String getIdBaihat() {
return idBaihat;
}

public void setIdBaihat(String idBaihat) {
this.idBaihat = idBaihat;
}

public String getTenBaihat() {
return tenBaihat;
}

public void setTenBaihat(String tenBaihat) {
this.tenBaihat = tenBaihat;
}

public String getHinhBahat() {
return hinhBahat;
}

public void setHinhBahat(String hinhBahat) {
this.hinhBahat = hinhBahat;
}

public String getCasi() {
return casi;
}

public void setCasi(String casi) {
this.casi = casi;
}

public String getLinkBaihat() {
return linkBaihat;
}

public void setLinkBaihat(String linkBaihat) {
this.linkBaihat = linkBaihat;
}

public String getLuotthich() {
return luotthich;
}

public void setLuotthich(String luotthich) {
this.luotthich = luotthich;
}

}