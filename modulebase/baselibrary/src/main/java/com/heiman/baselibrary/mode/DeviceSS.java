package com.heiman.baselibrary.mode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/6/20 8:39
 * @Description :
 * @Modify record :
 */

public class DeviceSS extends PLBase {
    /**
     * PL : {"2.1.1.0.252":{"NU":1,"DEV":[{"SS":{"RO":1,"TM":0,"PW":0,"OL":1,"ET":0},"ZX":12200},{"SS":{},"ZX":5102},{"SS":{"RO":1,"TM":0,"PW":9,"OL":1,"ET":200},"ZX":14801}],"PG":1}}
     */

    private PLBean PL;

    public PLBean getPL() {
        return PL;
    }

    public void setPL(PLBean PL) {
        this.PL = PL;
    }

    public static class PLBean {
        /**
         * 2.1.1.0.252 : {"NU":1,"DEV":[{"SS":{"RO":1,"TM":0,"PW":0,"OL":1,"ET":0},"ZX":12200},{"SS":{},"ZX":5102},{"SS":{"RO":1,"TM":0,"PW":9,"OL":1,"ET":200},"ZX":14801}],"PG":1}
         */

        @SerializedName("2.1.1.0.252")
        private OIDBean OID;

        public OIDBean getOID() {
            return OID;
        }

        public void setOID(OIDBean OID) {
            this.OID = OID;
        }

        public static class OIDBean {
            /**
             * NU : 1
             * DEV : [{"SS":{"RO":1,"TM":0,"PW":0,"OL":1,"ET":0},"ZX":12200},{"SS":{},"ZX":5102},{"SS":{"RO":1,"TM":0,"PW":9,"OL":1,"ET":200},"ZX":14801}]
             * PG : 1
             */

            private int NU;
            private int PG;
            private List<DEVBean> DEV;

            public int getNU() {
                return NU;
            }

            public void setNU(int NU) {
                this.NU = NU;
            }

            public int getPG() {
                return PG;
            }

            public void setPG(int PG) {
                this.PG = PG;
            }

            public List<DEVBean> getDEV() {
                return DEV;
            }

            public void setDEV(List<DEVBean> DEV) {
                this.DEV = DEV;
            }

            public static class DEVBean {
                /**
                 * SS : {"RO":1,"TM":0,"PW":0,"OL":1,"ET":0}
                 * ZX : 12200
                 */

                private SSBean SS;
                private int ZX;

                public SSBean getSS() {
                    return SS;
                }

                public void setSS(SSBean SS) {
                    this.SS = SS;
                }

                public int getZX() {
                    return ZX;
                }

                public void setZX(int ZX) {
                    this.ZX = ZX;
                }

                public static class SSBean {
                    /**
                     * RO : 1
                     * TM : 0
                     * PW : 0
                     * OL : 1
                     * ET : 0
                     */
                    @Expose
                    private int PW;
                    @Expose
                    private int ET;
                    @Expose
                    private int OF;
                    @Expose
                    private int LE;
                    @Expose
                    private int CR;
                    @Expose
                    private int CG;
                    @Expose
                    private int CB;
                    @Expose
                    private int OL;
                    @Expose
                    private int RO;
                    @Expose
                    private int UO;
                    @Expose
                    private int TMr;
                    @Expose
                    private int TMu;
                    @Expose
                    private int OF1;
                    @Expose
                    private int OF2;
                    @Expose
                    private int OF3;
                    @Expose
                    private int TM1;
                    @Expose
                    private int TM2;
                    @Expose
                    private int TM3;
                    @Expose
                    private int BP;
                    @Expose
                    private int DT;
                    @Expose
                    private int BA;
                    @Expose
                    private int TP;
                    @Expose
                    private int HY;
                    @Expose
                    private int TV;
                    @Expose
                    private int P2;
                    @Expose
                    private int CC;
                    @Expose
                    private int AQ;
                    @Expose
                    private int P1;
                    @Expose
                    private int TM;
                    @Expose
                    private int PH;

                    public int getPH() {
                        return PH;
                    }

                    public void setPH(int PH) {
                        this.PH = PH;
                    }

                    public int getOF() {
                        return OF;
                    }

                    public void setOF(int OF) {
                        this.OF = OF;
                    }

                    public int getLE() {
                        return LE;
                    }

                    public void setLE(int LE) {
                        this.LE = LE;
                    }

                    public int getCR() {
                        return CR;
                    }

                    public void setCR(int CR) {
                        this.CR = CR;
                    }

                    public int getCG() {
                        return CG;
                    }

                    public void setCG(int CG) {
                        this.CG = CG;
                    }

                    public int getCB() {
                        return CB;
                    }

                    public void setCB(int CB) {
                        this.CB = CB;
                    }

                    public int getUO() {
                        return UO;
                    }

                    public void setUO(int UO) {
                        this.UO = UO;
                    }

                    public int getTMr() {
                        return TMr;
                    }

                    public void setTMr(int TMr) {
                        this.TMr = TMr;
                    }

                    public int getTMu() {
                        return TMu;
                    }

                    public void setTMu(int TMu) {
                        this.TMu = TMu;
                    }

                    public int getOF1() {
                        return OF1;
                    }

                    public void setOF1(int OF1) {
                        this.OF1 = OF1;
                    }

                    public int getOF2() {
                        return OF2;
                    }

                    public void setOF2(int OF2) {
                        this.OF2 = OF2;
                    }

                    public int getOF3() {
                        return OF3;
                    }

                    public void setOF3(int OF3) {
                        this.OF3 = OF3;
                    }

                    public int getTM1() {
                        return TM1;
                    }

                    public void setTM1(int TM1) {
                        this.TM1 = TM1;
                    }

                    public int getTM2() {
                        return TM2;
                    }

                    public void setTM2(int TM2) {
                        this.TM2 = TM2;
                    }

                    public int getTM3() {
                        return TM3;
                    }

                    public void setTM3(int TM3) {
                        this.TM3 = TM3;
                    }

                    public int getBP() {
                        return BP;
                    }

                    public void setBP(int BP) {
                        this.BP = BP;
                    }

                    public int getDT() {
                        return DT;
                    }

                    public void setDT(int DT) {
                        this.DT = DT;
                    }

                    public int getBA() {
                        return BA;
                    }

                    public void setBA(int BA) {
                        this.BA = BA;
                    }

                    public int getTP() {
                        return TP;
                    }

                    public void setTP(int TP) {
                        this.TP = TP;
                    }

                    public int getHY() {
                        return HY;
                    }

                    public void setHY(int HY) {
                        this.HY = HY;
                    }

                    public int getTV() {
                        return TV;
                    }

                    public void setTV(int TV) {
                        this.TV = TV;
                    }

                    public int getP2() {
                        return P2;
                    }

                    public void setP2(int p2) {
                        P2 = p2;
                    }

                    public int getCC() {
                        return CC;
                    }

                    public void setCC(int CC) {
                        this.CC = CC;
                    }

                    public int getAQ() {
                        return AQ;
                    }

                    public void setAQ(int AQ) {
                        this.AQ = AQ;
                    }

                    public int getP1() {
                        return P1;
                    }

                    public void setP1(int p1) {
                        P1 = p1;
                    }

                    public int getRO() {
                        return RO;
                    }

                    public void setRO(int RO) {
                        this.RO = RO;
                    }

                    public int getTM() {
                        return TM;
                    }

                    public void setTM(int TM) {
                        this.TM = TM;
                    }

                    public int getPW() {
                        return PW;
                    }

                    public void setPW(int PW) {
                        this.PW = PW;
                    }

                    public int getOL() {
                        return OL;
                    }

                    public void setOL(int OL) {
                        this.OL = OL;
                    }

                    public int getET() {
                        return ET;
                    }

                    public void setET(int ET) {
                        this.ET = ET;
                    }
                }
            }
        }
    }
}
