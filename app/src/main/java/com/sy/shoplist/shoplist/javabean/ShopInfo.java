package com.sy.shoplist.shoplist.javabean;

/**
 * Created by Administrator on 2018/3/20 0020.
 */

public class ShopInfo {
    private int id = 0;//商品id
    private int sum = 1;//商品数
    private String price;//商品价格
    private String sname;//商品名称
    private String details;//商品详情
    private String imgurl;//商品图片链接

    public int getId() {
        return id;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public String toString() {
        return "商品信息:" + "id=" + id + ";price=" + price + ";sname=" + sname
                + ";details=" + details + ";imgurl=" + imgurl + ".";
    }
}
