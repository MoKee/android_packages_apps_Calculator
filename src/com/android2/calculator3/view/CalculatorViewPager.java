/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android2.calculator3.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.android2.calculator3.CalculatorSettings;
import com.android2.calculator3.Page;
import com.android2.calculator3.Page.NormalPanel;

public class CalculatorViewPager extends ViewPager {
    private boolean mIsEnabled;

    public CalculatorViewPager(Context context) {
        this(context, null);
    }

    public CalculatorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mIsEnabled = true;
    }

    /**
     * ViewPager inherits ViewGroup's default behavior of delayed clicks on its children, but in order to make the calc buttons more responsive we disable that here.
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mIsEnabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(mIsEnabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        mIsEnabled = enabled;
    }

    public boolean getPagingEnabled() {
        return mIsEnabled;
    }

    public void scrollToMiddle() {
        if(CalculatorSettings.useInfiniteScrolling(getContext())) {
            int pagesSize = Page.getPages(getContext()).size();
            int halfwayDownTheInfiniteList = (Integer.MAX_VALUE / pagesSize) / 2 * pagesSize + Page.getOrder(getContext(), new Page(getContext(), NormalPanel.BASIC));
            System.out.println("Halfway down is: " + halfwayDownTheInfiniteList);
            setCurrentItem(halfwayDownTheInfiniteList);
        }
    }
}
