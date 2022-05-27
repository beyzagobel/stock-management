package beyzagobel.depo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DepoEkleActivity extends AppCompatActivity {

    private TextInputLayout depoAdWrapper;
    Depolar depolar;
    String gelenDepoId;
    Button btnKaydet,btnGuncelle;
    Intent gelenIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_ekle);

        initCompanets();
        intentKarsila();
    }

    private void intentKarsila() {
        gelenIntent = getIntent();
        gelenDepoId = gelenIntent.getStringExtra("depoId");
        if(gelenDepoId == null){
            btnGuncelle.setVisibility(View.INVISIBLE);
            btnKaydet_onClick();

        }else{
            btnKaydet.setVisibility(View.INVISIBLE);
            depoGuncelle();
            btnGuncelle_onClick();
        }
    }

    private void btnGuncelle_onClick() {
        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                depoAdWrapper.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        String depoAdi = depoAdWrapper.getEditText().getText().toString();
                        depolar = new Depolar(gelenDepoId,depoAdi);
                        DatabaseReference dR = FirebaseDatabase.getInstance().getReference().child("depolar").child(gelenDepoId);
                        dR.setValue(depolar, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error == null) {
                                    Toast.makeText(DepoEkleActivity.this, depoAdi + " Depo Güncellendi!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(DepoEkleActivity.this, DepolarActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });
                Toast.makeText(DepoEkleActivity.this,"Lütfen Değişiklik Yapınız!",Toast.LENGTH_SHORT).show();



            }
        });
    }


    private void btnKaydet_onClick() {
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(depoAdWrapper.getEditText().getText().length()>0){
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("depolar");
                    String depoId = reference.push().getKey();
                    String depoAdi = depoAdWrapper.getEditText().getText().toString();
                    depolar = new Depolar(depoId, depoAdi);
                    reference.child(depoId).setValue(depolar, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null) {
                                Toast.makeText(DepoEkleActivity.this, depoAdi + " Kaydedildi", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(DepoEkleActivity.this, DepolarActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }else{
                    depoAdWrapper.setError("Lütfen Depo Adının Giriniz!");
                }

            }
        });
    }


    private void depoGuncelle() {
        depolar = new Depolar();
        DatabaseReference dbRef =FirebaseDatabase.getInstance().getReference().child("depolar");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    depolar = ds.getValue(Depolar.class);
                    String depoId = depolar.getDepoId();
                    String depoAdi = depolar.getDepoAdi();

                    try {
                        if(gelenDepoId.equals(depoId) ){
                            depoAdWrapper.getEditText().setText(depoAdi);
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



    private void initCompanets() {
        depoAdWrapper = findViewById(R.id.depoAdWrapper);
        btnKaydet = findViewById(R.id.btnKaydet);
        btnGuncelle = findViewById(R.id.btnGuncelle);
    }
}