package com.example.ftp4j_demo2.view;

import android.view.View;

interface Page {
    public int getPageChildCount();
    public View getChildOnPageAt(int i);
    public void removeAllViewsOnPage();
    public void removeViewOnPageAt(int i);
    public int indexOfChildOnPage(View v);
}
