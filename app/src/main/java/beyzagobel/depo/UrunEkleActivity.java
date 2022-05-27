package beyzagobel.depo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UrunEkleActivity extends AppCompatActivity {

    Urunler urunler;
    String gelenDepoId,gelenUrunId;
    FirebaseDatabase database;
    Button btnUrunKaydet, btnUrunGuncelle;
    private TextInputLayout urunAdiWrapper;
    private TextInputLayout urunAdetiWrapper;
    private TextInputLayout barkodWrapper;
    private TextView lbldepoAdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_ekle);

        initComponents();
        intentKarsila();
    }

    private void intentKarsila() {
        Intent intent = getIntent();
        gelenDepoId = intent.getStringExtra("depoId");

        gelenUrunId = intent.getStringExtra("urunId");

        if(gelenUrunId == null){
            btnUrunGuncelle.setVisibility(View.INVISIBLE);
            btnUrunKaydet_onClick();
        }
        else{
            btnUrunKaydet.setVisibility(View.INVISIBLE);
            urunGuncelle();
            btnUrunGuncelle_onClick();
        }
    }

    private void btnUrunGuncelle_onClick() {
        btnUrunGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                urunAdiWrapper.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        long barkod = Long.parseLong(barkodWrapper.getEditText().getText().toString());
                        long urunAdeti = Long.parseLong(urunAdetiWrapper.getEditText().getText().toString());
                        String urunAdi = urunAdiWrapper.getEditText().getText().toString();
                        urunler = new Urunler(gelenUrunId, urunAdi, urunAdeti, barkod);
                        DatabaseReference dRurun = FirebaseDatabase.getInstance().getReference("urunler").child(gelenDepoId).child(gelenUrunId);
                        dRurun.setValue(urunler, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error == null) {
                                    Toast.makeText(UrunEkleActivity.this, barkod + " Barkod'lu Ürün Güncellendi!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(UrunEkleActivity.this,UrunlerActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });

                   Toast.makeText(UrunEkleActivity.this,"Lütfen Değişiklik Yapınız!",Toast.LENGTH_SHORT).show();

            }
        });
    }
   private void btnUrunKaydet_onClick() {
            btnUrunKaydet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(urunAdiWrapper.getEditText().getText().length()>0){
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("urunler").child(gelenDepoId);
                        String urunId = ref.push().getKey();
                        String urunAdi = urunAdiWrapper.getEditText().getText().toString();
                        long urunAdeti = Long.parseLong(urunAdetiWrapper.getEditText().getText().toString());
                        long barkod = Long.parseLong(barkodWrapper.getEditText().getText().toString());

                        urunler = new Urunler(urunId, urunAdi, urunAdeti, barkod);

                        ref.child(urunId).setValue(urunler, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error == null) {
                                    Toast.makeText(UrunEkleActivity.this, urunAdi + " Kaydedildi!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(UrunEkleActivity.this,UrunlerActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                    else{
                        urunAdiWrapper.setError("Lütfen Depo Adını Giriniz!");
                    }

                }
            });
    }


    private void urunGuncelle() {
            urunler = new Urunler();
            database = FirebaseDatabase.getInstance();
            DatabaseReference dbRef =database.getReference().child("urunler").child(gelenDepoId);
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        urunler = ds.getValue(Urunler.class);
                        long barkod = urunler.getBarkod();
                        long urunAdeti = urunler.getUrunAdeti();
                        String urunAdi = urunler.getUrunAdi();
                        String urunId = urunler.getUrunId();
                        try {
                            if(gelenUrunId.equals(urunId)){
                                urunAdetiWrapper.getEditText().setText(String.valueOf(urunAdeti));
                                urunAdiWrapper.getEditText().setText(urunAdi);
                                barkodWrapper.getEditText().setText(String.valueOf(barkod));
                            }
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
    }
    private void initComponents() {
        urunAdiWrapper = findViewById(R.id.urunAdiWrapper);
        urunAdetiWrapper = findViewById(R.id.urunAdetiWrapper);
        barkodWrapper = findViewById(R.id.barkodWrapper);
        lbldepoAdi = findViewById(R.id.lblDepoAdi);
        btnUrunKaydet = findViewById(R.id.btnUrunKaydet);
        btnUrunGuncelle = findViewById(R.id.btnUrunGuncelle);
    }
}