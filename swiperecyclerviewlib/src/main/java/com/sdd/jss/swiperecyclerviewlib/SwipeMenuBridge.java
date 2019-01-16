/*
 * Copyright 2017 Yan Zhenjie
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
package com.sdd.jss.swiperecyclerviewlib;

/**
 * Created by YanZhenjie on 2017/7/20.
 */
public final class SwipeMenuBridge {

    private final SwipeSwitch mSwipeSwitch;
    private final int mDirection;
    private final int mPosition;

    SwipeMenuBridge(SwipeSwitch swipeSwitch, int direction, int position) {
        this.mSwipeSwitch = swipeSwitch;
        this.mDirection = direction;
        this.mPosition = position;
    }

    @SwipeMenuRecyclerView.DirectionMode
    public int getDirection() {
        return mDirection;
    }

    public int getPosition() {
        return mPosition;
    }

    public void closeMenu() {
        mSwipeSwitch.smoothCloseMenu();
    }
}
