
package org.dinamizadores.dinaeventos.dto.facebookprofile;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class Data {

    @SerializedName("is_silhouette")
    @Expose
    private Boolean isSilhouette;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param isSilhouette
     * @param url
     */
    public Data(Boolean isSilhouette, String url) {
        this.isSilhouette = isSilhouette;
        this.url = url;
    }

    /**
     * 
     * @return
     *     The isSilhouette
     */
    public Boolean getIsSilhouette() {
        return isSilhouette;
    }

    /**
     * 
     * @param isSilhouette
     *     The is_silhouette
     */
    public void setIsSilhouette(Boolean isSilhouette) {
        this.isSilhouette = isSilhouette;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
