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

package com.leinardi.androidthings.kuman.sm9.remote.di;

import com.leinardi.androidthings.kuman.sm9.common.di.AppInjector;
import com.leinardi.androidthings.kuman.sm9.remote.MobileApp;

public class MobileAppInjector extends AppInjector<MobileApp> {
    private static final MobileAppInjector INSTANCE = new MobileAppInjector();

    private MobileAppInjector() {
    }

    public static MobileAppInjector getInstance() {
        return INSTANCE;
    }

    @Override
    protected void injectApplication(MobileApp application) {
        DaggerAppComponent.builder().application(application).build().inject(application);
    }
}
