package come.manager.direct.hackuniversity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Module {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("eth_address")
    @Expose
    private String ethAddress;
    @SerializedName("smartcontract")
    @Expose
    private String smartcontract;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }

    public String getSmartcontract() {
        return smartcontract;
    }

    public void setSmartcontract(String smartcontract) {
        this.smartcontract = smartcontract;
    }

}
