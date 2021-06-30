package com.example.findstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewScore;
    Button button1;
    Button button2;
    Button button3;
    Button button4;

    private final String srcCode = "https://www.imdb.com/list/ls045252306/";
    List<String> imgSrc;
    List<String> starsName;

    int randomNumber;
    int randomCase;
    int trueAnswer;
    int falseAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        textViewScore = findViewById(R.id.textViewScore);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        imgSrc = new ArrayList<>();
        starsName = new ArrayList<>();

        try {
            getHTML getHTML = new getHTML();
            Document document = getHTML.execute(srcCode).get();
            getHTML.getStars(document, starsName, imgSrc);
            randomNumber = getRandomInt(0, 100);
            GetImage getImage = new GetImage();
            Bitmap bitmap = null;
            bitmap = getImage.execute(imgSrc.get(randomNumber)).get();
            Intent intent = getIntent();
            if(intent.hasExtra("trueAnswer")) {
                trueAnswer += intent.getIntExtra("trueAnswer", 0);
            }
            if(intent.hasExtra("falseAnswer")) {
                falseAnswer += intent.getIntExtra("falseAnswer", 0);
            }
            if(intent.hasExtra("showAnswer")) {
                showAnswer(intent.getBooleanExtra("showAnswer", true));
            }
            String score = String.format("Правильных ответов - %s\nНеправильных ответов - %s", trueAnswer, falseAnswer);
            textViewScore.setText(score);
            imageView.setImageBitmap(bitmap);
            setButtons(randomNumber);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void setButtons(int trueValue) {
        randomCase = getRandomInt(1, 5);
        Log.i("RandomCase", String.valueOf(randomCase));
        switch (randomCase) {
            case 1:
                button1.setText(starsName.get(trueValue));
            case 2:
                button2.setText(starsName.get(trueValue));
            case 3:
                button3.setText(starsName.get(trueValue));
            case 4:
                button4.setText(starsName.get(trueValue));
        }
        if (randomCase != 1) {
            int randomVal = getRandomInt(0, 100);
            while (randomNumber != trueValue) {
                randomVal = getRandomInt(0, 100);
            }
            button1.setText(starsName.get(randomVal));
        }
        if (randomCase != 2) {
            int randomVal = getRandomInt(0, 100);
            while (randomNumber != trueValue) {
                randomVal = getRandomInt(0, 100);
            }
            button2.setText(starsName.get(randomVal));
        }
        if (randomCase != 3) {
            int randomVal = getRandomInt(0, 100);
            while (randomNumber != trueValue) {
                randomVal = getRandomInt(0, 100);
            }
            button3.setText(starsName.get(randomVal));
        }
        if (randomCase != 4) {
            int randomVal = getRandomInt(0, 100);
            while (randomNumber != trueValue) {
                randomVal = getRandomInt(0, 100);
            }
            button4.setText(starsName.get(randomVal));
        }
    }

    private void showAnswer(boolean showAnswer) {
        if (showAnswer) {
            Toast.makeText(getApplicationContext(), "Правильный ответ!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Неправильный ответ!", Toast.LENGTH_LONG).show();
        }
    }

    public void firstBtnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        boolean showAnswer;
        if (randomCase == 1) {
            trueAnswer++;
            showAnswer = true;
        } else {
            falseAnswer++;
            showAnswer = false;
        }
        intent.putExtra("trueAnswer", trueAnswer);
        intent.putExtra("falseAnswer", falseAnswer);
        intent.putExtra("showAnswer", showAnswer);
        startActivity(intent);
    }

    public void secondBtnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        boolean showAnswer;
        if (randomCase == 2) {
            trueAnswer++;
            showAnswer = true;
        } else {
            falseAnswer++;
            showAnswer = false;
        }
        intent.putExtra("trueAnswer", trueAnswer);
        intent.putExtra("falseAnswer", falseAnswer);
        intent.putExtra("showAnswer", showAnswer);
        startActivity(intent);
    }

    public void thirdBtnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        boolean showAnswer;
        if (randomCase == 3) {
            trueAnswer++;
            showAnswer = true;
        } else {
            falseAnswer++;
            showAnswer = false;
        }
        intent.putExtra("trueAnswer", trueAnswer);
        intent.putExtra("falseAnswer", falseAnswer);
        intent.putExtra("showAnswer", showAnswer);
        startActivity(intent);
    }

    public void fourthBtnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        boolean showAnswer;
        if (randomCase == 4) {
            trueAnswer++;
            showAnswer = true;
        } else {
            falseAnswer++;
            showAnswer = false;
        }
        intent.putExtra("trueAnswer", trueAnswer);
        intent.putExtra("falseAnswer", falseAnswer);
        intent.putExtra("showAnswer", showAnswer);
        startActivity(intent);
    }

    private int getRandomInt(int min, int max) {
        min = (int) Math.ceil(min);
        max = (int) Math.floor(max);
        return (int) Math.floor(Math.random() * (max - min)) + min;
    }


    private static class GetImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            java.net.URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream intputStream = urlConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(intputStream);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }
    }

    private static class getHTML extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... strings) {
            String URL = strings[0];
            try {
                Document document = Jsoup.connect(URL).get();
                return document;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void getStars(Document document, List<String> starsNames, List<String> starsImg) {
            Elements stars = document.select("img");
            int i = 1;
            for (Element element : stars) {
                String name = element.attr("alt");
                String imgSrc = element.attr("src");
                if (!name.equals("")) {
                    starsNames.add(name);
                    starsImg.add(imgSrc);
                    Log.i("Name", name + " " + i);
                    Log.i("ImgSrc", imgSrc + " " + i);
                    i++;
                }
                if (i > 100)
                    break;
            }
        }
    }
}
