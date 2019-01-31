package commondata;

/**
 * @author bin.yu
 * @create 2018-09-20 15:59
 **/
public class AccountConsumeVo {

    private Long orgId;
    private Long peerOrgId;
    private Double amount;
    private Long productId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getPeerOrgId() {
        return peerOrgId;
    }

    public void setPeerOrgId(Long peerOrgId) {
        this.peerOrgId = peerOrgId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
