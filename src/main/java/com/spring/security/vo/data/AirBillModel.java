package com.spring.security.vo.data;


import com.spring.security.annotation.ExcelColumn;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 票务扫描机票excel中原始数据
 *
 * @auther: zhuweirong
 * @date: 2021\1\21 0021 19:25
 */
public class AirBillModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelColumn(index = 0)
    private String invoiceType;// 发票类型 invoiceType

    @ExcelColumn(index = 1)
    private String namecn;//乘客姓名 name

    @ExcelColumn(index = 2)
    private String certnum;//乘客证件号码 userCardNo

    @ExcelColumn(index = 3, required = true)
    private String printNumber;//印刷序号 printNumber

    @ExcelColumn(index = 4, required = true, dataUniqueKey = true)
    private String ticketNo;//电子客票号码 eticketNumber

    @ExcelColumn(index = 5)
    private String deptdt;//发车时间

    @ExcelColumn(index = 6)
    private String checkCode;//校验码 checkCode

    @ExcelColumn(index = 7)
    private String invoiceDate;//开票日期 billingDate

    @ExcelColumn(index = 8)
    private String openUnit;//填开单位 issueBy

    @ExcelColumn(index = 9)
    private String saleUnitCode;//销售单位代号 agentcode

    @ExcelColumn(index = 10, required = true, fieldType = BigDecimal.class)
    private BigDecimal zj;//合计金额 totalAmount

    @ExcelColumn(index = 11)
    private String zsf;//合计税额 tax

    @ExcelColumn(index = 12)
    private String fueltax;//燃油附加费 fuelSurcharge

    @ExcelColumn(index = 13)
    private String tkttax;//民航发展基金 caacDevelopmentFund

    @ExcelColumn(index = 14)
    private String insurance;//保险费 insurance

    @ExcelColumn(index = 15)
    private String belongidLabel;//国内国际标签 internationalFlag

    @ExcelColumn(index = 16)
    private String ticketmoney;//票面金额 amountTax

    @ExcelColumn(index = 17)
    private String orgname;//出发地 flights[0].from

    @ExcelColumn(index = 18)
    private String dstname;//到达地 flights[0].to

    @ExcelColumn(index = 19)
    private String flightnum;//航班号 flights[0].flightNumber

    @ExcelColumn(index = 20)
    private String createdate;//日期 flights[0].date

    @ExcelColumn(index = 21)
    private String createtime;//时间 flights[0].time

    @ExcelColumn(index = 22)
    private String seatType;//座位类型 flights[0].seat

    @ExcelColumn(index = 23)
    private String carriername;//承运人 flights[0].carrier

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getNamecn() {
        return namecn;
    }

    public void setNamecn(String namecn) {
        this.namecn = namecn;
    }

    public String getCertnum() {
        return certnum;
    }

    public void setCertnum(String certnum) {
        this.certnum = certnum;
    }

    public String getPrintNumber() {
        return printNumber;
    }

    public void setPrintNumber(String printNumber) {
        this.printNumber = printNumber;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getDeptdt() {
        return deptdt;
    }

    public void setDeptdt(String deptdt) {
        this.deptdt = deptdt;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getOpenUnit() {
        return openUnit;
    }

    public void setOpenUnit(String openUnit) {
        this.openUnit = openUnit;
    }

    public String getSaleUnitCode() {
        return saleUnitCode;
    }

    public void setSaleUnitCode(String saleUnitCode) {
        this.saleUnitCode = saleUnitCode;
    }

    public BigDecimal getZj() {
        return zj;
    }

    public void setZj(BigDecimal zj) {
        this.zj = zj;
    }

    public String getZsf() {
        return zsf;
    }

    public void setZsf(String zsf) {
        this.zsf = zsf;
    }

    public String getFueltax() {
        return fueltax;
    }

    public void setFueltax(String fueltax) {
        this.fueltax = fueltax;
    }

    public String getTkttax() {
        return tkttax;
    }

    public void setTkttax(String tkttax) {
        this.tkttax = tkttax;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getBelongidLabel() {
        return belongidLabel;
    }

    public void setBelongidLabel(String belongidLabel) {
        this.belongidLabel = belongidLabel;
    }

    public String getTicketmoney() {
        return ticketmoney;
    }

    public void setTicketmoney(String ticketmoney) {
        this.ticketmoney = ticketmoney;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getDstname() {
        return dstname;
    }

    public void setDstname(String dstname) {
        this.dstname = dstname;
    }

    public String getFlightnum() {
        return flightnum;
    }

    public void setFlightnum(String flightnum) {
        this.flightnum = flightnum;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getCarriername() {
        return carriername;
    }

    public void setCarriername(String carriername) {
        this.carriername = carriername;
    }

    @Override
    public String toString() {
        return "AirBillModel{" +
                "invoiceType='" + invoiceType + '\'' +
                ", namecn='" + namecn + '\'' +
                ", certnum='" + certnum + '\'' +
                ", printNumber='" + printNumber + '\'' +
                ", ticketNo='" + ticketNo + '\'' +
                ", deptdt='" + deptdt + '\'' +
                ", checkCode='" + checkCode + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", openUnit='" + openUnit + '\'' +
                ", saleUnitCode='" + saleUnitCode + '\'' +
                ", zj='" + zj + '\'' +
                ", zsf='" + zsf + '\'' +
                ", fueltax='" + fueltax + '\'' +
                ", tkttax='" + tkttax + '\'' +
                ", insurance='" + insurance + '\'' +
                ", belongidLabel='" + belongidLabel + '\'' +
                ", ticketmoney='" + ticketmoney + '\'' +
                ", orgname='" + orgname + '\'' +
                ", dstname='" + dstname + '\'' +
                ", flightnum='" + flightnum + '\'' +
                ", createdate='" + createdate + '\'' +
                ", createtime='" + createtime + '\'' +
                ", seatType='" + seatType + '\'' +
                ", carriername='" + carriername + '\'' +
                '}';
    }
}
