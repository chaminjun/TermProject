package my.mobile.com.termproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by chaminjun on 2016. 12. 2..
 */

public class ShowWeatherActivity extends AppCompatActivity {

    TextView show_weather_textview_today, show_weather_textview_tomorrow, show_weather_textview_next_tomorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);

        show_weather_textview_today = (TextView)findViewById(R.id.show_weather_textview_today);
        show_weather_textview_tomorrow = (TextView)findViewById(R.id.show_weather_textview_tomorrow);
        show_weather_textview_next_tomorrow = (TextView)findViewById(R.id.show_weather_textview_next_tomorrow);

        new ReceiveShowWeather().execute();
    }

    public class ReceiveShowWeather extends AsyncTask<URL, Integer, Long> {

        ArrayList<ShowWeather> showWeathers = new ArrayList<>();

        protected Long doInBackground(URL... urls) {

            String url = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1159068000";

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().url(url).build();

            Response response = null;

            try {
                response = client.newCall(request).execute();
                parseXML(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Long result) {
            String today_data = "";
            String tomorrow_data = "";
            String next_tomorrow_data = "";

            for(int i=0; i<showWeathers.size(); i++) {
                switch (showWeathers.get(i).getDay()){
                    case "0":
                        today_data += showTextWeather(i);
                        break;
                    case "1":
                        tomorrow_data += showTextWeather(i);
                        break;
                    case "2":
                        next_tomorrow_data += showTextWeather(i);
                        break;
                }
            }
            show_weather_textview_today.setText(today_data);
            show_weather_textview_tomorrow.setText(tomorrow_data);
            show_weather_textview_next_tomorrow.setText(next_tomorrow_data);
        }
        public String showTextWeather(int index){
            String showText = "시간 : "+ showWeathers.get(index).getHour() + "시\t" +
                    "온도 : " + showWeathers.get(index).getTemp() + "\t" +
                    showWeathers.get(index).getWfKor() + " " +
                    "강수확률 : " + showWeathers.get(index).getPop() + "%\n";
            return showText;
        }

        void parseXML(String xml) {
            try {
                String tagName = "";
                boolean onHour = false;
                boolean onDay = false;
                boolean onTem = false;
                boolean onWfKor = false;
                boolean onPop = false;
                boolean onEnd = false;
                boolean isItemTag1 = false;
                int i = 0;

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(new StringReader(xml));

                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        tagName = parser.getName();
                        if (tagName.equals("data")) {
                            showWeathers.add(new ShowWeather());
                            onEnd = false;
                            isItemTag1 = true;
                        }
                    } else if (eventType == XmlPullParser.TEXT && isItemTag1) {
                        if (tagName.equals("hour") && !onHour) {
                            showWeathers.get(i).setHour(parser.getText());
                            onHour = true;
                        }
                        if (tagName.equals("day") && !onDay) {
                            showWeathers.get(i).setDay(parser.getText());
                            onDay = true;
                        }
                        if (tagName.equals("temp") && !onTem) {
                            showWeathers.get(i).setTemp(parser.getText());
                            onTem = true;
                        }
                        if (tagName.equals("wfKor") && !onWfKor) {
                            showWeathers.get(i).setWfKor(parser.getText());
                            onWfKor = true;
                        }
                        if (tagName.equals("pop") && !onPop) {
                            showWeathers.get(i).setPop(parser.getText());
                            onPop = true;
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (tagName.equals("s06") && onEnd == false) {
                            i++;
                            onHour = false;
                            onDay = false;
                            onTem = false;
                            onWfKor = false;
                            onPop = false;
                            isItemTag1 = false;
                            onEnd = true;
                        }
                    }

                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

