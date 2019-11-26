package com.sunnet.org.pay.model;

import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.member.model.Tb_member;

import javax.persistence.*;

/**
 * 提现记录
 *
 * @author jng
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "TB_MemberExtractAccount")
public class TB_MemberExtractAccount extends BaseModel {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    private String organName;

    private String account;

    private String accountName;

    @ManyToOne
    @JoinColumn(name = "MemberId")
    private Tb_member memberId;

    private Integer extractWay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "organName")
    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Column(name = "accountName")
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Tb_member getMemberId() {
        return memberId;
    }

    public void setMemberId(Tb_member memberId) {
        this.memberId = memberId;
    }

    @Column(name = "extractWay")
    public Integer getExtractWay() {
        return extractWay;
    }

    public void setExtractWay(Integer extractWay) {
        this.extractWay = extractWay;
    }
}
