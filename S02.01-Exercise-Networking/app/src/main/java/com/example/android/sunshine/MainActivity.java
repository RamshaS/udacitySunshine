/*
 * Copyright (C) 2016 The Android Open Source Project
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
package com.example.android.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mWeatherTextView;
    private TextView mForecastErrorTextView;
    private ProgressBar mForecastProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);
        mForecastErrorTextView = (TextView) findViewById(R.id.tv_forecast_err);
        mForecastProgressBar = (ProgressBar) findViewById(R.id.pb_forecast_loading);

        // COMPLETED (4) Delete the dummy weather data. You will be getting REAL data from the Internet in this lesson.
        /*
         * This String array contains dummy weather data. Later in the course, we're going to get
         * real weather data. For now, we want to get something on the screen as quickly as
         * possible, so we'll display this dummy data.
         */

        // COMPLETED (3) Delete the for loop that populates the TextView with dummy data
        /*
         * Iterate through the array and append the Strings to the TextView. The reason why we add
         * the "\n\n\n" after the String is to give visual separation between each String in the
         * TextView. Later, we'll learn about a better way to display lists of data.
         */


        // TODO (9) Call loadWeatherData to perform the network request to get the weather
        loadWeatherDate();
    }
public String loadWeatherDate(){
String location = SunshinePreferences.getPreferredWeatherLocation(this);
   return new WeatherService().execute(location);

}
    // TODO (8) Create a method that will get the user's preferred location and execute your new AsyncTask and call it loadWeatherData

    // TODO (5) Create a class that extends AsyncTask to perform network requests
    // TODO (6) Override the doInBackground method to perform your network requests
    // TODO (7) Override the onPostExecute method to display the results of the network request

    private class WeatherService extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
        mForecastProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            mForecastProgressBar.setVisibility(View.GONE);
            if(!s.isEmpty() && s!=null && s.equals("")){

                showResultTextView();
                mWeatherTextView.append(s);
                mWeatherTextView.append("\n");
            }else {
                showErrorTextView();
            }
        }

        @Override
        protected String doInBackground(String... location) {
            String result = null;
            try {
                result = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private void showErrorTextView() {

        mWeatherTextView.setVisibility(View.INVISIBLE);
        mForecastErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showResultTextView() {
        mWeatherTextView.setVisibility(View.VISIBLE);
        mForecastErrorTextView.setVisibility(View.INVISIBLE);
    }
}