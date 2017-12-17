/*
 * Copyright 2017 Roberto Leinardi.
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

package com.leinardi.androidthings.kuman.sm9.controller;

import android.graphics.Bitmap;

public interface OledDisplayController extends BaseController {
    /**
     * Shows a bitmap image to LCD screen.
     *
     * @param bitmap The bitmap image that you want to show to screen.
     */
    void showBitmap(Bitmap bitmap);

    /**
     * Sets the contrast for the display.
     *
     * @param level The contrast level (0-255).
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     */
    void setContrast(int level);

    /**
     * Invert the display color without rewriting the contents of the display data RAM..
     *
     * @param invert Set to true to invert the display color; set to false to set the display back to normal.
     * @throws IllegalStateException
     */
    void invertDisplay(boolean invert);
}
