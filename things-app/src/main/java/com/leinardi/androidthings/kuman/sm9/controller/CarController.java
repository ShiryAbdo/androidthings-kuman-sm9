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

import com.leinardi.androidthings.kuman.sm9.api.GoogleApiClientRepository;
import com.leinardi.androidthings.kuman.sm9.common.api.car.ThingsMessage;
import com.leinardi.androidthings.kuman.sm9.controller.oled.OledDisplayController;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

@Singleton
public class CarController implements BaseController {
    private GoogleApiClientRepository mGoogleApiClientRepository;
    private MotorServoBoardController mMotorServoBoardController;
    private OledDisplayController mOledDisplayController;

    @Inject
    public CarController(GoogleApiClientRepository googleApiClientRepository,
                         MotorServoBoardController motorServoBoardController,
                         OledDisplayController oledDisplayController) {
        mGoogleApiClientRepository = googleApiClientRepository;
        mMotorServoBoardController = motorServoBoardController;
        mOledDisplayController = oledDisplayController;
        Observable.interval(2, TimeUnit.SECONDS).subscribe(tick -> refresh());

    }

    private void refresh() {
        mOledDisplayController.refreshDisplay();
    }

    public void start() {
        mGoogleApiClientRepository.connect();
        mGoogleApiClientRepository.subscribeToThingsMessage(new DisposableObserver<ThingsMessage>() {
            @Override
            public void onNext(ThingsMessage message) {
                handleThingsMessage(message);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void handleThingsMessage(ThingsMessage message) {
        Timber.d("message = %s", message);
        if (message.getCarMovement() != null) {
            mMotorServoBoardController.moveCar(message.getCarMovement().getAngle(), message.getCarMovement().getPower());
        }
        if (message.getCameraCradlePosition() != null) {
            int horizontalServoAngle = message.getCameraCradlePosition().getHorizontalServoAngle();
            int verticalServoAngle = message.getCameraCradlePosition().getVerticalServoAngle();
            if (verticalServoAngle < 0) {
                verticalServoAngle = 0;
            }
            mMotorServoBoardController.moveCamera(horizontalServoAngle, verticalServoAngle);
        }
    }

    public void close() {
        mGoogleApiClientRepository.clear();
        mMotorServoBoardController.close();
    }
}
