package com.example.stego;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stego.LSB.Asynk.EncCallback;
import com.example.stego.LSB.scr.Steganography;
import com.example.stego.LSB.scr.TextEncoding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements EncCallback {

    private TextView mTextMessage;
    Bitmap bitmap = null;
    Bitmap bitenc = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(MainActivity.this, DecodeImg.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        }
    };

    static final int GALLERY_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button loadImgBtn = (Button)findViewById(R.id.selectDecodeImg_btn);

        Button EncImgBtn = (Button)findViewById(R.id.encodeImg_btn);

        EncImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "Так называемая партизанская война началась со вступления неприятеля в Смоленск.\n" +
                        "Прежде чем партизанская война была официально принята нашим правительством, уже тысячи людей неприятельской армии — отсталые мародеры, фуражиры — были истреблены казаками и мужиками, побивавшими этих людей так же бессознательно, как бессознательно собаки загрызают забеглую бешеную собаку. Денис Давыдов своим русским чутьем первый понял значение той страшной дубины, которая, не спрашивая правил военного искусства, уничтожала французов, и ему принадлежит слава первого шага для узаконения этого приема войны.\n" +
                        "24-го августа был учрежден первый партизанский отряд Давыдова, и вслед за его отрядом стали учреждаться другие. Чем дальше подвигалась кампания, тем более увеличивалось число этих отрядов.\n" +
                        "Партизаны уничтожали Великую армию по частям. Они подбирали те отпадавшие листья, которые сами собою сыпались с иссохшего дерева — французского войска, и иногда трясли это дерево. В октябре, в то время как французы бежали к Смоленску, этих партий различных величин и характеров были сотни. Были партии, перенимавшие все приемы армии, с пехотой, артиллерией, штабами, с удобствами жизни; были одни казачьи, кавалерийские; были мелкие, сборные, пешие и конные, были мужицкие и помещичьи, никому не известные. Был дьячок начальником партии, взявший в месяц несколько сот пленных. Была старостиха Василиса, побившая сотни французов.\n" +
                        "Последние числа октября было время самого разгара партизанской войны. Тот первый период этой войны, во время которого партизаны, сами удивляясь своей дерзости, боялись всякую минуту быть пойманными и окруженными французами и, не расседлывая и почти не слезая с лошадей, прятались по лесам, ожидая всякую минуту погони, — уже прошел. Теперь уж война эта определилась, всем стало ясно, что можно было предпринять с французами и чего нельзя было предпринимать. Теперь уже только те начальники отрядов, которые с штабами, по правилам, ходили вдали от французов, считали еще многое невозможным. Мелкие же партизаны, давно уже начавшие свое дело и близко высматривавшие французов, считали возможным то, о чем не смели и думать начальники больших отрядов. Казаки же и мужики, лазившие между французами, считали, что теперь уже все было возможно.\n" +
                        "22-го октября Денисов, бывший одним из партизанов, находился с своей партией в самом разгаре партизанской страсти. С утра он с своей партией был на ходу. Он целый день по лесам, примыкавшим к большой дороге, следил за большим французским транспортом кавалерийских вещей и русских пленных, отделившимся от других войск и под сильным прикрытием, как это было известно от лазутчиков и пленных, направлявшимся к Смоленску. Про этот транспорт было известно не только Денисову и Долохову (тоже партизану с небольшой партией), ходившему близко от Денисова, но и начальникам больших отрядов с штабами: все знали про этот транспорт и, как говорил Денисов, точили на него зубы. Двое из этих больших отрядных начальников — один поляк, другой немец — почти в одно и в то же время прислали Денисову приглашение присоединиться каждый к своему отряду, с тем чтобы напасть на транспорт.\n" +
                        "— Нет, бг'ат, я сам с усам, — сказал Денисов, прочтя эти бумаги, и написал немцу, что, несмотря на душевное желание, которое он имел служить под начальством столь доблестного и знаменитого генерала, он должен лишить себя этого счастья, потому что уже поступил под начальство генерала-поляка. Генералу же поляку он написал то же самое, уведомляя его, что он уже поступил под начальство немца.\n" +
                        "Распорядившись таким образом, Денисов намеревался, без донесения о том высшим начальникам, вместе с Долоховым атаковать и взять этот транспорт своими небольшими силами. Транспорт шел 22 октября от деревни Микулиной к деревне Шамшевой. С левой стороны дороги от Микулина к Шамшеву шли большие леса, местами подходившие к самой дороге, местами отдалявшиеся от дороги на версту и больше. По этим-то лесам целый день, то углубляясь в середину их, то выезжая на опушку, ехал с партией Денисов, не выпуская из виду двигавшихся французов. С утра, недалеко от Микулина, там, где лес близко подходил к дороге, казаки из партии Денисова захватили две ставшие в грязи французские фуры с кавалерийскими седлами и увезли их в лес. С тех пор и до самого вечера партия, не нападая, следила за движением французов. Надо было, не испугав их, дать спокойно дойти до Шамшева и тогда, соединившись с Долоховым, который должен был к вечеру приехать на совещание к караулке в лесу (в версте от Шамшева), на рассвете пасть с двух сторон как снег на голову и побить и забрать всех разом.\n" +
                        "Позади, в двух верстах от Микулина, там, где лес подходил к самой дороге, было оставлено шесть казаков, которые должны были донести сейчас же, как только покажутся новые колонны французов.\n" +
                        "Впереди Шамшева точно так же Долохов должен был исследовать дорогу, чтобы знать, на каком расстоянии есть еще другие французские войска. При транспорте предполагалось тысяча пятьсот человек. У Денисова было двести человек, у Долохова могло быть столько же. Но превосходство числа не останавливало Денисова. Одно только, что еще нужно было знать ему, это то, какие именно были эти войска; и для этой цели Денисову нужно было взять языка (то есть человека из неприятельской колонны). В утреннее нападение на фуры дело сделалось с такою поспешностью, что бывших при фурах французов всех перебили и захватили живым только мальчика-барабанщика, который был отсталый и ничего не мог сказать положительно о том, какие были войска в колонне.\n" +
                        "Нападать другой раз Денисов считал опасным, чтобы не встревожить всю колонну, и потому он послал вперед в Шамшево бывшего при его партии мужика Тихона Щербатого — захватить, ежели можно, хоть одного из бывших там французских передовых квартиргеров.";
                Steganography stego = new Steganography(message,"qwerty",bitmap);
                TextEncoding textEncoding = new TextEncoding(MainActivity.this, MainActivity.this);
                ImageView imageView = (ImageView) findViewById(R.id.loadedImg);

                textEncoding.execute(stego);

               // bitenc = stego.getEncoded_image();

                //Log.d("ENCODE","TRUE");
                Log.d("STEGOMESSAGE",stego.getMessage());
                //imageView.setImageBitmap(bitenc);

            }
        });


        loadImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        ImageView imageView = (ImageView) findViewById(R.id.loadedImg);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);
                }
        }
    }

    @Override
    public void onStartTextEncoding() {

    }

    @Override
    public void onCompleteTextEncoding(Steganography result) {

        //After the completion of text encoding.
        ImageView imageView = (ImageView) findViewById(R.id.loadedImg);
        //result object is instantiated

        if (result != null && result.isEncoded()) {

            //encrypted image bitmap is extracted from result object
            Bitmap encoded_image = result.getEncoded_image();
            imageView.setImageBitmap(encoded_image);

        }
    }
}
