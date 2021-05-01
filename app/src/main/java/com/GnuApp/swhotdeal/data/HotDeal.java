package com.GnuApp.swhotdeal.data;

import com.GnuApp.swhotdeal.R;

// 리사이클러 뷰 사용하기 위한 데이터, get
public class HotDeal {
    private int loadNumber; // 갱신순서
    private String swName; // 소프트웨어 명
//    private String devName; // 개발사 명
    private int disPeriod; // 할인 기간
    private String currency; // 통화
    private int cost; // 원가
    private int disPrice; // 할인가
    private int disRate; // 할인율
    private String platAddress; // 플랫폼 주소, 판매처 링크
    private String platName; // 플랫폼 이름
    private String repPicture; // 대표사진
    private String othPicture; // 기타사진

    public HotDeal(){
        this.setSWName("tempSWName");
        this.setDisPeriod(20991231);
        this.setCost(999900);
        this.setDisPrice(666600);
        this.setDisRate(3300);
        this.setPlatAddress("https://gnu.ac.kr/main/");
        this.setPlatName("GNU");
        this.setRepPicture("https://www.gnu.ac.kr/img/main/logo_N.gif");
        this.setOthPicture("https://newgh.gnu.ac.kr/common/images/T1_layout/logo.png");
    }
    // 파이어베이스에서 임시로 받아올 것.
    // 생성자로 기본 자료 셋팅은 위험할듯

    public String getSWName() { return swName; }
    public void setSWName(String swName) { this.swName = swName; }

//    public String getDevName() { return devName; }
//    public void setDevName(String devName) { this.devName = devName; }
    // 21. 4. 27. 회의 devname 제외

    public int getDisPeriod() { return disPeriod; }
    public void setDisPeriod(int disPeriod) { this.disPeriod = disPeriod; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost / 100; }

    public int getDisPrice() { return disPrice; }
    public void setDisPrice(int disPrice) { this.disPrice = disPrice / 100; }

    public int getDisRate() { return disRate; }
    public void setDisRate(int disRate) { this.disRate = disRate / 100; }
    // 할인율 서버에서 받아옴
    
    public String getPlatAddress() { return platAddress; }
    public void setPlatAddress(String platAddress) { this.platAddress = platAddress; }

    public String getPlatName() { return platName; }
    public void setPlatName(String platName) { this.platName = platName; }

    public String getRepPicture() { return repPicture; }
    public void setRepPicture(String repPicture) { this.repPicture = repPicture; }

    public String getOthPicture() { return othPicture; }
    public void setOthPicture(String othPicture) { this.othPicture = othPicture; }
}
