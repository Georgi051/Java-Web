package panda.domain.models.views;

import panda.domain.entities.Package;

import java.math.BigDecimal;

public class ReceiptViewModel {
    private String id;
    private BigDecimal fee;
    private String issuedOn;
    private String recipient;
    private PackageViewModel aPackage;

    public ReceiptViewModel() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getIssuedOn() {
        return issuedOn;
    }

    public void setIssuedOn(String issuedOn) {
        this.issuedOn = issuedOn;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public PackageViewModel getaPackage() {
        return aPackage;
    }

    public void setaPackage(PackageViewModel aPackage) {
        this.aPackage = aPackage;
    }
}
