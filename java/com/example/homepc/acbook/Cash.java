package com.example.homepc.acbook;

public class Cash {

    int _id;
    String _date;
    int _money;
    int _tt;
    String _pur;
    int _clear;

    public Cash() {
    }

    public Cash(int _id, String _date, int _money, int _tt, String _pur, int _clear) {
        this._id = _id;
        this._date = _date;
        this._money = _money;
        this._tt = _tt;
        this._pur = _pur;
        this._clear = _clear;
    }

    public Cash( String _date, int _money, int _tt, String _pur, int _clear) {
        this._date = _date;
        this._money = _money;
        this._tt = _tt;
        this._pur = _pur;
        this._clear = _clear;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public int get_money() {
        return _money;
    }

    public void set_money(int _money) {
        this._money = _money;
    }

    public int get_tt() {
        return _tt;
    }

    public void set_tt(int _tt) {
        this._tt = _tt;
    }

    public String get_pur() {
        return _pur;
    }

    public void set_pur(String _pur) {
        this._pur = _pur;
    }

    public int get_clear() {
        return _clear;
    }

    public void set_clear(int _clear) {
        this._clear = _clear;
    }

}



