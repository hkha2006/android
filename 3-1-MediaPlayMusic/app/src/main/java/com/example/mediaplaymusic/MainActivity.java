package com.example.mediaplaymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Music> arrayList;
    private CustomMusicAdapter adapter;
    private ListView songList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songList = (ListView) findViewById(R.id.song_List);
        arrayList = new ArrayList<>();
        arrayList.add(new Music("Răng khôn","Phí Phương Anh",R.raw.rangkhon));
        arrayList.add(new Music("Trên tình bạn dưới tình yêu","Min",R.raw.trentinhbanduoitinhyeu));
        arrayList.add(new Music("Chuyện rằng","Thịnh Suy",R.raw.chuyenrang));
        arrayList.add(new Music("Để mị nói cho mà nghe","Hoàng Thùy Linh",R.raw.deminoichomanghe));
        arrayList.add(new Music("Gác lại âu lo","Miu Lê",R.raw.gaclaiaulo));
        arrayList.add(new Music("Giữa đại lộ đông tây","Uyên Linh",R.raw.giuadailodongtay));
        arrayList.add(new Music("Lỡ bye là say bye","Chang",R.raw.lobyelasaybye));
        arrayList.add(new Music("Phải chăng em đã yêu?","Juky San",R.raw.phaichangemdayeu));
        arrayList.add(new Music("Sài Gòn đau lòng quá","Hoàng Duyên",R.raw.saigondaulongqua));

        adapter = new CustomMusicAdapter(this,R.layout.custom_music_item,arrayList);
        songList.setAdapter(adapter);
    }

}