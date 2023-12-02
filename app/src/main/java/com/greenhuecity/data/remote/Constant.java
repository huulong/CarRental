package com.greenhuecity.data.remote;

public class Constant {
    /**
     * @since 12-12-2023
     * đây là API key từ https://openweathermap.org/
     * mình sử dụng API này để lấy nhiệt độ của TP.HCM
     * https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
     * lat là kinh độ của thành phố
     * lon là vĩ độ của thành phố
     * API Key là key bên dưới
     */
    public static String OPEN_WEATHER_MAP_API_KEY()
    {
        return "61799e907a45047b9b1bde5280cfed4d";
    }

    public static  String OPEN_WEATHER_MAP_API_KEY_2()
    {
        return "b8f52d84ad1ddb87329e39abc68a399a\n";
    }

    public static String OPEN_WEATHER_MAP_PATH()
    {
        return "https://api.openweathermap.org/data/2.5/weather/";
    }
}
