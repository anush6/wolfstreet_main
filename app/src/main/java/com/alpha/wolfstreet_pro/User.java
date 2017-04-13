package com.alpha.wolfstreet_pro;

/**
 * Created by Adi on 10/03/17.
 */

public class User {

    public String name;
    public float bal, worth;
    public String phone;
    public String stockList;
    public String stockAmount;
    public String stockWishList;



    public User()
    {
        //Datasnapshot
    }
    public User(String name, String phone) {
      /*Blank default constructor essential for Firebase*/
        this.phone = phone;
        this.name = name;
        bal = 500000;
        worth = 500000;
        stockList = "GOOG+AAPL+MSFT";
        stockAmount = "0 0 0";
        stockWishList = "GOOG+AAPL+MSFT";
    }
    //Getters and setters
    public String getName() {
        return name;
    }


    public String getStockList(){
        return this.stockList;
    }
    public String getStockWishList(){
        return this.stockWishList;
    }
    public String getStockAmount(){ return this.stockAmount; }
    public String getPhone() { return this.phone; }
}
