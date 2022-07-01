package com.example.appnhachay.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Baihat  implements Parcelable{

@SerializedName("IdBaihat")
@Expose
private String idBaihat;
@SerializedName("TenBaihat")
@Expose
private String tenBaihat;
@SerializedName("HinhBaihat")
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


    protected Baihat(Parcel in) {
        idBaihat = in.readString();
        tenBaihat = in.readString();
        hinhBahat = in.readString();
        casi = in.readString();
        linkBaihat = in.readString();
        luotthich = in.readString();
    }

    public static final Creator<Baihat> CREATOR = new Creator<Baihat>() {
        @Override
        public Baihat createFromParcel(Parcel in) {
            return new Baihat(in);
        }

        @Override
        public Baihat[] newArray(int size) {
            return new Baihat[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idBaihat);
        dest.writeString(tenBaihat);
        dest.writeString(hinhBahat);
        dest.writeString(casi);
        dest.writeString(linkBaihat);
        dest.writeString(luotthich);
    }
}